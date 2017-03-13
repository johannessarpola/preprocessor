/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.String;

import com.google.common.base.CharMatcher;

/**
 * Holds operations for Strings
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StringOperations {

    public static boolean regionMatch(String string, String partOfString) {
        int originalStringLength = string.length();
        int partOfStringLength = partOfString.length();
        for (int i = 0; i <= (originalStringLength - partOfStringLength); i++) {
            if (string.regionMatches(i, partOfString, 0, partOfStringLength)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Removes words like [word] or <word>
     *
     * @param token
     * @return
     */
    public static String removeTags(String token, CharMatcher charmatcher) {
        if (charmatcher.countIn(token) >= 2) {
            char startChar = token.charAt(0);
            char endChar = token.charAt(token.length() - 1);
            if (charmatcher.matches(startChar) && charmatcher.matches(endChar)) {
                return "";
            }
        }
        return token;
    }
    public static String rmCharAt(int index, String string){
        StringBuilder sb = new StringBuilder(string);
        sb.deleteCharAt(index);
        return sb.toString();
    }

}
