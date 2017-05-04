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
import fi.johannes.Utilities.Logging.Logging;
import fi.johannes.Utilities.Map.MapUtils;
import fi.johannes.Utilities.Structures.LinkedWord;

import java.util.*;
import java.util.Map.Entry;

/**
 * Gets ngrams for set number of keywords.
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class KeywordsFirstExtractor extends FeatureExtractor {

    private WordNgramExtractor wne;
    private KeywordExtractor ke;
    private Map<String, List<LinkedWord>> wordToNgramMapping; // Used to access ngrams for a word quicker
    private double totalScore;
    private int itemsInResult;

    public KeywordsFirstExtractor() {
        super(SupportedProcessingStrategy.TFIDF_KeywordsFirst);
        subclassSpecificInit();
    }

    @Override
    public void build(List<String> documents) {
        addVocabulary(documents, true);
    }

    @Override
    public void addVocabulary(List<String> documents, boolean doCompression) {
        try {
            setupBoth(documents, doCompression);
        } catch (NoValueFoundException ex) {
            Logging.logStackTrace_Error(getClass(), ex);
        }
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        try {
            this.setNumberOfDefiningFeatures(biasingSize);
            List<String> keywords = getKeywords(line, biasingSize);
            List<String> ngrams = getHighestNgramsForKeywords(line, keywords);
            // TODO using mapping to get highest ngrams
            return this.doAppend(line, ngrams);
        } catch (NoValueFoundException ex) {
            throw new UnhandledServiceException();
        }
    }

    /**
     * Gets the highest keywords from KeywordExtractor without stopwords
     *
     * @param line
     * @param biasingsize
     * @return
     * @throws NoValueFoundException
     */
    private List<String> getKeywords(String line, int biasingsize) throws NoValueFoundException {
        ke.setNumberOfDefiningFeatures(biasingsize);
        List<String> result = ke.getHighestScoringEntriesWithoutStopWords(line);
        return result;
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        try {
            this.setNumberOfDefiningFeatures(biasingSize);
            List<String> keywords = getKeywords(line, biasingSize);
            List<String> ngrams = getHighestNgramsForKeywords(line, keywords);
            // TODO using mapping to get highest ngrams
            return this.doReplace(line, ngrams);
        } catch (NoValueFoundException ex) {
            throw new UnhandledServiceException();
        }
    }

    protected List<String> getHighestNgramsForKeywords(String line, List<String> keywords) {
        Integer index = getDocIndex(line);
        Map<LinkedWord, Double> tfidf = this.tfidfScoreAsLinkedWordMap(index);
        Map<LinkedWord, Double> ngrams = getNgramsForKeywords(keywords, tfidf);
        List<String> highestNgrams = getHighestEntriesAsStrings(ngrams);
        return highestNgrams;
    }

    // TODO  Avg is not probably the best
    private List<String> getHighestEntriesAsStrings(Map<LinkedWord, Double> map) {
        map = MapUtils.sortByValue(map, true);
        List<String> l = new ArrayList<>();
        double avg = totalScore / itemsInResult;
        for (Entry<LinkedWord, Double> e : map.entrySet()) {
            if (e.getValue() >= avg) {
                l.add(e.getKey().toString());
            }
        }
        return l;
    }

    private List<LinkedWord> getHighestEntriesAsLinkedWord(Map<LinkedWord, Double> map) {
        map = MapUtils.sortByValue(map, true);
        List<LinkedWord> l = new ArrayList<>();
        double avg = totalScore / itemsInResult;
        for (Entry<LinkedWord, Double> e : map.entrySet()) {
            if (e.getValue() >= avg) {
                l.add(e.getKey());
            }
        }
        return l;
    }

    /**
     * Gets the Ngrams for a given word
     *
     * @param word
     * @return
     */
    protected List<LinkedWord> getNgramsInMapping(String word) {
        List<LinkedWord> lws = wordToNgramMapping.get(word);
        return lws;
    }

    /**
     * Gets the Ngrams for a given word
     *
     * @param word
     * @return
     */
    protected List<String> getNgramsInMappingAsString(String word) {
        List<LinkedWord> lws = getNgramsInMapping(word);
        List<String> strs = new ArrayList<>();
        for (LinkedWord lw : lws) {
            strs.add(lw.toString());
        }
        return strs;
    }

    /**
     * Gets the Ngrams for a specific keywords (keyword-ngram relationship is
     * one-to-many)
     *
     * @param keywords
     * @param tfMap
     * @return
     */
    private Map<LinkedWord, Double> getNgramsForKeywords(List<String> keywords, Map<LinkedWord, Double> tfidfMap) {
        Map<LinkedWord, Double> result = new HashMap<>();
        List<LinkedWord> lwsForKw = new ArrayList<>();
        Map<LinkedWord, Double> copyoftfidf = new HashMap<>(tfidfMap);
        for (String kw : keywords) {
            lwsForKw = wordToNgramMapping.get(kw);
            // TODO There's problem with LwsForKW
            Iterator<Entry<LinkedWord, Double>> it = copyoftfidf.entrySet().iterator();
            while (it.hasNext()) {
                Entry<LinkedWord, Double> item = it.next();
                if (lwsForKw.contains(item.getKey()) && !result.containsKey(item.getKey())) {
                    Double put = result.put(item.getKey(), item.getValue());
                    totalScore += item.getValue();
                    itemsInResult++;
                    it.remove();
                    if (put != null) {
                        Logging.logMessage_Error(getClass(), "There was duplicate value in result serviceMap (Equals or Hashmap for LW doesn't work)" + put);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void clear() {
        super.reInit();
        subclassSpecificInit();
    }

    private void subclassSpecificInit() {
        wne = new WordNgramExtractor();
        ke = new KeywordExtractor();
        wordToNgramMapping = new HashMap<>();
    }

    @Override
    protected void defineSplitter() {
        wne.defineSplitter();
        ke.defineSplitter();
    }

    public void setupBoth(List<String> documents, boolean doCompression) throws NoValueFoundException {
        wne.addVocabulary(documents, false);
        ke.addVocabulary(documents, false); // We do not want to compress yet
        int docIndex = 0;

        for (String doc : documents) {
            //super.tfScores.addAll(tfScores)
            Map<String, Double> temp = wne.getTfMapAsString(doc);
            this.tfScores.add(temp);
            this.hashStore.storeKey(doc, docIndex);
            docIndex++;
        }
        this.idfScores = TFIDF.idfFromTfs(this.tfScores);
        // TODO Create combined UnsupervisedBiasing
        if (doCompression) {
            // We're going to use mapping in this class
            wordToNgramMapping = this.compress(true).getWordToNgramsMapping();
        }
        //TODO Null keywordex and ngramex
        this.isServiceReady = true;
        this.isVocabularyAdded = true;
        wne = null;
    }

    @Override
    public Map<String, Double> getScoresByLine(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
