/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.UnsupervisedBiasing.Strategies;

import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Clusters.UnsupervisedBiasing.Internal.TFIDF;
import fi.johannes.Core.App.SupportedProcessingStrategy;
import fi.johannes.Utilities.Adapters.StringTransforms;
import fi.johannes.Utilities.Compression.CompressionPayload;
import fi.johannes.Utilities.Compression.StringCompressor;
import fi.johannes.Utilities.Hashing.HashStore;
import fi.johannes.Utilities.Logging.CustomExceptions.NoValueFoundException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Map.MapUtils;
import fi.johannes.Utilities.Structures.LinkedWord;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class FeatureExtractor extends GenericService {

    protected Splitter splitter; // Class spec (delimeted by words or something else)
    protected final int sizetoDigest = 100; // Not class spec      
    protected boolean usePos, compressed; // Not class spec
    protected HashStore hashStore;
    protected List<Map<String, Double>> tfScores; // Term frequencies for documents
    protected List<Map<LinkedWord, Double>> tfScoresCompressed;
    protected Map<String, Double> idfScores; // IDF Scores for words
    protected Map<LinkedWord, Double> compressedEntities;
    protected Set<String> universe;

    // protected Map<String, Double> reusableMap;
    // How many words are used to bias the product
    protected int numberOfDefiningFeatures = 20; // Not class spec

    public FeatureExtractor(SupportedProcessingStrategy id) {
        super(id);
        init();
    }

    public abstract Map<String, Double> getScoresByLine(String line);

    @Override
    public abstract void build(List<String> documents); // Needs to be class specific based on splitting

    public abstract void addVocabulary(List<String> documents, boolean doCompression); // Needs to be class specific based on splitting

    @Override
    public abstract String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException;

    @Override
    public abstract String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException;

    @Override
    public abstract void clear();

    /**
     * Each child needs to define splitter as they can differ on how to split
     * input e.g sentences or word or character
     */
    protected abstract void defineSplitter();

    private void init() {
        hashStore = new HashStore(this.sizetoDigest);
        this.tfScores = new ArrayList<>();
        this.tfScoresCompressed = Collections.emptyList();
        this.idfScores = new HashMap<>();
        this.splitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();
        this.universe = new HashSet<>();
        this.compressedEntities = new HashMap<>();
        setupBooleans();
    }

    private void setupBooleans() {
        this.isServiceReady = false;
        this.isVocabularyAdded = false;
        this.usePos = false;
        this.compressed = false;
        this.requiresVocabulary = true;
    }

    protected void clearData() {
        universe.clear();
        compressedEntities.clear();
        tfScores.clear();
        tfScoresCompressed.clear();
        idfScores.clear();
    }

    public void reInit() {
        setupBooleans();
        clearData();
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
        Map<LinkedWord, Double> tf = this.tfScoresCompressed.get(index);
        Map<LinkedWord, Double> tfidf = TFIDF.tfIdfFromCompressed(tf, this.compressedEntities);
        return tfidf;
    }

    protected Map<String, Double> tfidfScoreAsStringMap(Integer index) {
        Map<String, Double> tf = this.tfScores.get(index);
        Map<String, Double> tfidf = TFIDF.tfIdf(tf, this.idfScores);
        return tfidf;
    }

    public Map<String, Double> getTfMapAsString(String document) throws NoValueFoundException {
        Integer docIndex = getDocIndex(document);
        if (!this.compressed && docIndex != null) {
            return this.tfScores.get(docIndex);
        } else if (docIndex != null) {
            Map<String, Double> map = new HashMap<>();
            Map<LinkedWord, Double> lwMap = tfScoresCompressed.get(docIndex);
            map = StringTransforms.linkedWordMapToStringMap(lwMap);
            return map;
        } else {
            throw new NoValueFoundException();
        }
    }

    public Map<LinkedWord, Double> getTfMapAsLinkedWord(String document) throws NoValueFoundException {
        Integer docIndex = getDocIndex(document);
        return tfScoresCompressed.get(docIndex);
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
        int maxSize = splitter.splitToList(line).size();
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
        CompressionPayload cr = StringCompressor.compressTermFrequencies(tfScores, idfScores, doMapping);

        this.universe = new HashSet<>(cr.getAllWords());
        this.tfScoresCompressed = cr.getCompressedTermFrequencies();
        this.compressedEntities = cr.getCompressedIdfMap();
        this.tfScores.clear();
        this.idfScores.clear();
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
        line += ls != null && !ls.isEmpty() ? " " + ls.stream().collect(Collectors.joining(" ")) : "";
        return line;
    }

    /**
     * Does the replacing from list tstring
     *
     * @param line
     * @param ls
     * @return
     */
    protected String doReplace(String line, List<String> ls) {
        return ls.stream().collect(Collectors.joining(" "));
    }
}
