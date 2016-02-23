/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Global.Options;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class GeneralUtilities {
            public static Splitter guavaSplitterWhiteSpace = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();;
            public static HashFunction murmur128 = Hashing.murmur3_128(Options.MURMURSEED);
}
