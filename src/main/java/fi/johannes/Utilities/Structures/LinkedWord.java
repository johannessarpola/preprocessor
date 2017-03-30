/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import java.util.Objects;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class LinkedWord {

    private String word;
    private LinkedWord tail;

    public LinkedWord(String word) {
        this.word = word;
        tail = null;
    }

    public String getWord() {
        return word;
    }

    public LinkedWord getTail() {
        return tail;
    }

    public void append(LinkedWord lw) {
        if (tail == null) {
            tail = lw;
        } else {
            LinkedWord n;
            n = tail;
            while (n.hasNext()) {
                n = n.getTail();
            }
            n.append(lw);
        }
    }

    public boolean hasNext() {
        return tail != null;
    }

    /**
     * Returns the whole linked list as a string. These LinkedWordsGenerator are
     * basically Strings (e.g. Ngrams) so we can use toString()
     *
     * @return
     */
    @Override
    public String toString() {
        String s = word;
        LinkedWord n = tail;
        if (n != null) {
            while (n.hasNext()) { //Get words between the second to second last
                s += " " + n.getWord();
                if (n.hasNext()) {
                    n = n.getTail();
                }
            }
            s += " " + n.getWord();
        }// Add the last word
        return s;
    }

    /**
     * Equals, if it's not LinkedWord it's not equals, if it is we use basic
     * String.equals() after toString()
     *
     * @param second
     * @return
     */
    @Override
    public boolean equals(Object second) {
        if (second instanceof LinkedWord) {
            LinkedWord lw = (LinkedWord) second;
            return this.toString().equals(lw.toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.word);
        LinkedWord n = tail;
        if (n != null) {
            while (n.hasNext()) { //Get words between the second to second last
                hash = 67 * hash + Objects.hashCode(n.getWord());
                if (n.hasNext()) {
                    n = n.getTail();
                }
            }
            hash = 67 * hash + Objects.hashCode(n.getWord());
        }// Add the last word
        return hash;
    }
}
