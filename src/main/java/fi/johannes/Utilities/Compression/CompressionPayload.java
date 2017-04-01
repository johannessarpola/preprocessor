/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Compression;

import fi.johannes.Utilities.Structures.LinkedWord;

import java.util.List;
import java.util.Map;

/**
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CompressionPayload {

    /**
     * Data class to return the compression result
     */
    private final List<Map<LinkedWord, Double>> compressedTermFrequencies;
    private final Map<LinkedWord, Double> compressedIdfMap;
    private final List<String> allWords;
    private final Map<String, List<LinkedWord>> wordToNgramsMapping; // Map to a Word to Ngram(s)

    public CompressionPayload(List<Map<LinkedWord, Double>> result,
                              List<String> items, Map<String,
            List<LinkedWord>> mapping,
                              Map<LinkedWord, Double> compressedIdfMap) {
        this.compressedTermFrequencies = result;
        this.allWords = items;
        this.wordToNgramsMapping = mapping;
        this.compressedIdfMap = compressedIdfMap;
    }

    public List<Map<LinkedWord, Double>> getCompressedTermFrequencies() {
        return compressedTermFrequencies;
    }

    public List<String> getAllWords() {
        return allWords;
    }

    public Map<String, List<LinkedWord>> getWordToNgramsMapping() {
        return wordToNgramsMapping;
    }

    public Map<LinkedWord, Double> getCompressedIdfMap() {
        return compressedIdfMap;
    }

}