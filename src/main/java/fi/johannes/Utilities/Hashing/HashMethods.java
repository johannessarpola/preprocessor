/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Hashing;

import fi.johannes.Utilities.GeneralUtilities;

import java.util.List;

import static fi.johannes.Utilities.GeneralUtilities.guavaSplitterWhiteSpace;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class HashMethods {
    /**
     * Splits the line according to whitespace and uses sizeToDigest number of items in the hash
     * @param line
     * @param sizeToDigest
     * @return 
     */
    static String createHashKey(String line, int sizeToDigest) {
        List<String> lineSplit = guavaSplitterWhiteSpace.splitToList(line);
        int i = 0;
        return createHashKey(lineSplit, sizeToDigest);
    }
    /**
     * Deducts key for line of strings based on the sizeToDigest as pointer to length
     * ohn how many words to use (Murmur128)
     * @param line
     * @param sizeToDigest
     * @return 
     */
    static String createHashKey(List<String> line, int sizeToDigest) {
        String digestation = "";
        if (line.size() <= sizeToDigest) {
            sizeToDigest = line.size() - 1;
        }
        for (int i = 0; i <= sizeToDigest; i++) {
            digestation += line.get(i);
        }
        String key = makeKey(digestation);
        return key;
    }

    private static String makeKey(String keyStr) {
        String key = GeneralUtilities.murmur128.hashUnencodedChars(keyStr).toString();
        return key;
    }
}
