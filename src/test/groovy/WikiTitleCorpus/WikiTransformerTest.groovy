package fi.johannes.Clusters.EntityDetection.WikiTitleCorpus

import org.junit.Test

import static org.junit.Assert.*;

/**
 * Created by Johannes on 7.4.2017.
 */
class WikiTransformerTest {
    @Test
    void translateWikiTitle() throws Exception {
        List<String> collect = ["Abc_abc_(disamb)_as", "Bank_of_Nothing"].collect
                { s -> return WikiTransformer.transformTitle(s)}
        assertTrue(collect.contains("abc abc as"))
        assertTrue(collect.contains("bank of nothing"))

    }

    @Test
    void transformToWTitle() throws Exception {
    }

}