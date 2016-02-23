/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson.Internal;

import Clusters.Watson.Internal.WatsonOptions;
import Utilities.Json.JsonReader;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johannes töissä
 */
public class WatsonCredentialsStorage {

    private JsonObject store;

    public WatsonCredentialsStorage() {
        try {
            buildJsonStore();
        } catch (Exception ex) {
            Logger.getLogger(WatsonCredentialsStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildJsonStore() throws Exception {
        if (store == null) {
            try {
                this.store = JsonReader.readJson(WatsonOptions.pathToCrendetials);
            } catch (IOException ex) {
                Logger.getLogger(WatsonCredentialsStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Accesses the json objects fields 
     * @param field
     * @return
     */
    public String access(String field) {
        return JsonReader.accessField(store, field);
    }
}
