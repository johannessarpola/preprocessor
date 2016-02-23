/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor;

import Utilities.Processing.Stopwords;
import Processor.Internal.Stemmer;
import Processor.Internal.Lemmatizer;
import static Processor.Internal.URLRemover.removeUrl;
import static Utilities.String.CStringOperations.removeTags;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Johannes töissä
 */
public class ArticleProcessor {

    private Splitter guavaSplitter;
    private CharMatcher guavaCharMatcher;
    private Stopwords sw;
    private Set<String> stopWords;
    private Stemmer stemmer;
    private ArticleProcessorStates states;
    private Lemmatizer lemmatizer;
    // private SemanticProcessorClusters spClusters;
    
    // TODO Add procesing for Jsn, Jsnusty etc words and other dates http://stackoverflow.com/questions/4024544/how-to-parse-dates-in-multiple-formats-using-simpledateformat
    // TODO Remove weekdays
    // TODO words between <> and [}

    public ArticleProcessor() {
        
        init();

    }

    private void init() {
        this.guavaCharMatcher = CharMatcher.anyOf("-_!@#$%*()_+{}:\"'\\,./<>?|[]`~=;");
        this.sw = new Stopwords();
        this.stopWords = getSw().getStopwords();
        this.stemmer = new Stemmer();
        this.guavaSplitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();
        this.states = new ArticleProcessorStates();
        this.lemmatizer = new Lemmatizer();
        //this.spClusters = new SemanticProcessorClusters();
    }
    // TODO Use either Lemnas or Stems
    public String processLineToString(String line) {
        List<String> ms = processLine(line);
        String str = "";
        boolean firstrun = true;
        for (String s : ms) {
            if(firstrun) {
                str = s;
                firstrun = false;
            }
            else {
                str += " " + s;
            }
        }
        return str;
    }
    /**
     * Saves up memory but looses the ordering
     * @param line
     * @return 
     */
    public Multiset<String> processLineToMultiset(String line){
         HashMultiset<String> ms = HashMultiset.create();
         List<String> lineL = processLine(line);
         for(String s : lineL){
             ms.add(s);
         }
         return ms;
    }
    /**
     * Processes a line (article) in order
     * @param line
     * @return
     */
    public List<String> processLine(String line) {
        List<String> tokens = getSplitter().splitToList(line);
        // MultiSet is a Set where count of occurencies are stored also
       List<String> cleanTokens = new ArrayList<String>();
        // Lemmatizer need to operate on the sentence level because it uses PoS tagging
        if(states.isUseStanfordLemmatizer()) {
            tokens = lemmatizer.lemmatize(line);
        }
        for (String token : tokens) {
            String cleanWord = processWord(token);
            if (cleanWord != null) {
                cleanTokens.add(cleanWord);

            }
        }
        // TODO Add concepts and weigts
        return cleanTokens;
    }
    
    // TODO prisoner-of-war will be prisonerofwar, need to be fixed?
    public String processWord(String token) {
        if(states.isUseRemoveTags()) {
            token = removeTags(token, getCharMatcher()); // Removes [Word] <Word> and such
        }
        if(states.isUseRemoveURLs()) {
            token = removeUrl(token); // remove urls
        }
        // you could also increase the weight of urls if it is concluded to be important
        //String noPunctLowerCase = getCharMatcher().removeFrom(token).toLowerCase();
        if(states.isUseRemovePunctuationMarks()) {
            token = getCharMatcher().removeFrom(token);
        }
        if(states.isUseLowercaseOnly()) {
            token = token.toLowerCase();
        }
        if (token.length() < 2 && states.isUseRemoveSingleCharacters()) {
            return null;
        }
        if( Doubles.tryParse(token) != null && states.isUseRemoveNumbers()) {
            return null;
        }
        if(stopWords.contains(token)  && states.isUseRemoveStopwords()){
            return null;
        }
        //String stemOrLem = "";
        if(states.isUsePorterstemmer()){
            token = getStem(token);
        }
        return token;
    }

    public String getStem(String word) {
        getStemmer().add(word.toCharArray(), word.length());
        return getStemmer().stem().intern();
    }

    public Splitter getSplitter() {
        return guavaSplitter;
    }

    public CharMatcher getCharMatcher() {
        return guavaCharMatcher;
    }

    public Stopwords getSw() {
        return sw;
    }

    public Stemmer getStemmer() {
        return stemmer;
    }

    public ArticleProcessorStates getStates() {
        return states;
    }

}
