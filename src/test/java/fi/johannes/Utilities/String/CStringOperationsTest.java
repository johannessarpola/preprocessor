/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.String;

import fi.johannes.Core.ArticleProcessor;
import com.google.common.base.CharMatcher;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CStringOperationsTest {

    static ArticleProcessor p;

    public CStringOperationsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        p = new ArticleProcessor();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of regionMatch method, of class StringOperations.
     */
    @Test
    public void testRegionMatch() {
        System.out.println("regionMatch");
        String url = "http://topnews.session.rservices.com";
        String urlId = "http://";
        String str = "financial results";
        String strP = "resul";
        String strP2 = "fnancial";

        Assert.assertTrue(StringOperations.regionMatch(url, urlId));
        Assert.assertTrue(StringOperations.regionMatch(str, strP));
        Assert.assertFalse(StringOperations.regionMatch(str, strP2));

    }

    /**
     * Test of removeTags method, of class StringOperations.
     */
    @Test
    public void testRemoveTags() {
        System.out.println("removeTags");
        String[] tokens = {"<word>", "[word]", "@[Word]"};

        CharMatcher cm = p.getCharMatcher();
        String expResult = "";
        for (String s : tokens) {
            String result = StringOperations.removeTags(s, cm);
            Assert.assertEquals(expResult, result);
        }

    }

}
