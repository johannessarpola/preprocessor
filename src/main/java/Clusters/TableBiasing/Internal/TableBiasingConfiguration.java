/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TableBiasing.Internal;

import Utilities.Json.JsonReader;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBiasingConfiguration {

    public static List<String> getResourcePaths() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private JsonObject conf;
    boolean isReady;
    
    public TableBiasingConfiguration(String filepath){
        try {
            conf = JsonReader.readJson(filepath);
            // Marshal to POJO
            // Allow access
            isReady = true;
        } catch (IOException ex) {
            //GeneralLogging.logMessage_Error(getClass(), msg_info_noTableAdded);
            isReady = false;
        }
    }
    
}
