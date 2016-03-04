/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Map;

/**
 * Holds utilities for map operations
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUtils {

    /**
     * Sorts the map according to value (V) descending
     *
     * @param <K>
     * @param <V>
     * @param map Map to be sorted
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V>
            sortByValue(Map<K, V> map) {
        return sortByValue(map, false);
    }

    /**
     * Sorts the map according to value (V) either desc or asc, depending on the
     * boolean param
     *
     * @param <K>
     * @param <V>
     * @param map Map to be sorted
     * @param reverse either true = descending, false = ascending
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V>
            sortByValue(Map<K, V> map, final boolean reverse) {
        List<Map.Entry<K, V>> list
                = new LinkedList<>(map.entrySet());
        Collections.sort(list, (Map.Entry<K, V> o1, Map.Entry<K, V> o2) -> {
            if (reverse) {
                return -(o1.getValue()).compareTo(o2.getValue());
            } else {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Map<K, V> result = new LinkedHashMap<>();
        list.stream().forEach((entry) -> {
            result.put(entry.getKey(), entry.getValue());
        });
        return result;
    }
}
