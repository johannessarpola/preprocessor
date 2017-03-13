/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Compression;

import Utilities.Structures.LinkedWord;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
 public class CompressionPayload {

        /**
         * Data class to return the compression result
         */
        private final List<Map<LinkedWord, Double>> compressedDocuments;
        private final List<String> items;
        private final Map<String, List<LinkedWord>> mapping; // Map to a Word to Ngram(s)
        private final Map<LinkedWord, Double> compressedIdfMap;

        public CompressionPayload(List<Map<LinkedWord, Double>> result, 
                List<String> items, Map<String, 
                List<LinkedWord>> mapping, 
                Map<LinkedWord, Double> compressedIdfMap) 
        {
            this.compressedDocuments = result;
            this.items = items;
            this.mapping = mapping;
            this.compressedIdfMap = compressedIdfMap;
        }

        public List<Map<LinkedWord, Double>> getCompressedDocuments() {
            return compressedDocuments;
        }

        public List<String> getItems() {
            return items;
        }

        public Map<String, List<LinkedWord>> getMapping() {
            return mapping;
        }

        public Map<LinkedWord, Double> getCompressedIdfMap() {
            return compressedIdfMap;
        }

    }