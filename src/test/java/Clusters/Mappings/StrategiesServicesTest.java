/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Mappings;

import Global.Options;
import Global.Options.SupportedProcessingStrategy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StrategiesServicesTest {

    /**
     * Test that all ENUM should have corresponding services in Strategies
     */
    @Test
    public void testGetService() throws Exception {
        for(SupportedProcessingStrategy s : Options.SupportedProcessingStrategy.values()){
            assertThat(StrategiesServices.getService(s), is(not(nullValue())));
        }
    }
    
}
