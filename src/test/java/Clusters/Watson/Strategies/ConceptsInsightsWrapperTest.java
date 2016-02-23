/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson.Strategies;

import Clusters.Watson.DeveloperCloudCluster;
import Global.Options;
import Global.Options.SupportedProcessingParadigms;
import Utilities.Logging.CustomExceptions.InvalidStrategyForClusterException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ConceptsInsightsWrapperTest {
    static DeveloperCloudCluster dc;
    public ConceptsInsightsWrapperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        dc = new DeveloperCloudCluster();
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of processLine method, of class ConceptsInsightsMethods.
     */
    @Test
    public void testProcessLineAppend() throws ServiceNotReadyException, InvalidStrategyForClusterException {
        System.out.println("processLine");
        String line = "Wikipedia";
        dc.selectStrategy(Options.SupportedProcessingStrategy.ConceptInsights);
        String expResult = "Wikipedia Wikipedia";
        String result = dc.processLine(line, SupportedProcessingParadigms.Append);
        Assert.assertEquals(expResult.toLowerCase(), result.toLowerCase());
    }
    @Test
    public void testConcepts() throws ServiceNotReadyException, InvalidStrategyForClusterException{
        dc.selectStrategy(Options.SupportedProcessingStrategy.ConceptInsights);
        dc.setBiasingSize(5);
        String line = "during the American of his|North Carolina the North the North Carolina during the one of the American 1954, the North Carolina of Jethro Sumner had Hogun's career as virtually no|that would his life. surviving correspondence that would appear to have American Civil War, North Carolina legislature 1786, the North Carolina and one ;;";
        System.out.println(dc.processLine(line, SupportedProcessingParadigms.Append));
                
    }
}
