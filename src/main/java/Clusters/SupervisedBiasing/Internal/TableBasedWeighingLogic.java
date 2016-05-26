
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
        // 1 / 4 = 0.25 --> pos 4 should be 0.25 and pos 1 should be 1
        Double block = 1/pair.getTotalDepth().doubleValue();
        Double blocksToCover = pair.getDepth().doubleValue()-(pair.getTotalDepth().doubleValue()+1);
        return Math.abs(block*blocksToCover);
    }
    
    public static class Builder{
        public static TableBasedWeighingLogic build(){
            return new TableBasedWeighingLogic();
        }
    }
}
