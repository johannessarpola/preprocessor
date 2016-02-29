/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TFIDF.Strategies;

import Abstractions.GenericService;
import Clusters.TFIDF.Internal.TFIDF;
import Global.Options.SupportedProcessingStrategy;
import Utilities.Adapters.StringTransforms;
import Utilities.Compression.CompressionPayload;
import Utilities.Compression.StringCompressor;
import Utilities.Logging.CustomExceptions.NoValueFoundException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Utilities.Hashing.HashStore;
import Utilities.Map.MapUtils;
import Utilities.Structures.LinkedWord;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class FeatureExtractor extends GenericService {
    //TODO This class needs to be cleaned up

    protected Splitter guavaSplitter; // Class spec (delimeted by words or something else)
    protected final int sizetoDigest = 100; // Not class spec      
    protected boolean usePos, compressed; // Not class spec

    protected HashStore hashStore;

    protected List<Map<String, Double>> termFrequenciesByDocument; // Term frequencies for documents
    protected List<Map<LinkedWord, Double>> compressedTermFrequenciesByDocument;
    protected Map<String, Double> invertedTermFrequenciesByDocuments; // IDF Scores for words
    protected Map<LinkedWord, Double> compressedInvertedTermFrequenciesByDocuments;
    // TODO This could be optimized by storing idf store either by the linkedword alongside tf score
    // TODO Or just store TFIDF score in the first place and not tf and idf

    protected List<String> itemsInDocuments;

    // protected Map<String, Double> reusableMap;
    // How many words are used to bias the product
    protected int numberOfDefiningFeatures = 20; // Not class spec

    public FeatureExtractor(SupportedProcessingStrategy id) {
        super(id);
        init();
    }

    // TODO Get scores for the items in lines
    public abstract Map<String, Double> getScoresByLine(String line);

    @Override
    public abstract void build(List<String> documents); // Needs to be class specific based on splitting

    public abstract void addVocabulary(List<String> documents, boolean doCompression); // Needs to be class specific based on splitting

    @Override
    public abstract String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException;

    @Override
    public abstract String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException;

    @Override
    public abstract void clear();

    /**
     * Each child needs to define splitter as they can differ on how to split
     * input e.g sentences or word or character
     */
    protected abstract void defineSplitter();

    protected abstract void childInit();

    protected void init() {
        hashStore = new HashStore(this.sizetoDigest);
        this.termFrequenciesByDocument = new ArrayList<>();
        this.compressedTermFrequenciesByDocument = new ArrayList<>();
        this.invertedTermFrequenciesByDocuments = new HashMap<>();
        this.guavaSplitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();
        this.isServiceReady = false;
        this.isVocabularyAdded = false;
        this.usePos = false;
        this.compressed = false;
        this.requiresVocabulary = true;
        this.itemsInDocuments = new ArrayList<>();
        this.compressedInvertedTermFrequenciesByDocuments = new HashMap<>();
    }

    /**
     * Gets precalculated ti-idf values
     *
     * @param line
     * @return
     */
    protected Map<String, Double> retrieveScores(String line) throws NoValueFoundException {
        Integer docIndex = getDocIndex(line);
        if (docIndex != null) {
            if (this.compressed) {
                Map<LinkedWord, Double> lwMap = tfidfScoreAsLinkedWordMap(docIndex);
                return StringTransforms.linkedWordMapToStringMap(lwMap);
            } else {
                Map<String, Double> im = tfidfScoreAsStringMap(docIndex);
                return im;
            }
        } else {
            throw new NoValueFoundException();
        }
    }

    protected Integer getDocIndex(String line) {
        return hashStore.getIndex(line);
    }

    protected Map<LinkedWord, Double> tfidfScoreAsLinkedWordMap(Integer index) {
        Map<LinkedWord, Double> tf = this.compressedTermFrequenciesByDocument.get(index);
        Map<LinkedWord, Double> tfidf = TFIDF.tfIdfFromCompressed(tf, this.compressedInvertedTermFrequenciesByDocuments);
        return tfidf;
    }

    protected Map<String, Double> tfidfScoreAsStringMap(Integer index) {
        Map<String, Double> tf = this.termFrequenciesByDocument.get(index);
        Map<String, Double> tfidf = TFIDF.tfIdf(tf, this.invertedTermFrequenciesByDocuments);
        return tfidf;
    }

    public Map<String, Double> getTfMapAsString(String document) throws NoValueFoundException {
        Integer docIndex = getDocIndex(document);
        if (!this.compressed && docIndex != null) {
            return this.termFrequenciesByDocument.get(docIndex);
        } else if (docIndex != null) {
            Map<String, Double> map = new HashMap<>();
            Map<LinkedWord, Double> lwMap = compressedTermFrequenciesByDocument.get(docIndex);
            map = StringTransforms.linkedWordMapToStringMap(lwMap);
            return map;
        } else {
            throw new NoValueFoundException();
        }
    }

    public Map<LinkedWord, Double> getTfMapAsLinkedWord(String document) throws NoValueFoundException {
        Integer docIndex = getDocIndex(document);
        return compressedTermFrequenciesByDocument.get(docIndex);
    }

    /**
     * Gets sorted map of tfidf scores for a line
     *
     * @param line
     * @return
     */
    protected Map<String, Double> getSortedEntries(String line) throws NoValueFoundException {
        Map<String, Double> lineTfidf = retrieveScores(line);
        // Sorts the values descending (true param)
        Map<String, Double> sortedTfidf = MapUtils.sortByValue(lineTfidf, true);
        return sortedTfidf;
    }

    /**
     * Gets the highest ranking parts based on TFIDF
     *
     * @param line
     * @return
     */
    protected List<String> getHighestScoringEntries(String line) throws NoValueFoundException {
        Map<String, Double> sortedTfidf = getSortedEntries(line);
        List<String> result = getDefinedNumberOfFeatures(sortedTfidf, line);
        return result;
    }

    /**
     * Translates entries of map to Strings based on ranking in a line
     *
     * @param sortedMap
     * @param line
     * @return
     */
    private List<String> getDefinedNumberOfFeatures(Map<String, Double> sortedMap, String line) {
        List<String> kws = new ArrayList<>();
        int maxSize = guavaSplitter.splitToList(line).size();
        int i = 0;
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            if (i == this.numberOfDefiningFeatures && i < maxSize) {
                break;
            }
            kws.add(entry.getKey());
            i++;

        }
        return kws;
    }

    // This is for backwards compatibility
    protected CompressionPayload compress() {
        return compress(false);
    }

    /**
     * Compresses the object into less memory-hungry version
     */
    protected CompressionPayload compress(boolean doMapping) {
        CompressionPayload cr = StringCompressor.compressTermFrequencies(termFrequenciesByDocument, invertedTermFrequenciesByDocuments, doMapping);
        this.itemsInDocuments = cr.getItems();
        this.compressedTermFrequenciesByDocument = cr.getCompressedDocuments();
        this.compressedInvertedTermFrequenciesByDocuments = cr.getCompressedIdfMap();
        this.termFrequenciesByDocument.clear();
        this.invertedTermFrequenciesByDocuments.clear();
        this.compressed = true;
        return cr;
    }

    public int getNumberOfDefiningFeatures() {
        return numberOfDefiningFeatures;
    }

    public void setNumberOfDefiningFeatures(int numberOfDefiningFeatures) {
        this.numberOfDefiningFeatures = numberOfDefiningFeatures;
    }

    /**
     * Does the appending from list to string
     *
     * @param line
     * @param ls
     * @return
     */
    protected String doAppend(String line, List<String> ls) {
        line = ls.stream().map((s) -> " " + s).reduce(line, String::concat);
        return line;
    }

    /**
     * Does the replacing from list tstring
     *
     * @param line
     * @param ls
     * @return
     */
    // TODO Fix the |
    protected String doReplace(String line, List<String> ls) {
        String ret = "";
        // Lol, no idea
        ret = ls.stream().map((s) -> s + "|").reduce(ret, String::concat);
        return ret;
    }
}
