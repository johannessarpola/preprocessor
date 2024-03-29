/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class FinalizedPairTest {
    
    public FinalizedPairTest() {
    }

    /**
     * Test of getItem method, of class FinalizedPair.
     */
    @Test
    public void testGetDatapoint() {
        String item = "item";
        FinalizedPair<String, Integer> fp = new FinalizedPair(item, 1);
        FinalizedPair<String, Integer> fp2 = new FinalizedPair(item, 2);
        assertThat(fp, is(not(nullValue())));
        assertThat(fp2, is(not(nullValue())));
        assertThat(fp.getItem(), is(item));
    }

    /**
     * Test of getValue method, of class FinalizedPair.
     */
    @Test
    public void testGetFrequency() {
        String item = "item";
        FinalizedPair<String, Integer> fp = new FinalizedPair(item, 1);
        FinalizedPair<String, Integer> fp2 = new FinalizedPair(item, 2);
        assertThat(fp.getValue(), is(1));
        assertThat(fp2.getValue(), is(2));
        
        assertThat(fp2, is(not(fp)));
    }
    
}
