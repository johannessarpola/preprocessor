/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.Strategies;

import Clusters.SupervisedBiasing.Internal.StringTableHierarchy;
import Utilities.Logging.CustomExceptions.UnevenSizedListsException;
import Utilities.Structures.FinalizedPair;
import Utilities.Structures.SortedListPair;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola at gmail.com>
 */
public class TableBiasingServiceMethodsTest {

    List<String> ra;
    List<Double> rb;
    List<Double> b;
    SortedListPair<String, Double> slist;

    public TableBiasingServiceMethodsTest() {
    }
    @Before
    public void setUp() throws UnevenSizedListsException {
        ra = Arrays.asList("First", "Third", "Second", "Fourth");
        rb = Arrays.asList( 1., 3., 7.,10.);
        b = Arrays.asList(10.,7.,3.,1.);
        slist = TableBiasingServiceMethods.makeListPair(ra, rb);

        
    }


    /**
     * Test of makeListPair method, of class TableBiasingServiceMethods.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testMakeListPair() {
        System.out.println("makeListPair");
        assertEquals(slist.size(), rb.size());
        FinalizedPair<String, Double> firstpair = slist.getPair(2);
        FinalizedPair<String, Double> ndpair = slist.getPair(0);
        assertEquals(b.get(2), firstpair.getValue(), 0.);
        assertEquals(b.get(0), ndpair.getValue(), 0.);

    }

    /**
     * Test of getHighestWords method, of class TableBiasingServiceMethods.
     */
    @Test
    public void testGetHighestWords() throws UnevenSizedListsException {
        List<FinalizedPair<String,Double>> lfp = TableBiasingServiceMethods.getHighestWords(ra,rb, 1);
        assertEquals(b.get(0), lfp.get(0).getValue(), 0.);
        assertEquals(ra.get(3),lfp.get(0).getItem());
        
    }

    /**
     * Test of getWeightsForLine method, of class TableBiasingServiceMethods.
     */
    @Test
    public void testGetWeightsForLine() {
        // TableBiasingServiceMethods.getWeightsForLine(ra, rb);
        // TODO This test
    }

}
