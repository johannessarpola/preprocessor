/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

import fi.johannes.Abstractions.Cluster;
import fi.johannes.Core.AppConf.*;


import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ClusterConnection {

    private final ClusterMapping.ClusterEnums clusterId;
    private final List<SupportedProcessingStrategy> strategies;
    private final Cluster gc;
    private final AppConf appConf;

    ClusterConnection(ClusterMapping.ClusterEnums c, AppConf appConf) {
        this.clusterId = c;
        this.strategies = ClusterMapping.getStrategies(c);
        this.appConf = appConf;
        this.gc = ClusterMapping.getCluster(c);
        gc.initClusterWithConf(appConf);
    }

    public Cluster getCluster() {
        return gc;
    }

    public ClusterMapping.ClusterEnums getClusterId() {
        return clusterId;
    }

    public List<SupportedProcessingStrategy> getStrategies() {
        return strategies;
    }

    public AppConf getAppConf() {
        return appConf;
    }
}
