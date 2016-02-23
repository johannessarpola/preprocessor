/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TFIDF.Internal;

import Utilities.GeneralUtilities;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class Ngram {

    static final int startingNgram = 2;
    /**
     * Creates ngrams which are stored into lists e.g bigrams go to one list and trigrams to another
     * @param line
     * @param range
     * @return 
     */
    public static List<List<String>> createNgrams(String line, int range) {
        List<String> ls = new ArrayList<>();
        List<List<String>> ngrams = new ArrayList<>();
        List<String> lineLs = GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line);
        int linepos = 0;
        int ngramSize = 0;
        for (int i = startingNgram; i <= (range + startingNgram); i++) {
            for (String st : lineLs) {
                String ngram = "";
                if (linepos == lineLs.size()) {
                    break;
                }
                boolean first = true;
                ngramSize = 0;
                for (int j = 0; j < i; j++) {
                    if (linepos < lineLs.size() && (linepos + j) < lineLs.size()) {
                        if (first) {
                            ngram += lineLs.get(linepos) + " ";
                            first = false;
                            ngramSize++;
                        } else if (j < i - 1) {
                            ngram += lineLs.get(linepos + j) + " ";
                            ngramSize++;
                        } else {
                            ngram += lineLs.get(linepos + j);
                            ngramSize++;
                        }
                    }
                }
                ngram = ngram.trim();
                first = true;
                linepos++;
                // Adds if ngram is either n-1 or n length
                // ngramSize == i -1  ||  this is where end of sentence would lead to unigrams e.g kol kol kol would lead to a single 
                // ngram of 'kol' from the end
                if (ngramSize == i) {
                    ls.add(ngram);
                }
            }
            linepos = 0;
            ngrams.add(ls);
            ls = new ArrayList<>();
        }
        return ngrams;
    }
    /**
     * Creates a flat list of ngrams where bi, tri etc are in a single list
     * @param line
     * @param range
     * @return 
     */
    public static List<String> createFlatNgrams(String line, int range) {
        List<String> ngrams = new ArrayList<>();
        int startingNgram = 2;
        List<String> lineLs = GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line);
        int linepos = 0;
        int ngramSize = 0;
        for (int i = startingNgram; i <= (range + startingNgram); i++) {
            for (String st : lineLs) {
                String ngram = "";
                if (linepos == lineLs.size()) {
                    break;
                }
                boolean first = true;
                ngramSize = 0;
                for (int j = 0; j < i; j++) {
                    if (linepos < lineLs.size() && (linepos + j) < lineLs.size()) {
                        if (first) {
                            ngram += lineLs.get(linepos) + " ";
                            first = false;
                            ngramSize++;
                        } else if (j < i - 1) {
                            ngram += lineLs.get(linepos + j) + " ";
                            ngramSize++;
                        } else {
                            ngram += lineLs.get(linepos + j);
                            ngramSize++;
                        }
                    }
                }
                ngram = ngram.trim();
                first = true;
                linepos++;
                // Adds if ngram is either n-1 or n length
                // ngramSize == i -1  ||  this is where end of sentence would lead to unigrams e.g kol kol kol would lead to a single 
                // ngram of 'kol' from the end
                if (ngramSize == i) {
                    ngrams.add(ngram);
                }
            }
            linepos = 0;
        }
        return ngrams;
    }
    
    /**
     * Check for the length of Ngram to be correct
     * @param ngram
     * @param gram
     * @return 
     */
    public static boolean checkLength(String ngram, int gram) {
        int l = ngram.split(" ").length;
        boolean t = l == gram - 1 || l == gram;
        return t;
    }
    // TODO Need skipgrams?
}
