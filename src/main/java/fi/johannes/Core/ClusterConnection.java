/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

import fi.johannes.Abstractions.Core.Cluster;

import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ClusterConnection {

    // Hold clusters
    private Cluster gc;
    private boolean isConnectionEstablished;
    private boolean isClusterReady;

    ClusterConnection(ClusterMapping.ClusterEnums c) {
        isConnectionEstablished = false;
        initCluster(c);
    }

    private void initCluster(ClusterMapping.ClusterEnums c) {
        gc = ClusterMapping.getCluster(c);
        gc.buildCluster();
        isClusterReady = gc.isClusterReady();
        isConnectionEstablished = true;
    }
    /**
     * Adds documents for given strategy. Doesn't really matter if a strategy doesn' need
     * documents as then it'll just lead to nothing. 
     * @param s
     * @param docs 
     */
    public void addDocumentsToStrategy(App.SupportedProcessingStrategy s, List<String> docs) {
       gc.buildStrategy(s, docs);
    }

}
