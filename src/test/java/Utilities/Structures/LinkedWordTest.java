/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class LinkedWordTest {

    public LinkedWordTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of createLinkedWords method, of class LinkedWords.
     */
    @Test
    public void testLinkedWord() {
        System.out.println("createLinkedWords");

        List<String> line = new ArrayList<>();
        line.add("Ab");
        line.add("Ba");
        List<String> line2 = new ArrayList<>();
        line2.add("Ba");
        line2.add("Ab");
        line2.add("Baa");
        line2.add("Abb");
        List<String> line3 = new ArrayList<>();
        for (String s : line) {
            String s2 = s + " "; // Immutable, so this way we dont use pointer
            s2 = s2.trim();
            line3.add(s2);
        }
        LinkedWord lw1 = LinkedWords.createLinkedWords(line);
        LinkedWord lw2 = LinkedWords.createLinkedWords(line2);
        LinkedWord lw3 = LinkedWords.createLinkedWords(line3);
        Assert.assertEquals(lw1, lw1);
        Assert.assertFalse(lw1.equals(lw2));
        Assert.assertEquals(line2.size(), lw2.toString().split(" ").length);
        Assert.assertEquals(lw1, lw3);
    }

    /**
     * Test of getWord method, of class LinkedWord.
     */
    @Test
    public void testGetWord() {
    }

    /**
     * Test of getTail method, of class LinkedWord.
     */
    @Test
    public void testGetTail() {
    }

    /**
     * Test of append method, of class LinkedWord.
     */
    @Test
    public void testAppend() {
    }

    /**
     * Test of hasNext method, of class LinkedWord.
     */
    @Test
    public void testHasNext() {
    }

    /**
     * Test of toString method, of class LinkedWord.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of equals method, of class LinkedWord.
     */
    @Test
    public void testEquals() {
    }

    /**
     * Test of hashCode method, of class LinkedWord.
     */
    @Test
    public void testHashCode() {
    }

}
