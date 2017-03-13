/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Compression;

import Utilities.Structures.LinkedWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is legacy class and not used anymore
 * 
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StringUncompressor {
    
     /**
     * Rebuilds the string representations from compressed term frequencies
     * @return
     */
    static Map<String, Double> rebuildDocument(List<String> items, Map<Integer, Double> orig) {
        Map<String, Double> ret = new HashMap<>();
        for (Entry<Integer, Double> e : orig.entrySet()) {
            String w = items.get(e.getKey());
            ret.put(w, e.getValue());
        }
        return ret;
    }
    /**
     * Translates LinkedWord,Double to String,Double
     * @param compr
     * @return 
     */
    static Map<String, Double> castToString(Map<LinkedWord, Double> compr) {
        Map<String, Double> u = new HashMap<>();
        for(Entry<LinkedWord, Double> e : compr.entrySet()){
            u.put(e.getKey().toString(), e.getValue());
        }
        return u;
    }
}
