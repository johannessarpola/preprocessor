/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.WikipediaTitles;

import fi.johannes.Clusters.EntityDetection.WikipediaTitles.WikiCorpus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
// TODO Need to import the Wikititles
@Ignore public class WikiServiceTest {

    WikiCorpus wise;

    public WikiServiceTest() {
        wise = new WikiCorpus();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testBloomfilterWithoutProcessing() {
        String[] contains = {"Finland", "Sentence_function", "Sentence_stress"};
        String[] shouldnotcontain = {"Johannes Sarpola is the best"};
        for (String s : contains) {
            assertThat("bloom filter contains", wise.mightContain(s), equalTo(true));
        }
        for (String s : shouldnotcontain) {
            assertThat("bloom filter contains", wise.mightContain(s), equalTo(false));
        }
    }

}
