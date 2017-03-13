/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Sorting;

import java.util.Comparator;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StringComparators {
    /**
     * Comparator for case-insensitive alphabetical.
     */
    public static Comparator<String> ALPHABETICAL_ORDER = (String str1, String str2) -> {
        int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
        if (res == 0) {
            res = str1.compareTo(str2);
        }
        return res;
    };
    
}
