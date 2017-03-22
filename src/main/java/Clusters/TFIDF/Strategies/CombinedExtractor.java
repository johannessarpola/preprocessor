/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TFIDF.Strategies;

import Clusters.TFIDF.Internal.TFIDF;
import Global.Options.SupportedProcessingStrategy;
import Utilities.Logging.CustomExceptions.NoValueFoundException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Utilities.Logging.CustomExceptions.UnhandledServiceException;
import Utilities.Logging.GeneralLogging;
import Utilities.Structures.LinkedWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Uses both word ngrams and keywords to extract most defining features
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CombinedExtractor extends FeatureExtractor {

    private WordNgramExtractor wordNgramExtractor;
    private KeywordExtractor keywordExtractor;
    Map<String, List<LinkedWord>> wordToNgramMapping; // Used to access ngrams for a word quicker
    
    public CombinedExtractor() {
        super(SupportedProcessingStrategy.TFIDF_Combined);
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
            GeneralLogging.logStackTrace_Error(getClass(), ex);
        }
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException {
        try {
            this.setNumberOfDefiningFeatures(biasingSize);
            List<String> res = this.getHighestScoringEntries(line);
            return this.doAppend(line, res);
        } catch (NoValueFoundException ex) {
            GeneralLogging.logStackTrace_Error(getClass(), ex);
        }
        return null;
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        try {
            this.setNumberOfDefiningFeatures(biasingSize);
            List<String> res = this.getHighestScoringEntries(line);
            return this.doReplace(line, res);
        } catch (NoValueFoundException ex) {
            throw new UnhandledServiceException();
        }
    }

    @Override
    public void clear() {
        super.reInit();
        subclassSpecificInit();
    }

    private void subclassSpecificInit() {
        wordNgramExtractor = new WordNgramExtractor();
        keywordExtractor = new KeywordExtractor();
    }

    @Override
    protected void defineSplitter() {
        wordNgramExtractor.defineSplitter();
        keywordExtractor.defineSplitter();
    }

    private void setupBoth(List<String> documents, boolean doCompression) throws NoValueFoundException {
        wordNgramExtractor.addVocabulary(documents, false);
        keywordExtractor.addVocabulary(documents, false); // We do not want to compress yet
        int docIndex = 0;

        for (String doc : documents) {
            //super.tfScores.addAll(tfScores)
            Map<String, Double> temp = keywordExtractor.getTfMapAsString(doc);
            Map<String, Double> temp2 = wordNgramExtractor.getTfMapAsString(doc);
            Map<String, Double> temp3 = new HashMap<>(temp);
            temp3.putAll(temp2);
            this.tfScores.add(temp3);
            this.hashStore.storeKey(doc, docIndex);
            docIndex++;
        }
        this.idfScores = TFIDF.idfFromTfs(this.tfScores);
        if (doCompression) {
            this.compress();
        }
        this.isServiceReady = true;
        this.isVocabularyAdded = true;
        keywordExtractor = null;
        wordNgramExtractor = null;
    }

    @Override
    public Map<String, Double> getScoresByLine(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
