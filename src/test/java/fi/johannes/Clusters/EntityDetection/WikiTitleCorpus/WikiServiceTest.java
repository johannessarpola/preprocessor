/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection.WikiTitleCorpus;

import fi.johannes.Clusters.EntityDetection.WikiTitleCorpus.WikiCorpus;
import fi.johannes.Clusters.EntityDetection.WikiTitleCorpus.WikiTransformer;
import fi.johannes.Core.App;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikiServiceTest {

    private WikiCorpus corpus;
    private static String pathtowikis;
    public WikiServiceTest() {
        corpus = new WikiCorpus(pathtowikis);
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        pathtowikis = App.getResource("wiki").getFile().getAbsolutePath();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testBloomfilterWithoutProcessing() {
        String[] contains = {"Finland", "Sentence_function", "Sentence_stress"};
        String[] shouldnotcontain = {"Johannes Sarpola is the best"};
        for (String s : contains) {
            assertThat("bloom filter contains", corpus.mightContain(WikiTransformer.transformTitle(s)), equalTo(true));
        }
        for (String s : shouldnotcontain) {
            assertThat("bloom filter contains", corpus.mightContain(WikiTransformer.transformTitle(s)), equalTo(false));
        }
    }

}
