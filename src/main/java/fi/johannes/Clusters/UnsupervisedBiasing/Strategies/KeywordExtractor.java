/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.UnsupervisedBiasing.Strategies;

import fi.johannes.Clusters.UnsupervisedBiasing.Internal.TFIDF;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Utilities.Logging.CustomExceptions.NoValueFoundException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Map.MapUtils;
import fi.johannes.Utilities.Processing.Stopwords;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.*;

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
        super(SupportedProcessingStrategy.TFIDF_Keywords);
        subclassSpecificInit();
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
            List<String> d = splitter.splitToList(doc);
            Map<String, Double> map = TFIDF.tf(d);
            this.tfScores.add(map);
            this.hashStore.storeKey(d, docIndex);
            docIndex++;
        }
        this.idfScores = TFIDF.idfFromTfs(this.tfScores);
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
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        if (isServiceReady) {
            try {
                this.setNumberOfDefiningFeatures(biasingSize);
                String aLine = replaceWords(line);
                return aLine;
            } catch (NoValueFoundException ex) {
                throw new UnhandledServiceException();
            }
        } else {
            throw new ServiceNotReadyException();
        }
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        if (isServiceReady) {
            try {
                this.setNumberOfDefiningFeatures(biasingSize);
                String aLine = appendWords(line);
                return aLine;
            } catch (NoValueFoundException ex) {
                throw new UnhandledServiceException();
            }
        } else {
            throw new ServiceNotReadyException();
        }
    }

    @Override
    public void clear() {
        super.reInit();
        subclassSpecificInit();
    }

    private void subclassSpecificInit() {
        stopwords = new Stopwords().getStopwords();
    }

    @Override
    protected void defineSplitter() {
        this.splitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();
    }

    @Override
    public Map<String, Double> getScoresByLine(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Custom getter to get the highest ranking parts based on TFIDF_Keywords
     * without stopwords
     *
     * @param line
     * @return
     */
    protected List<String> getHighestScoringEntriesWithoutStopWords(String line) throws NoValueFoundException {
        Map<String, Double> lineTfidf = retrieveScores(line);
        Map<String, Double> sortedTfidf = MapUtils.sortByValue(lineTfidf, true);
        List<String> keywords = getEntriesWithoutStopwords(sortedTfidf, line);
        return keywords;
    }

    /**
     * Translates entries of serviceMap to Strings based on ranking in a line
     *
     * @param sortedMap
     * @param line
     * @return
     */
    private List<String> getEntriesWithoutStopwords(Map<String, Double> sortedMap, String line) {
        List<String> kws = new ArrayList<>();
        int lineLen = splitter.splitToList(line).size();
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
