/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor;

import com.google.common.collect.Multiset;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.TreeMap;

/**
 *
 * @author Johannes töissä
 */
public class ArticleProcessorTest {

    public static ArticleProcessor p;

    public ArticleProcessorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        p = new ArticleProcessor();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of processLine method, of class ArticleProcessor.
     */
    @Test
    public void testGetStem() {
        System.out.println("processLine");
        String line = "weakness";
        String expResult = "weak";
        String line2 = "powerful";
        String expResult2 = "power";

        String result = p.getStem(line);
        String result2 = p.getStem(line2);
        Assert.assertTrue(expResult.equals(result));
        Assert.assertTrue(expResult2.equals(result2));

    }

    /**
     * Test of processLine method, of class ArticleProcessor.
     */
    @Test
    public void testProcessLine() {
        System.out.println("processLine");
        String line = "Since it's raining, it would be better it you stayed at home. I agree with you absolutely. He translated the verse into English.";
        String[] expResult = {"rain", "stai", "home", "agre", "absolut", "translat", "vers", "english"};
        Multiset<String> result = p.processLineToMultiset(line);
        for (String s : expResult) {
            Assert.assertTrue(result.contains(s));
        }
        Assert.assertTrue(expResult.length == result.size());
    }

    @Test
    public void testProcessWord() {
        String[] shouldNotPass = {"www.google.fi", "http://www.kuppi.fi", "<link>", "[Banavenue]", "[[.}}", "a"};
        String[] shouldPass = {"Dog", "Cat", "googl", "link", "banana"}; // Google get trasnformed to googl by stemmer
        TreeMap<String, String> shouldTransform = new TreeMap<>();
        shouldTransform.put(">Cat", "cat");
        shouldTransform.put("Doggyness", "doggy"); // Doesnt remove 2Doggy3 numbers from around the word
        shouldTransform.put("do//h", "doh");
        shouldTransform.put("agree", "agre");

        for (String s : shouldNotPass) {
            s = p.processWord(s);
            Assert.assertEquals(null, s);
        }
        int i = 0;
        for (String s : shouldPass) {
            s = p.processWord(s);
            Assert.assertEquals(shouldPass[i].toLowerCase(), s);
            i++;
        }
        for (String s : shouldTransform.keySet()) {
            String kStemmed = p.processWord(s);
            Assert.assertEquals(shouldTransform.get(s), kStemmed);
        }
        //p.processWord(null)
    }
    @Test
    public void testLemma(){
        p.getStates().setUseStanfordLemmatizer(true);
        String t = "Dog is painting a painting";
        String e = "dog paint painting";
        String[] eT = e.split(" ");
        String r = p.processLineToString(t);
        String[] rT = r.split(" ");
        for(String s : eT) {
            int hit = 0;
            System.out.println(s);
            for(String s2 : rT){
                System.out.print(s2);
                if(s2.equalsIgnoreCase(s)){
                    hit++;
                }
            }
            Assert.assertEquals(1, hit);
            hit = 0;
            
        }
        p.getStates().setUseStanfordLemmatizer(false);
    }
}
