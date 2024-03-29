package fi.johannes.Clusters.SupervisedBiasing.Internal;

import fi.johannes.Abstractions.WeighingLogic;
import fi.johannes.Abstractions.WeighingPair;
import fi.johannes.Utilities.Math.ListMath;

import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class DoubleBasedWeighingLogic implements WeighingLogic {

    private DoubleBasedWeighingLogic() {
    }

    /**
     * Manages the weighting logic for tablebiasing
     *
     * @param pair depth and depthFactor pair
     * @return
     */
    @Override
    public Double calculateDepth(WeighingPair pair) {
        Double block = 1 / pair.getTotalDepth().doubleValue();
        Double blocksToCover = pair.getDepth().doubleValue() - (pair.getTotalDepth().doubleValue() + 1);
        return Math.abs(block * blocksToCover);
    }

    @Override
    public Double combineWeights(CombineStrategies strategy, List<Double> weights) {
        if (strategy == CombineStrategies.mean) {
            return ListMath.OnDoubles.mean(weights);
        }
        if (strategy == CombineStrategies.median) {
            return ListMath.OnDoubles.median(weights);
        }
        if (strategy == CombineStrategies.mode) {
            return ListMath.OnDoubles.mode(weights);
        }
        if (strategy == CombineStrategies.sum) {
            return ListMath.OnDoubles.sum(weights);
        } else {
            throw new NoSuchMethodError();
        }
    }

    public static DoubleBasedWeighingLogic build() {
            return new DoubleBasedWeighingLogic();
        }

}
