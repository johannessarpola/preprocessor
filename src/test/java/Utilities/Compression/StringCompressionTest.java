/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Compression;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

/**
 * TODO Finish this
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StringCompressionTest {

    public StringCompressionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of compressTermFrequencies method, of class Compressor.
     */
    @Test
    public void testCompressTermFrequencies() {
        System.out.println("compressTermFrequencies");
        Map<String, Double> m1 = new HashMap<>();
        Map<String, Double> m2 = new HashMap<>();

        m1.put("String", 1.0);
        m1.put("String", 1.0);

        m2.put("bigram bigram", 1.0);
        m2.put("trigram trigram trigram", 1.0);
        m2.put("quadgram quadgram quadgram quadgram", 1.0);
        m2.put("ungram", 1.0);

        List<Map<String, Double>> freqs = new ArrayList<>();

        CompressionPayload expResult = null;
        //Compressor.CompressionPayload result = Compressor.compressTermFrequencies(freqs);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    @Test
    public void testDupl() {
        Random r = new Random();
        Set<Integer> integs = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Integer integ = r.nextInt(5);
            integs.add(integ);
        }
        System.out.println(integs.size());
    }
}
