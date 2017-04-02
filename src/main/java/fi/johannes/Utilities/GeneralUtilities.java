/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities;

import fi.johannes.Core.App;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class GeneralUtilities {
            public static Splitter guavaSplitterWhiteSpace = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();;
            public static HashFunction murmur128 = Hashing.murmur3_128(App.MURMURSEED);

    /**
     * Splits with white list, uses GuavaSplitter (Google)
     * @param line
     * @return
     */
    public static List<String> splitWithWhitespace(String line) {
        return GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line);
    }
}
