/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Abstractions.GenericCluster;
import Clusters.Mappings.EnumToCluster;
import Global.Options;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ClusterConnection {

    // Hold clusters
    protected GenericCluster gc;
    boolean isConnectionEstablished;
    boolean isClusterReady;

    public ClusterConnection(Options.SupportedClusters c) {
        isConnectionEstablished = false;
        initCluster(c);
    }

    private void initCluster(Options.SupportedClusters c) {
        gc = EnumToCluster.getCluster(c);
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
    public void addDocumentsToStrategy(Options.SupportedProcessingStrategy s, List<String> docs) {
       gc.buildStrategy(s, docs);
    }

}
