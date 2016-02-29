/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TFIDF.Strategies;

import Clusters.TFIDF.Internal.TFIDF;
import Global.Options;
import Utilities.Logging.CustomExceptions.NoValueFoundException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Utilities.Map.MapUtils;
import Utilities.Processing.Stopwords;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class KeywordExtractor extends FeatureExtractor {
    
    // TODO Figure out a better way to remove stopwords, this messes up the contract
    private Set<String> stopwords;

    /**
     * Gets the Keywords based on TDIDF scores. Scope is only on word level.
     */
    public KeywordExtractor() {
        super(Options.SupportedProcessingStrategy.TFIDF_Keywords);
        childInit();
    }

    @Override
    public void addVocabulary(List<String> documents, boolean doCompression) {
        if (!isVocabularyAdded) {
            setupService(documents, doCompression);
            isVocabularyAdded = true;
        }
    }

    @Override
    public void build(List<String> documents) {
        if (!isVocabularyAdded) {
            setupService(documents);
            isVocabularyAdded = true;
        }
    }

    private void setupService(List<String> documents) {
        setupService(documents, true);
    }

    private void setupService(List<String> documents, boolean doCompression) {

        int docIndex = 0;
        // TODO remove the other stuff than noun,verbs and adjectives here. Tests break?
        for (String doc : documents) {
            List<String> d = guavaSplitter.splitToList(doc);
            Map<String, Double> map = TFIDF.tf(d);
            this.termFrequenciesByDocument.add(map);
            this.hashStore.storeKey(d, docIndex);
            docIndex++;
        }
        this.invertedTermFrequenciesByDocuments = TFIDF.idfFromTfs(this.termFrequenciesByDocument);
        if (doCompression) {
            super.compress();
        }
        this.isServiceReady = true;
        this.isVocabularyAdded = true;
    }

    private String appendWords(String line) throws NoValueFoundException {
        String words = "";
        List<String> highestKeywords = this.getHighestScoringEntries(line);
        for (String w : highestKeywords) {
            words += w + " ";
        }
        String ret = line + " " + words;
        return ret;
    }

    private String replaceWords(String line) throws NoValueFoundException {
        String words = "";
        List<String> highestKeywords = this.getHighestScoringEntries(line);
        for (String w : highestKeywords) {
            words += w + " ";
        }
        String ret = words;
        return ret;
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException {
        if (isServiceReady) {
            try {
                this.setNumberOfDefiningFeatures(biasingSize);
                String aLine = replaceWords(line);
                return aLine;
            } catch (NoValueFoundException ex) {
                Logger.getLogger(KeywordExtractor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new ServiceNotReadyException();
        }
        return line; // TODO Processing failed exception
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException {
        if (isServiceReady) {
            try {
                this.setNumberOfDefiningFeatures(biasingSize);
                String aLine = appendWords(line);
                return aLine;
            } catch (NoValueFoundException ex) {
                Logger.getLogger(KeywordExtractor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new ServiceNotReadyException();
        }
        return line; // TODO Processing failed exception
    }

    @Override
    public void clear() {
        super.init();
        childInit();
    }

    @Override
    protected void childInit() {
        stopwords = new Stopwords().getStopwords();
    }

    @Override
    protected void defineSplitter() {
        this.guavaSplitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();
    }

    @Override
    public Map<String, Double> getScoresByLine(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Custom getter to get the highest ranking parts based on TFIDF_Keywords without
 stopwords
     *
     * @param line
     * @return
     */
    
    protected List<String> getHighestScoringEntriesWithoutStopWords(String line) throws NoValueFoundException {
        Map<String, Double> lineTfidf = retrieveScores(line);
        // Sorts the values descending (true param)
        Map<String, Double> sortedTfidf = MapUtils.sortByValue(lineTfidf, true);
        List<String> keywords = getEntriesWithoutStopwords(sortedTfidf, line);
        return keywords;
    }
    
    /**
     * Translates entries of map to Strings based on ranking in a line
     *
     * @param sortedMap
     * @param line
     * @return
     */
    
    private List<String> getEntriesWithoutStopwords(Map<String, Double> sortedMap, String line) {
        List<String> kws = new ArrayList<>();
        int lineLen = guavaSplitter.splitToList(line).size();
        int i = 0; 
        Map<String, String> pos = new HashMap<>();
        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            if (i == this.numberOfDefiningFeatures && i < lineLen) {
                break;
            }
            if (!stopwords.contains(entry.getKey())) {
                kws.add(entry.getKey());
                i++;
            }
        }
        // TODO Handle if input is ONLY stopwords
        return kws;
    }
    
}
