/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Processing;

import fi.johannes.Utilities.Processing.PartOfSpeechTagging;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class PartOfSpeechTaggingTests {

    static PartOfSpeechTagging spr;

    public PartOfSpeechTaggingTests() {
    }

    @BeforeClass
    public static void setUpClass() {
        spr = new PartOfSpeechTagging();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of annotate method, of class PartOfSpeechTagging.
     */
    @Test
    public void testGetPos() {
        String teststring = "This is a great day";
        Map<String, String> annotated = spr.getPos(teststring);
        for (Entry<String, String> e : annotated.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
            if (e.getKey().equalsIgnoreCase("day")) {
                Assert.assertEquals("NN", e.getValue());
            }
        }
    }

    @Test
    public void testgetSimplifiedPos() {
        String teststring = "This is a great day";
        Map<String, String> annotated = spr.getSimplifiedPos(teststring);
        for (Entry<String, String> e : annotated.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
            if (e.getKey().equalsIgnoreCase("day")) {
                Assert.assertEquals("Noun", e.getValue());
            }
            if (e.getKey().equalsIgnoreCase("is")) {
                Assert.assertEquals("Verb", e.getValue());
            }
        }
    }

}
