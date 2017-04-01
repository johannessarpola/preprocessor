/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing;

import fi.johannes.Abstractions.Core.ClustersStrategyMap;
import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Clusters.SupervisedBiasing.Strategies.TableBiasingService;
import fi.johannes.Core.Options;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class SupervisedBiasingStrategyMap extends ClustersStrategyMap<GenericService>   {
    
    public SupervisedBiasingStrategyMap(Options.SupportedClusters id){
        super(id);
    }
    @Override
    public GenericService initializeStrategy(Options.SupportedProcessingStrategy strategy) throws StrategyNotSupportedException {
        switch(strategy){
            case SupervisedBiasingWithTable: return new TableBiasingService();
            default: throw new StrategyNotSupportedException();
        }
    }
    

}
