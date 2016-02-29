package Clusters.TFIDF.Strategies;

import Clusters.TFIDF.Internal.Ngram;
import Clusters.TFIDF.Internal.TFIDF;
import Global.Options;
import Utilities.Logging.CustomExceptions.NoValueFoundException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WordNgramExtractor extends FeatureExtractor {
    // POSSIBILITY To use Google Ngrams (?)
    // http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.21.3248&rep=rep1&type=pdf
    // TODO Zipfs law
    // TODO Skip n-grams?

    private int ngramRange = 2; // From 2 to 4 by default

    public WordNgramExtractor() {
        super(Options.SupportedProcessingStrategy.TFIDF_WordNgram);
        super.init();
    }

    @Override
    public void addVocabulary(List<String> documents, boolean doCompression) {
        List<List<String>> docs = createNgramsForDocuments(documents);
        setupService(docs, doCompression);
    }

    @Override
    public void build(List<String> documents) {
        List<List<String>> docs = createNgramsForDocuments(documents);
        setupService(docs);

    }

    private List<List<String>> createNgramsForDocuments(List<String> documents) {
        List<List<String>> docs = new ArrayList<>();
        int docIndex = 0;
        // Create ngrams for document
        for (String document : documents) {
            List<String> ngramsForDocument = Ngram.createFlatNgrams(document, ngramRange);
            docs.add(ngramsForDocument);
            indexDocumentWithKey(document, docIndex);
            docIndex++;
        }
        return docs;
    }
    
    private void indexDocumentWithKey(String doc, int index) {
        this.hashStore.storeKey(doc, index);
    }

    private void setupService(List<List<String>> documents) {
        setupService(documents, true);
    }

    private void setupService(List<List<String>> documents, boolean doCompression) {
        // TODO remove the other stuff than noun,verbs and adjectives here. Tests break?
        for (List<String> ngramsInDoc : documents) {
            //List<String> d = guavaSplitter.splitToList(doc);
            Map<String,Double> map = TFIDF.tf(ngramsInDoc);
            this.termFrequenciesByDocument.add(map);
        }
        this.invertedTermFrequenciesByDocuments = TFIDF.idfFromTfs(this.termFrequenciesByDocument);
        if (doCompression) {
            super.compress();
        }
        this.isServiceReady = true;
        this.isVocabularyAdded = true;
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException {
        if (isServiceReady) {
            try {
                this.numberOfDefiningFeatures = biasingSize;
                List<String> ls = this.getHighestScoringEntries(line);
                line = this.doAppend(line, ls);
                return line;
            } catch (NoValueFoundException ex) {
                Logger.getLogger(WordNgramExtractor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new ServiceNotReadyException();
        }
        return line; // TODO Report unprocessed
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException {
        if (isServiceReady) {
            try {
                this.numberOfDefiningFeatures = biasingSize;
                List<String> ls = this.getHighestScoringEntries(line);
                line = this.doReplace(line, ls);
                return line;
            } catch (NoValueFoundException ex) {
                Logger.getLogger(WordNgramExtractor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new ServiceNotReadyException();
        }
        return line; // TODO Report unprocessd
    }

    @Override
    public void clear() {
        childInit();
    }

    @Override
    protected void defineSplitter() {
        this.guavaSplitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();
    }

    @Override
    protected void childInit() {
        super.init();
    }

    @Override
    public Map<String, Double> getScoresByLine(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
