/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing;

import Abstractions.GenericService;
import Abstractions.StrategyMap;
import Clusters.SupervisedBiasing.Strategies.TableBiasingService;
import Global.Options;
import Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class SupervisedBiasingStrategyMap extends StrategyMap<GenericService>   {
    
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
