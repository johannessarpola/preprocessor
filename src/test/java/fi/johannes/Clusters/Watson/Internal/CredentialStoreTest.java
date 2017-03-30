/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.Watson.Internal;

import fi.johannes.Utilities.Json.JsonReader;
import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Johannes töissä
 */
public class CredentialStoreTest {
    private static CredentialsStore scc;

    public CredentialStoreTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Gson gson = new Gson();
        try {
            scc = gson.fromJson(JsonReader.readJson(WatsonOptions.pathToCrendetials), CredentialsStore.class);
        } catch (IOException ex) {
            Logger.getLogger(CredentialStoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of buildCredentialStore method, of class WatsonCredentialsStorage.
     */
    @Test
    public void testBuildCredentialStore() {
        System.out.println("buildCredentialStore");
        WatsonCredentialsStorage result = new WatsonCredentialsStorage();
        Assert.assertNotNull(result);
    }

    /**
     * Test of access method, of class WatsonCredentialsStorage.
     */
    @Test
    public void testAccess() throws Exception {
        System.out.println("access");
        WatsonCredentialsStorage cs = new WatsonCredentialsStorage();
        String field = "credentials.username";
        String expResult = scc.getCredentials().getUsername();
        String result = cs.access(field);
        Assert.assertEquals(expResult, result);
    }
    
}
