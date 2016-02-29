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
import Utilities.Structures.LinkedWord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Uses both word ngrams and keywords to extract most defining features
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CombinedExtractor extends FeatureExtractor {

    WordNgramExtractor wne;
    KeywordExtractor ke;
    Map<String, List<LinkedWord>> wordToNgramMapping; // Used to access ngrams for a word quicker
    
    public CombinedExtractor() {
        super(SupportedProcessingStrategy.TFIDF_Combined);
        super.init();
        childInit();
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
            Logger.getLogger(CombinedExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException {
        try {
            this.setNumberOfDefiningFeatures(biasingSize);
            List<String> res = this.getHighestScoringEntries(line);
            return this.doAppend(line, res);
        } catch (NoValueFoundException ex) {
            Logger.getLogger(CombinedExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException {
        try {
            this.setNumberOfDefiningFeatures(biasingSize);
            List<String> res = this.getHighestScoringEntries(line);
            return this.doReplace(line, res);
        } catch (NoValueFoundException ex) {
            Logger.getLogger(CombinedExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void clear() {
        super.init();
        childInit();
    }

    @Override
    protected void childInit() {
        wne = new WordNgramExtractor();
        ke = new KeywordExtractor();
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
            //super.termFrequenciesByDocument.addAll(termFrequenciesByDocument)
            Map<String, Double> temp = ke.getTfMapAsString(doc);
            Map<String, Double> temp2 = wne.getTfMapAsString(doc);
            Map<String, Double> temp3 = new HashMap<>(temp);
            temp3.putAll(temp2);
            this.termFrequenciesByDocument.add(temp3);
            this.hashStore.storeKey(doc, docIndex);
            docIndex++;
        }
        this.invertedTermFrequenciesByDocuments = TFIDF.idfFromTfs(this.termFrequenciesByDocument);
        // TODO Create combined TFIDF_Keywords
        if (doCompression) {
            this.compress();
        }
        //TODO Null keywordex and ngramex
        this.isServiceReady = true;
        this.isVocabularyAdded = true;
        ke = null;
        wne = null;
    }

    @Override
    public Map<String, Double> getScoresByLine(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
