/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing.Internal;

import fi.johannes.Utilities.Json.JsonReader;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBiasingConfiguration {
    List<Path> paths;
    
    public static List<String> getResourcePaths() {
        // TODO Get table paths
        return Collections.emptyList();
    }
    private JsonObject conf;
    boolean isReady;
    // TODO This really doesn't need a json, just a path to folder with tables which can be then read if the strategy (e.g. xlsx) is supported by checking 
    // file extension
    public TableBiasingConfiguration(String filepath){
        try {
            conf = JsonReader.readJson(filepath);
            // Marshal to POJO
            // Allow access
            isReady = true;
        } catch (IOException ex) {
            //GenLogging.logMessage_Error(getClass(), msg_info_noTableAdded);
            isReady = false;
        }
    }
    public TableBiasingConfiguration(List<String> path){
        
        
    }
}
