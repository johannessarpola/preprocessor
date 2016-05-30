/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import Utilities.Logging.CustomExceptions.UnevenSizedListsException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Johannes
 */
public class SortedListPairTest {
    
    public SortedListPairTest() {
    }
    

    @Test
    public void testStructure() throws UnevenSizedListsException {
        List<Double> sorteddbls = Arrays.asList(5., 3., 2., 1.);
        List<String> sortedstrs = Arrays.asList("First", "Second", "Third", "Fourth");
        List<Double> dbls = Arrays.asList(2., 3., 5., 1.);
        List<String> strs = Arrays.asList("Third", "Second", "First", "Fourth");
        SortedListPair<String, Double> sorted = new SortedListPair(strs,dbls);
        Iterator<FinalizedPair<String, Double>> iter = sorted.iterator();
        int i = 0;
        while(iter.hasNext()) {
            FinalizedPair<String, Double> fp = iter.next();
            assertEquals(sorteddbls.get(i), fp.getValue(), 0.);
            assertEquals(sortedstrs.get(i), fp.getItem());
            i++;
        }
    }
    
}
