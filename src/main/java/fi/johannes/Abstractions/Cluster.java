/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions;

import fi.johannes.Core.AppConf;
import fi.johannes.Core.ClusterMapping;
import fi.johannes.Core.ClusterMapping.ClusterEnums;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Utilities.Logging.CustomExceptions.InvalidStrategyForClusterException;

import java.util.HashMap;
import java.util.List;

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

    protected List<SupportedProcessingStrategy> strategies;
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
        if (strategies.contains(strategy)) {
            this.selectedStrategy = strategy;
        } else {
            throw new InvalidStrategyForClusterException();
        }
    }


    public void clearStrategy(SupportedProcessingStrategy strategy) {
        services.get(strategy).clear();
    }

    @Override
    public void clear() {
        services.values().forEach(GenericService::clear);
    }

    protected AppConf getConf() {
        return conf;
    }

    /**
     * Sets conf, used in child clusters
     * @param conf
     */
    protected void setConf(AppConf conf) {
        this.conf = conf;
    }
}
