/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

import fi.johannes.Abstractions.Core.Cluster;
import fi.johannes.Core.AppConf.*;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
@Component
public class ClusterConnection {

    private Cluster gc;
    private boolean isClusterInitialized;

    private AppConf appConf;

    ClusterConnection(ClusterMapping.ClusterEnums c, AppConf appConf) {
        isClusterInitialized = false;
        initCluster(c);
        this.appConf = appConf;
    }



    private void initCluster(ClusterMapping.ClusterEnums c) {
        gc = ClusterMapping.getCluster(c);
        gc.initClusterWithConf(appConf);
        isClusterInitialized = true;
    }
    /**
     * Adds documents for given strategy. Doesn't really matter if a strategy doesn' need
     * documents as then it'll just lead to nothing.
     * @param s
     * @param docs
     */
    public void buildCluster(SupportedProcessingStrategy s, List<String> docs) {
        gc.buildStrategy(s, docs);
    }

}
