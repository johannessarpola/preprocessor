/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Adapters;

import Utilities.Structures.LinkedWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Translates different structures to String constructs
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StringTransforms {

    /**
     * Transfors map of LinkedWord,Double to String,Double
     *
     * @param map
     * @return
     */
    public static Map<String, Double> linkedWordMapToStringMap(Map<LinkedWord, Double> map) {
        Map<String, Double> sMap = new HashMap<>();
        for (Map.Entry<LinkedWord, Double> e : map.entrySet()) {
            sMap.put(e.getKey().toString(), e.getValue());
        }
        return sMap;
    }

    /**
     * List of LinkedWords to String with spaces
     *
     * @param ls
     * @return
     */
    public static String LinkedWordListToString(List<LinkedWord> ls) {
        String s = "";
        for (LinkedWord lw : ls) {
            s += " " + lw.toString();
        }
        return s;
    }

    /**
     * Map of LinkedWord,Double to String with spaces, uses only Keys
     * (LinkedWord)
     *
     * @param map
     * @return
     */
    public static String LinkedWordListToString(Map<LinkedWord, Double> map) {
        String s = "";
        for (Entry<LinkedWord, Double> e : map.entrySet()) {
            s += " " + e.getKey().toString();
        }
        return s;
    }

    /**
     * Map of LinkedWord,Double to List<String>, uses only Keys
     * (LinkedWord)
     *
     * @param map
     * @return
     */
    public static List<String> LinkedWordListToStringList(Map<LinkedWord, Double> map) {
        List<String> s = new ArrayList<>();
        for (Entry<LinkedWord, Double> e : map.entrySet()) {
            s.add(e.getKey().toString());
        }
        return s;
    }

}
