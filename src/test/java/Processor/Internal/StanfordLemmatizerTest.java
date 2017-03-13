/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor.Internal;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Johannes töissä
 */
public class StanfordLemmatizerTest {
    
    public StanfordLemmatizerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of lemmatize method, of class Lemmatizer.
     */
    @Test
    public void testLemmatize() {
        System.out.println("lemmatize");
        String documentText = "Community communities funniest am are";
        String documentTest2 = "Indian guar seed futures end up tracking firm spot\",\"    MUMBAI, Jan 1 (Reuters) - Indian guar seed futures ended  higher on Tuesday tracking purchases in the spot market by  stockists, who expect prices to rise in a month.      'Stockists entered the market today to buy cheap...they  expect prices to go up in a months time when exports pick up,'  said a large trader in Bikaner, a major trading centre.       Stockists lifted around 20,000 tonnes of guar seed from  arrivals of 30,000 tonnes.Guar seed is raw material for guar  gum.      India is the biggest exporter of guar gum and ships around 80  percent of its guar gum production.       Spot gaur seed prices in Bikaner were trading nearly 20  rupees higher around 1,600 rupees per 100 kg. Guar seed stocks in  exchange-accredited warehouses stood at 47,646 tonnes as per the  latest data available.      Following were closing prices of guar seed futures <0==================================      Guar seed      Jan           <NGUF8>        1,641          +0.24      Mar           <NGUH8>        1,728+0.29      Guar gum      Jan           <NGGF8>        4,092          +0.05      ((Reporting by Sourav Mishra; Editing by Prem Udayabhanu))    ((sourav.mishra@reuters.com; +91 22 6636 9241; Reuters  Messaging: sourav.mishra.reuters.com@reuters.net))                             Keywords: INDIA GUAR FUTURES/       \"";
        
        Lemmatizer instance = new Lemmatizer();
        List<String> expResult = new ArrayList<>();
        expResult.add("Community");
        expResult.add("funniest");
        expResult.add("be");
        long time = System.currentTimeMillis();
        List<String> result2 = instance.lemmatize(documentTest2);
        System.out.println(result2);
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time);
        List<String> result = instance.lemmatize(documentText);
        Assert.assertEquals(expResult.get(0), result.get(0));
        Assert.assertEquals(expResult.get(0), result.get(0));
        Assert.assertEquals(expResult.get(1), result.get(2));
        Assert.assertEquals(expResult.get(2), result.get(3));
        Assert.assertEquals(expResult.get(2), result.get(4));
    }
    
}
