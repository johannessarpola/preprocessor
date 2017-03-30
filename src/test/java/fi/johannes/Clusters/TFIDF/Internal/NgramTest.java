/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.TFIDF.Internal;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class NgramTest {

    public NgramTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of createNgrams method, of class NgramTest.
     */
    @Test
    public void testCreateNgrams() {
        String t = "Test1 test2 test3 test4";
        String[] bigrams = {"Test1 test2", "test2 test3"};
        String[] trigrams = {"Test1 test2 test3", "test2 test3 test4"};
        String[] quadgrams = {"Test1 test2 test3 test4"};
        int i = 0;
        int j = 0;
        int ngramiter = 0;
        List<List<String>> ll = Ngram.createNgrams(t, 2);
        Assert.assertEquals(bigrams[0], ll.get(0).get(0));
        Assert.assertEquals(bigrams[1], ll.get(0).get(1));
        Assert.assertEquals(trigrams[0], ll.get(1).get(0));
        Assert.assertEquals(trigrams[1], ll.get(1).get(1));
        Assert.assertEquals(quadgrams[0], ll.get(2).get(0));
    }

    /**
     * Test of checkLength method, of class NgramTest.
     */
    @Test
    public void testCheckLength() {
        System.out.println("checkLength");
        String bigram = "bi bi";
        String bigram2 = "bi";
        String trigram = "Tri tri tri";
        String trigram2 = "Tri tri";
        String quadgram = "quad quad quad quad";
        String quadgram2 = "quad quad quad";
        int b = 2;
        int t = 3;
        int q = 4;
        Assert.assertTrue(Ngram.checkLength(bigram, b));
        Assert.assertTrue(Ngram.checkLength(bigram2, b));
        Assert.assertTrue(Ngram.checkLength(trigram, t));
        Assert.assertTrue(Ngram.checkLength(trigram2, t));
        Assert.assertTrue(Ngram.checkLength(quadgram, q));
        Assert.assertTrue(Ngram.checkLength(quadgram2, q));
        Assert.assertFalse(Ngram.checkLength(quadgram2, b));
        Assert.assertFalse(Ngram.checkLength(bigram2, q));
    }

}
