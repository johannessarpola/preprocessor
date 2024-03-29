package fi.johannes.Clusters.UnsupervisedBiasing.Strategies;

import fi.johannes.Clusters.UnsupervisedBiasing.Strategies.FeatureExtractor;
import fi.johannes.Utilities.String.Ngram;
import fi.johannes.Clusters.UnsupervisedBiasing.Internal.TFIDF;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Utilities.Logging.CustomExceptions.NoValueFoundException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        super(SupportedProcessingStrategy.TFIDF_WordNgram);

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
            //List<String> d = splitter.splitToList(doc);
            Map<String,Double> map = TFIDF.tf(ngramsInDoc);
            this.tfScores.add(map);
        }
        this.idfScores = TFIDF.idfFromTfs(this.tfScores);
        if (doCompression) {
            super.compress();
        }
        this.isServiceReady = true;
        this.isVocabularyAdded = true;
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        if (isServiceReady) {
            try {
                this.numberOfDefiningFeatures = biasingSize;
                List<String> ls = this.getHighestScoringEntries(line);
                line = this.doAppend(line, ls);
                return line;
            } catch (NoValueFoundException ex) {
                throw new UnhandledServiceException();
            }
        } else {
            throw new ServiceNotReadyException();
        }
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        if (isServiceReady) {
            try {
                this.numberOfDefiningFeatures = biasingSize;
                List<String> ls = this.getHighestScoringEntries(line);
                line = this.doReplace(line, ls);
                return line;
            } catch (NoValueFoundException ex) {
                throw new UnhandledServiceException();
            }
        } else {
            throw new ServiceNotReadyException();
        }
    }

    @Override
    public void clear() {
        super.clearData();
        subclassSpecificInit();
    }

    @Override
    protected void defineSplitter() {
        this.splitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();
    }

    private void subclassSpecificInit() {
        // TODO ??
    }

    @Override
    public Map<String, Double> getScoresByLine(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
