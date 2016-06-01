/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.Strategies;

import Clusters.SupervisedBiasing.Internal.StringTableHierarchy;
import Utilities.Structures.FinalizedPair;
import Utilities.Structures.SortedListPair;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola at gmail.com>
 */
public class TableBiasingServiceMethodsTest {

    List<String> ra;
    List<Double> rb;
    List<Double> b;

    public TableBiasingServiceMethodsTest() {
    }

    @Before
    public void setUp() {
        ra = Arrays.asList("First", "Third", "Second", "Fourth");
        rb = Arrays.asList(10., 3., 7., 1.);
        b = Arrays.asList(10., 3., 7., 1.);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of makeListPair method, of class TableBiasingServiceMethods.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testMakeListPair() throws Exception {
        System.out.println("makeListPair");
        SortedListPair<String, Double> slist = TableBiasingServiceMethods.makeListPair(ra, rb);
        assertEquals(slist.size(), b.size());
        FinalizedPair<String, Double> firstpair = slist.getPair(3);
        FinalizedPair<String, Double> ndpair = slist.getPair(0);
        assertEquals(b.get(3), firstpair.getValue(), 0.);
        assertEquals(b.get(0), firstpair.getValue(), 0.);

    }

    /**
     * Test of getHighestWords method, of class TableBiasingServiceMethods.
     */
    @Test
    public void testGetHighestWords() {
        System.out.println("getHighestWords");

    }

}
