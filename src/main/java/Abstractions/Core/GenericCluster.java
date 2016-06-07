/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abstractions.Core;

import Global.Options;
import Global.Options.SupportedClusters;
import Global.Options.SupportedProcessingStrategy;
import Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import Utilities.Logging.CustomExceptions.InvalidStrategyForClusterException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Utilities.Logging.CustomExceptions.UnhandledServiceException;
import java.util.HashMap;

/**
 * Is the main class to implement in preprocessing clusters Just has the general
 * attributes which all Clusters should have.
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class GenericCluster implements GenericClusterMethods {
    
    protected ClustersStrategyMap<? extends GenericService> map;
    protected Options.SupportedClusters id;
    protected int biasingSize;
    protected boolean isClusterReady;
    protected SupportedProcessingStrategy selectedStrategy;
    protected HashMap<Options.SupportedProcessingStrategy, GenericService> services;
    // Enums for each clusters
    protected SupportedProcessingStrategy[] strategies;

    public GenericCluster(Options.SupportedClusters id) {
        services = new HashMap();
        strategies = Clusters.Mappings.ClustersToStrategies.getStrategies(id);
        this.id = id;
    }

    @Override
    public SupportedClusters getId() {
        return id;
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
        //SupportedProcessingStrategy[] strategies = ClustersToStrategies.getStrategies(id);
        for (int i = 0; i < strategies.length; i++) {
            if (strategies[i] == strategy) {
                return true;
            }
        }
        return false;
    }

    public void clearStrategy(Options.SupportedProcessingStrategy strategy) {
        services.get(strategy).clear();
    }

    @Override
    public void clear() {
        services.values().stream().forEach((s) -> {
            s.clear();
        });
    }
}
