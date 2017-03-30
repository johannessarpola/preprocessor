/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import fi.johannes.Utilities.GeneralUtilities;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class LinkedWordsGenerator {

    /**
     * Splits the line with whitespace and returns linked list of Strings
     *
     * @param line
     * @return
     */
    public static LinkedWord createLinkedWords(String line) {
        List<String> ls = GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line);
        return createLinkedWords(ls);
    }

    /**
     * Creates a LinkedWord
     *
     * @param line
     * @return
     */
    public static LinkedWord createLinkedWords(String[] line) {
        LinkedList<String> links = new LinkedList<>();
        links.addAll(Arrays.asList(line));
        return createLinkedWords(links);
    }

    /**
     * Creates a LinkedWord
     *
     * @param line
     * @return
     */
    public static LinkedWord createLinkedWords(List<String> line) {
        LinkedList<String> links = new LinkedList<>(line);
        return createLinkedWords(links);
    }

    /**
     * Creates a LinkedWord
     *
     * @param links
     * @return
     */
    public static LinkedWord createLinkedWords(LinkedList<String> links) {
        LinkedWord lw = null;
        LinkedWord latest = null;
        boolean first = true;
        while (!links.isEmpty()) {
            if (first) {
                lw = new LinkedWord(links.pop());
                first = false;
            }
            if (!links.isEmpty()) {
                latest = new LinkedWord(links.pop());
                lw.append(latest);
            }
        }
        return lw;
    }
}
