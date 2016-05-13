/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class GeneralUtilityMethods {
    /**
     * Splits with white list, uses GuavaSplitter (Google)
     * @param line
     * @return 
     */
    public static List<String> splitWithWhitespace(String line) {
        return GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line);
    }
}
