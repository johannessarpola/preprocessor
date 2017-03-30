/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Math;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Johannes
 */
public class ListMathTest {

    public ListMathTest() {
    }

    @Test
    public void testOnDoubles() {
        List<Double> sample1 = Arrays.asList(5., 3., 1.);
        List<Double> sample2 = Arrays.asList(6., 6., 6., 5.);
        List<Double> sample3 = Arrays.asList(5., 3., 5., 2.);
        List<Double> sample4 = Arrays.asList(5., 3., 1., 1.);
        List<Double> sample5 = Arrays.asList(5., 3., 1., 1., 7.);

        assertEquals(9., ListMath.OnDoubles.sum(sample1), 0.);
        assertEquals(3., ListMath.OnDoubles.median(sample1), 0.);
        assertEquals(3., ListMath.OnDoubles.mean(sample1), 0.);
        
        assertEquals(23., ListMath.OnDoubles.sum(sample2), 0.);
        assertEquals(6., ListMath.OnDoubles.median(sample2), 0.1);
        assertEquals(5.75, ListMath.OnDoubles.mean(sample2), 0.);
        assertEquals(6., ListMath.OnDoubles.mode(sample2), 0.);
        
        assertEquals(4., ListMath.OnDoubles.median(sample3), 0.);
        assertEquals(3.75, ListMath.OnDoubles.mean(sample3), 0.);
        
        assertEquals(2., ListMath.OnDoubles.median(sample4), 0.);
        assertEquals(2.5, ListMath.OnDoubles.mean(sample4), 0.);
        assertEquals(1., ListMath.OnDoubles.mode(sample4), 0.);
        
        assertEquals(3., ListMath.OnDoubles.median(sample5), 0.);
        assertEquals(3.4, ListMath.OnDoubles.mean(sample5), 0.);
        assertEquals(1., ListMath.OnDoubles.mode(sample5), 0.);
        
    }

}
