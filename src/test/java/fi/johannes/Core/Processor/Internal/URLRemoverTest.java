/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core.Processor.Internal;

import fi.johannes.Utilities.Processing.URLRemover;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class URLRemoverTest {

    public URLRemoverTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of testForUrl method, of class URLRemover.
     */
    @Test
    public void testTestForUrl() {
        String[] urls = {"www.google.fi", "http://hs.fi", "http://stackoverflow.com/questions/285619/how-to-detect-the-presence-of-url-in-a-string"};
        String[] noturls = {"dog.dog.cat", "htp.dog.sos", "ttp/ananas", "http:\\::ptth", "//noturl"};
        for (String s : urls) {
            System.out.print(s + " ");
            boolean result = URLRemover.testForUrl(s);
            Assert.assertTrue(result);

        }
        for (String s : noturls) {
            System.out.print(s + " ");        
            boolean result = URLRemover.testForUrl(s);
            Assert.assertFalse(result);

        }

    }

}
