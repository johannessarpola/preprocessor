/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions.Core;

import fi.johannes.Core.Options;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 * Class which each cluster should have
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class ClustersStrategyMap <T> {
    Options.SupportedClusters id;
    protected ClustersStrategyMap(Options.SupportedClusters id){
        this.id = id;
    }
    public abstract T initializeStrategy(Options.SupportedProcessingStrategy strategy) throws StrategyNotSupportedException;
}
