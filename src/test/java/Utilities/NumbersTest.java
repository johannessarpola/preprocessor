/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Johannes
 */
public class NumbersTest {
    
    public NumbersTest() {
    }

    /**
     * Test of deepCopyNumber method, of class Numbers.
     */
    @Test
    public void testDeepCopyNumber() {
        System.out.println("deepCopyNumber");
        Number nbr = 10.;
        Number expResult = 10.;
        Number result = Numbers.deepCopyNumber(nbr);
        nbr = null;
        assertEquals(expResult, result);        
        assertEquals(expResult.doubleValue(), result.doubleValue(),0);
        assertNotNull(result);
    }
    
}
