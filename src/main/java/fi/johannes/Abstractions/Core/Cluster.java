/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions.Core;

import fi.johannes.Core.AppConf;
import fi.johannes.Core.ClusterMapping;
import fi.johannes.Core.ClusterMapping.ClusterEnums;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Utilities.Logging.CustomExceptions.InvalidStrategyForClusterException;

import java.util.HashMap;

/**
 * Is the main class to implement in preprocessing clusters Just has the general
 * attributes which all fi.johannes.Clusters should have.
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class Cluster implements GenericClusterMethods {

    private AppConf conf;

    protected int biasingSize;
    protected boolean isClusterReady;

    protected ClusterEnums clusterId;
    protected ClustersStrategyMap<? extends GenericService> serviceMap;
    protected SupportedProcessingStrategy selectedStrategy;

    // Enums for each clusters
    protected SupportedProcessingStrategy[] strategies;
    protected HashMap<SupportedProcessingStrategy, GenericService> services;

    public Cluster(ClusterEnums id) {
        services = new HashMap<>();
        strategies = ClusterMapping.getStrategies(id);
        this.clusterId = id;
    }

    @Override
    public ClusterEnums getClusterId() {
        return clusterId;
    }

    @Override
    public boolean isClusterReady() {
        return isClusterReady;
    }

    @Override
    public void setBiasingSize(int biasingSize) {
        this.biasingSize = biasingSize;
    }

    @Override
    public void selectStrategy(SupportedProcessingStrategy strategy) throws InvalidStrategyForClusterException {
        if (checkStrategy(strategy)) {
            this.selectedStrategy = strategy;
        } else {
            throw new InvalidStrategyForClusterException();
        }
    }

    public boolean checkStrategy(SupportedProcessingStrategy strategy) {
        for (int i = 0; i < strategies.length; i++) {
            if (strategies[i] == strategy) {
                return true;
            }
        }
        return false;
    }

    public void clearStrategy(SupportedProcessingStrategy strategy) {
        services.get(strategy).clear();
    }

    @Override
    public void clear() {
        services.values().stream().forEach((s) -> {
            s.clear();
        });
    }

    protected AppConf getConf() {
        return conf;
    }

    protected void setConf(AppConf conf) {
        this.conf = conf;
    }
}
