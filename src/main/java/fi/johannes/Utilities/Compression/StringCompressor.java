/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Compression;

import fi.johannes.Utilities.GeneralUtilities;
import fi.johannes.Utilities.Structures.LinkedWord;
import fi.johannes.Utilities.Structures.LinkedWordsGenerator;

import java.util.*;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StringCompressor {
    // TODO COmpress even futher by removing all tfidf scores below avg
    
    public static CompressionPayload compressTermFrequencies(List<Map<String, Double>> freqs, Map<String, Double> idf) {
        return compressTermFrequencies(freqs, idf, false);
    }

    /**
     * Should compress the strings and remove duplicates result has mapping from
     * word -> list for ngrams e.g 'Word' in Ngram 'Word is the bird' or 'Word
     * is the herd' should serviceMap to both ngrams This should allow for fast access
     * from word level to ngram
     *
     * By using a Set and same String object there should be no excess
     * duplicates of words in memory for Ngram either, thanks Java.
     *
     * @param freqs
     * @param idf
     * @return CompressionPayload
     */
    public static CompressionPayload compressTermFrequencies(List<Map<String, Double>> freqs, Map<String, Double> idf, boolean doMapping) {
        Set<String> wordSet = new HashSet<>();
        Map<String, List<LinkedWord>> wordToNgramMapping = new HashMap<>(); // Holds String to LWs, as a word can have multiple ngrams
        Map<LinkedWord, Double> compressedIdfMap = new HashMap<>();
        List<Map<LinkedWord, Double>> compressedDocs = new ArrayList<>(freqs.size());
        Map<String, LinkedWord> usedLinkedWords = new HashMap<>(); // Holds pointer from String to LW, hashmap is used for fast 'contains()'
        Set<String> usedIdfs = new HashSet<>();

        for (Map<String, Double> map : freqs) {
            Map<LinkedWord, Double> linkedWordMap = new HashMap<>();
            // Go through all the documents
            for (String st : map.keySet()) {
                // Each ngrm / word
                List<String> words = splitByWhiteSpace(st);
                wordSet.addAll(words); // Add to set > remove duplicates

                // Is either a word or words in ngram
                LinkedWord lw;
                if (!usedLinkedWords.containsKey(st)) {
                    lw = LinkedWordsGenerator.createLinkedWords(words);
                    usedLinkedWords.put(st, lw);
                } else {
                    lw = usedLinkedWords.get(st);
                }
                // Translate String, Double to LinkedWord, Double
                linkedWordMap.put(lw, map.get(st));

                // Creates linked list of ngram
                // Add to idfMap the LinkedWord and idfvalue
                if (!usedIdfs.contains(st)) {
                    compressedIdfMap.put(lw, idf.get(st));
                }
                usedIdfs.add(st);
                if (doMapping) {
                    // TODO There's a problem with mapping from words to ngrams
                    doMapping(words,lw,wordToNgramMapping);
                }
            }
            // Add to pooled docs
            compressedDocs.add(linkedWordMap);
        }
        CompressionPayload cr = new CompressionPayload(compressedDocs, new ArrayList<>(wordSet), wordToNgramMapping, compressedIdfMap); // create payload by using pointers from Set
        return cr;
    }

    private static void doMapping(List<String> words, LinkedWord lw, Map<String, List<LinkedWord>> wordToNgramMapping) {
        words.stream().map((s) -> {
            if (!wordToNgramMapping.containsKey(s)) {
                // Initialize if the word is not added to mapping yet
                wordToNgramMapping.put(s, new ArrayList<>());
            }
            return s;
        }).forEach((s) -> {
            wordToNgramMapping.get(s).add(lw);
        });
    }

    public static List<String> removeTrailingSpaces(List<String> ls) {
        for (int i = 0; i < ls.size(); i++) {
            String s = ls.get(i).trim();
            ls.set(i, s);
        }
        return ls;
    }

    private static List<String> splitByWhiteSpace(String line) {
        return GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line);
    }
    

}
