/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Map;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapUtils {

    public static <K, V extends Comparable<? super V>> Map<K, V>
            sortByValue(Map<K, V> map) {
        return sortByValue(map, false);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V>
            sortByValue(Map<K, V> map, final boolean reverse) {
        List<Map.Entry<K, V>> list
                = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                if (reverse) {
                    return -(o1.getValue()).compareTo(o2.getValue());
                } else {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            }
        });
        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
