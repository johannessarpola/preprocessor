/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Doubles;
import fi.johannes.Utilities.Processing.Lemmatizer;
import fi.johannes.Utilities.Processing.Stemmer;
import fi.johannes.Utilities.Processing.Stopwords;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static fi.johannes.Utilities.Processing.URLRemover.removeUrl;
import static fi.johannes.Utilities.String.StringOperations.removeTags;

/**
 *
 * @author Johannes töissä
 */
@Component
public class ArticleProcessor {

    private Stemmer stemmer;
    private Lemmatizer lemmatizer;
    private Stopwords sw;

    private Splitter guavaSplitter;
    private CharMatcher guavaCharMatcher;
    private Set<String> stopWords;
    private ArticleProcessorStates states;

    // TODO Add procesing for Jsn, Jsnusty etc words and other dates http://stackoverflow.com/questions/4024544/how-to-parse-dates-in-multiple-formats-using-simpledateformat
    // TODO Remove weekdays
    // TODO words between <> and [}

    public ArticleProcessor() {
        init();
    }

    private void init() {
        this.guavaCharMatcher = CharMatcher.anyOf("-_!@#$%*()_+{}:\"'\\,./<>?|[]`~=;");
        this.sw = new Stopwords();
        this.stemmer = new Stemmer();
        this.states = new ArticleProcessorStates();
        this.lemmatizer = new Lemmatizer();
        this.stopWords = new Stopwords().getStopwords();
        this.guavaSplitter = Splitter.on(CharMatcher.whitespace()).omitEmptyStrings();
    }

    /**
     * Saves up memory but looses the ordering
     * @param line
     * @return 
     */
    public Multiset<String> processLineToMultiset(String line){
         HashMultiset<String> ms = HashMultiset.create();
         List<String> lineL = processLineToList(line);
         for(String s : lineL){
             ms.add(s);
         }
         return ms;
    }

    public String processLineToString(String line){
        return processLineToList(line).stream().collect(Collectors.joining(" "));
    }

    /**
     * Processes a line (article) in order
     * @param line
     * @return
     */
    public List<String> processLineToList(String line) {
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
