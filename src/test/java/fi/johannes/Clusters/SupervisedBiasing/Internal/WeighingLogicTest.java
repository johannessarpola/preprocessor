/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing.Internal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Johannes
 */
public class WeighingLogicTest {
    
    public WeighingLogicTest() {
    }

    /**
     * Test of calculateDepth method, of class DoubleBasedWeighingLogic.
     */
    @Test
    public void testCalculateWeight() {
        
        System.out.println("calculateDepth");
        
        TableBasedWeighingPair tbwp = new TableBasedWeighingPair(1, 4);
        TableBasedWeighingPair tbwp2 = new TableBasedWeighingPair(2, 4);
        TableBasedWeighingPair tbwp3 = new TableBasedWeighingPair(3, 4);
        TableBasedWeighingPair tbwp4 = new TableBasedWeighingPair(4, 4);
        DoubleBasedWeighingLogic instance = DoubleBasedWeighingLogic.build();
        
        Double expResult = 1.;
        Double expResult2 = 0.75;
        Double expResult3 = 0.5;
        Double expResult4 = 0.25;
        
        Double result = instance.calculateDepth(tbwp);
        Double result2 = instance.calculateDepth(tbwp2);
        Double result3 = instance.calculateDepth(tbwp3);
        Double result4 = instance.calculateDepth(tbwp4);
        
        
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
    }
    
}
