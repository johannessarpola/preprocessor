/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TableBiasing;

import Abstractions.GenericService;
import Abstractions.StrategyMap;
import Clusters.TableBiasing.Strategies.TableBiasing;
import Global.Options;
import Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBiasingStrategyMap extends StrategyMap<GenericService>   {
    
    public TableBiasingStrategyMap(Options.SupportedClusters id){
        super(id);
    }
    @Override
    public GenericService initializeStrategy(Options.SupportedProcessingStrategy strategy) throws StrategyNotSupportedException {
        switch(strategy){
            case TableBiasing: return new TableBiasing();
            default: throw new StrategyNotSupportedException();
        }
    }
    

}
