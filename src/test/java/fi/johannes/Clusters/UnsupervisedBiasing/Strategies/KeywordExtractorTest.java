/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.UnsupervisedBiasing.Strategies;

import fi.johannes.Abstractions.Core.Cluster;
import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Core.App;
import fi.johannes.Core.ArticleProcessor;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class KeywordExtractorTest {
     // TODO Needs to serialize the TF.IDF set to allow for more dynamic addition without going through all the documents all over again
    static GenericService instance;
    static ArticleProcessor apr;
    static Cluster TFIDFCluster;

    public KeywordExtractorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instance = new KeywordExtractor();
        apr = new ArticleProcessor();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of build method, of class KeywordExtractor.
     */
    @Test
    public void testAddVocabulary_Iterable() {
    }

    /**
     * Test of getInstance method, of class KeywordExtractor.
     */
    @Test
    public void testBuildService() {
    }

    /**
     * Test of selectProcessing method, of class KeywordExtractor.
     */
    @Test
    public void testSelectProcessing() {
    }

    /**
     * Test of processLine method, of class KeywordExtractor.
     */
    @Test
    public void testAppend() throws ServiceNotReadyException, UnhandledServiceException {
        //pw.selectProcessing(Options.SemanticProcessingStrategy.TFIDF);
        List<String> ls = new ArrayList<>();
        ls.add("Dog Cat Bird");
        ls.add("Dog Cat Horse");
        ls.add("Giraffe Cat Dog");
        String[] expect = {"Bird", "Horse", "Giraffe"};
        instance.build(ls);
        Assert.assertTrue(instance.isServiceReady());
        List<String> results = new ArrayList<>();
        if (instance.isServiceReady()) {
            int i = 0;
            //pw.setBiasingSize(1);
            for (String st : ls) {
                String s = instance.processLine(st, App.SupportedProcessingMethods.Append, 1);
                System.out.println(i + " : " + s);
                Assert.assertEquals(st + " " + expect[i] + " ", s);
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

    @Test
    public void testReplace() throws ServiceNotReadyException, UnhandledServiceException {
        List<String> ls = new ArrayList<>();
        ls.add("Spanish mackerel spawn in oceanic conditions on reef edges. Eggs have a large oil droplet that aids in buoyancy and keeps them at the top of the water column which is warmer, well oxygenated, and has an abundant planktonic food supply for the larvae once they are hatched. When in the larval stage, Spanish mackerel are believed to stay in their own species-specific groups and are not normally found with other species of the same genus, such as S. semifasciatus and S. queenslandicus. This is not always the case with adult mackerel, where occasional mixing of different species within the same genus can occur. Spawning is seasonal, but it is protracted in the warmer waters of the tropics. Many of the fisheries that target this species are based on prespawning feeding aggregations. A significant proportion of the female fish caught in NT waters between July and December have either recently spawned or are close to spawning.[4] In general, spawning times for Spanish mackerel tend to be associated with higher water temperatures that promote optimal food availability for the rapid growth and development of the larvae. As the young larvae grow, they move from the offshore spawning grounds to inshore and estuarine habitats, where they are frequently found in the juvenile phase of their growth cycle. In the inshore environments, they feed mostly on the larvae and juveniles of small fish and crustaceans until they become large enough to eat small fish and squid.[6] Australian studies of this species suggest females are larger than males.[7][8] [9] Female Spanish mackerel mature at about two years of age or around 80 cm in length");
        ls.add("Spanish mackerel spawn in oceanic conditions on reef edges. Eggs have a large oil droplet that aids in buoyancy and keeps them at the top of the water column which is warmer, well oxygenated, and has an abundant planktonic food supply for the larvae once they are hatched.");
        ls.add("The wagonway was the earliest form of railway. Although modern historians are uncertain as to whether it evolved or was invented, it is known that, between the Autumn of 1603 and 1 October 1604, a waggonway (wagonway) had been built near Nottingham, by Huntingdon Beaumont who was the partner of Sir Percival Willoughby, the local land-owner and owner of Wollaton Hall. It ran for approximately two miles (3 km) from Strelley to Wollaton to assist the haulage of coal. The actual track gauge is unknown, some websites state it was 4 ft 6 in (1,372 mm) [3] however no documentary evidence exists to support such statements although Lewis' work (1970) on early wooden railways, and the practicalities of horse haulage, suggest a gauge close to that dimension is plausible. Earlier waggonways may have been built, but the Wollaton Wagonway is the earliest surface-level waggonway on record anywhere in the world, and therefore remains credited as the first.");
        ls.add("Earl Carlton \"Irish\" Krieger[1] (c. 1896 – November 10, 1960) was an American football and basketball player, coach of football, basketball, and baseball, and official in football and basketball. He was the third head football coach at Bowling Green State Normal School—now Bowling Green State University—serving for one season in 1921 and compiling a record of 3–1–1. Krieger was also the head basketball coach at Bowling Green State Normal during the 1921–22 season, tallying a mark of 4–10, and the school's head baseball coach in the spring of 1922, notching a record of 7–1. Krieger played college football at Ohio University, from which he graduated in 1920. In addition to coaching at Bowling Green, he was also a member of the football coaching staffs at his alma mater and at the University of Tennessee. For 25 years until his retirement in 1953, Krieger worked as a football and basketball official for the Big Ten Conference. He was also a member of the National Collegiate Athletic Association's football rules committee. Krieger died at the age of 64 on November 10, 1960 at his home in Columbus, Ohio.[2]");
        ls.add("Earl Carlton \"Irish\" Krieger[1] (c. 1896 – November 10, 1960) was an American football and basketball player, coach of football, basketball, and baseball, and official in football and basketball. He was the third head football coach at Bowling Green State Normal School—now Bowling Green State University—serving for one season in 1921 and compiling a record of 3–1–1. Krieger was also the head basketball coach at Bowling Green State Normal during the 1921–22 season, tallying a mark of 4–10, and the school's head baseball coach in the spring of 1922, notching a record of 7–1. Krieger played college football at Ohio University, from which he graduated in 1920. In addition to coaching at Bowling Green, he was also a member of the football coaching staffs at his alma mater and at the University of Tennessee. For 25 years until his retirement in 1953, Krieger worked as a football and basketball official for the Big Ten Conference. He was also a member of the National Collegiate Athletic Association's football rules committee.");
        ls.add("Brown has been bequeathed a hotel in the capital from his late British mother, but has been unable to sell it in his trip to New York City. Brown also has an ongoing affair with Martha (Elizabeth Taylor), the German-born wife of the Uruguayan ambassador to Haiti, Pineda (Peter Ustinov). When Martha and Brown have an argument, Brown goes to Mere Catherine's brothel where he discovers that not only has Jones been released, but he's a guest of Captain Concasseur and is enjoying the hospitality of Brown's favourite tart, Marie Therese (Cicely Tyson).Jones has gained the favour of the new regime, who are keen to receive a supply of arms. They have paid a down payment, and Jones claims the weapons are impounded in a warehouse in Miami, but the weapons may be imaginary and a confidence trick by Jones. The government will not allow Jones to leave the island until they are sure the weapons exist.Mr. Smith, a former \"Vegetarian Party\" candidate for the Presidency of the United States against Harry S. Truman, is given a tour of the new capital, an empty shambles called Duvalierville. He and Mrs. Smith follow a local procession that they believe is a religious ceremony but turns out to be an audience for executions by firing squad. Captain Concasseur and his men enter Brown's hotel and beat him up until Mrs. Smith bluffs the thugs by threatening to inform her husband, the American \"presidential candidate.\" The Smiths depart the next day.");
        int i = 0;
        List<String> lspr = new ArrayList<>();
        
        // Stems and removes stopwords
        for (String s : ls) {
            String pr = apr.processLineToString(s);
            Assert.assertTrue(pr.length() < s.length());
            lspr.add(i, pr);
            i++;
        }
        instance.build(lspr);
        Assert.assertTrue(instance.isServiceReady());
        int sizeofbiasing = 15;
        //pw.setBiasingSize(sizeofbiasing);
        ls = null;
        List<String> result = new ArrayList<>();
        for (String l : lspr) {
            // TODO Figure out automatic way to test this
            String ll = instance.processLine(l, App.SupportedProcessingMethods.Replace, sizeofbiasing);
            result.add(ll);
            System.out.println(ll);
            String[] la = ll.split(" ");
            Assert.assertEquals(sizeofbiasing, la.length);
        }
        // Test for only singular words
        for (String r : result) {
            ArrayList<String> words = new ArrayList<>();
            ArrayList<String> words2 = new ArrayList<>();
            words.addAll(Arrays.asList(r.split(" ")));
            words2.addAll(Arrays.asList(r.split(" ")));
            //wordsAll.add(words);
            //int times = 0;
            for (String w : words2) {
                int indx = 0;
                int counter = 0;
                while (indx != -1) {

                    indx = words.indexOf(w);
                    if (indx == -1) {
                        break;
                    }
                    if (indx != -1) {
                        words.remove(indx);
                    }
                    counter++;
                    Assert.assertTrue(counter < 2);
                }

            }
        }

        instance.clear(); // TODO doesnt fully work atm, some null pointers
        Assert.assertFalse(instance.isServiceReady());
    }

    /**
     * Test of getName method, of class KeywordExtractor.
     */
    @Test
    public void testGetName() {
    }

    /**
     * Test of isClusterReady method, of class KeywordExtractor.
     */
    @Test
    public void testIsServiceReady() {
    }

    /**
     * Test of build method, of class KeywordExtractor.
     */
    @Test
    public void testAddVocabulary_0args() {
    }

    /**
     * Test of clear method, of class KeywordExtractor.
     */
    @Test
    public void testPurge() {
    }

}
