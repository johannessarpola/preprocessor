package fi.johannes.Clusters.EntityDetection.WikiTitleCorpus;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Johannes on 3.5.2017.
 */
public class WikiTransformerTest {
    @Test
    public void transformTitle() throws Exception {
        String a = "Rupashi_Bangla_Express";
        String b = "Ruotsinsalmi_(minelayer)";

        assertEquals("rupashi bangla express", WikiTransformer.transformTitle(a));
        assertEquals("ruotsinsalmi",WikiTransformer.transformTitle(b));

    }

}