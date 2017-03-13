/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor.Internal;

import Utilities.Processing.Stopwords;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Johannes töissä
 */
public class StopwordsTest {
    
    public StopwordsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testClass() {
        int l = new Stopwords("stopwords.txt").getStopwords().size();
        Assert.assertTrue(l!=0);
    }
    
}
