/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.Watson.Internal;

import fi.johannes.Utilities.Json.JsonReader;
import fi.johannes.Utilities.Logging.GenLogging;
import com.google.gson.JsonObject;

import java.io.IOException;

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
             GenLogging.logStackTrace_Error(getClass(), ex);
        }
    }

    private void buildJsonStore() throws Exception {
        if (store == null) {
            try {
                this.store = JsonReader.readJson(WatsonOptions.pathToCrendetials);
            } catch (IOException ex) {
                  GenLogging.logStackTrace_Error(getClass(), ex);
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
