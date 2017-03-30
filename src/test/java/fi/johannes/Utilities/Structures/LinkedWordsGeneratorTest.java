/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class LinkedWordsGeneratorTest {
    
    public LinkedWordsGeneratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of createLinkedWords method, of class LinkedWordsGenerator.
     */
    @Test
    public void testCreateLinkedWords() {
        System.out.println("createLinkedWords");
        String[] line = {"Word1", "Word2", "Word3"};
        LinkedWord lw = LinkedWordsGenerator.createLinkedWords(line);
        LinkedWord current = LinkedWordsGenerator.createLinkedWords(line);
        for(String st : line) {
            Assert.assertEquals(st, lw.getWord());
            if(lw.hasNext()) {
                lw = lw.getTail();
            }
        }
        Assert.assertEquals("Word1 Word2 Word3", current.toString());
    }
    
}
