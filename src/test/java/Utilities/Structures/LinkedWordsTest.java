/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import Utilities.Structures.LinkedWord;
import Utilities.Structures.LinkedWords;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class LinkedWordsTest {
    
    public LinkedWordsTest() {
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
    public void testCreateLinkedWords() {
        System.out.println("createLinkedWords");
        String[] line = {"Word1", "Word2", "Word3"};
        LinkedWord lw = LinkedWords.createLinkedWords(line);
        LinkedWord current = LinkedWords.createLinkedWords(line); 
        for(String st : line) {
            Assert.assertEquals(st, lw.getWord());
            if(lw.hasNext()) {
                lw = lw.getTail();
            }
        }
        Assert.assertEquals("Word1 Word2 Word3", current.toString());
    }
    
}
