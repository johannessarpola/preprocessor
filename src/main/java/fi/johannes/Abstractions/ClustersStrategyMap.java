/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions;

import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Core.ClusterMapping;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 * Class which each cluster should have
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class ClustersStrategyMap <T> {
    ClusterMapping.ClusterEnums id;
    protected ClustersStrategyMap(ClusterMapping.ClusterEnums id){
        this.id = id;
    }
    public abstract T initializeStrategy(SupportedProcessingStrategy strategy) throws StrategyNotSupportedException;
}
