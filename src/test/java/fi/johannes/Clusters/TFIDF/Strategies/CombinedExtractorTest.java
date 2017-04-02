/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.TFIDF.Strategies;

import fi.johannes.Abstractions.Core.GenericCluster;
import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Core.App;
import fi.johannes.Core.ArticleProcessor;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CombinedExtractorTest {
    static GenericService instance;
    static ArticleProcessor apr;
    static GenericCluster TFIDFCluster;
    
    public CombinedExtractorTest() {
        instance = new CombinedExtractor();
        apr = new ArticleProcessor();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }


    /**
     * Test of processLineByReplace method, of class CombinedExtractor.
     */
    @Test
    public void testProcessLineByReplace() throws Exception {
         //pw.selectProcessing(Options.SemanticProcessingStrategy.TFIDF);
        List<String> ls = new ArrayList<>();
        ls.add("Dog Cat Bird");
        ls.add("Dog Cat Horse");
        ls.add("Giraffe Cat Dog");
        ls.add("Dog Cat Cat Horse Giraffe Cat Penguin");
        ls.add("Dog Cat Cat Horse Giraffe Cat Elephant");
        String[] expect = {"Dog Cat Bird", "Dog Cat Horse", "Giraffe Cat Dog", "Cat", "Cat"};
        instance.build(ls);
        Assert.assertTrue(instance.isServiceReady());
        List<String> results = new ArrayList<>();
        if (instance.isServiceReady()) {
            int i = 0;
            //pw.setBiasingSize(1);
            for (String st : ls) {
                String s = instance.processLine(st, App.SupportedProcessingParadigms.Append, 1);
                s = s.substring(st.length()+1, s.length());
                System.out.println(i + " : " + s);
                Assert.assertEquals(expect[i], s);
                i++;
                results.add(s);
            }
            Assert.assertTrue(results.size() == ls.size());
        } else {
            System.out.println("Services wasn't ready");
        }

        instance.clear();
        Assert.assertFalse(instance.isServiceReady());
    }


}
