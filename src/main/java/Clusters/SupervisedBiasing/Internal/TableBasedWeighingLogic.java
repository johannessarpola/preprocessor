
package Clusters.SupervisedBiasing.Internal;

import Abstractions.Weighing.WeighingLogic;
import Abstractions.Weighing.WeighingPair;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBasedWeighingLogic implements WeighingLogic{
    
    private TableBasedWeighingLogic(){}
    /**
     * Manages the weighting logic for tablebiasing
     * @param pair depth and depthFactor pair
     * @return 
     */
    @Override
    public Double calculateWeight(WeighingPair pair){
        Double d =pair.getPosition().doubleValue()/pair.getDepth().doubleValue();
        return d;
    }
    
    public static class Builder{
        public static TableBasedWeighingLogic build(){
            return new TableBasedWeighingLogic();
        }
    }
}
