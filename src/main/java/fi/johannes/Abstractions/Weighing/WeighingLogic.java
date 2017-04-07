/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions.Weighing;

import java.util.List;

/**
 *
 * @author Johannes
 */
public interface WeighingLogic {
    
     public Double calculateDepth(WeighingPair pair);
     /**
      * Combines multiple weights for a single element
      * @param strategy how weights are combined
      * @param weights
      * @return 
      */
     public Double combineWeights(CombineStrategies strategy, List<Double> weights);
     /**
      * Just defined the possibilites of combining weights as a element might be
      * present in multiple places and have multiple weights
      */
     public static enum CombineStrategies{
        sum,
        median,
        mean,
        mode
    }
}
