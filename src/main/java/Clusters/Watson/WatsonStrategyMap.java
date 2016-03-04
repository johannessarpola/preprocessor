/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson;

import Abstractions.StrategyMap;
import Clusters.Watson.Internal.WatsonConnector;
import Clusters.Watson.Strategies.AlchemyWrapper;
import Clusters.Watson.Strategies.ConceptsInsightsWrapper;
import Global.Options;
import Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WatsonStrategyMap extends StrategyMap<WatsonConnector> {

    public WatsonStrategyMap(Options.SupportedClusters id) {
        super(id);
    }

    @Override
    public WatsonConnector initializeStrategy(Options.SupportedProcessingStrategy strategy) throws StrategyNotSupportedException {
        switch (strategy) {
            case ConceptInsights: return new ConceptsInsightsWrapper();
            case Alchemy: return new AlchemyWrapper ();
            default: throw new StrategyNotSupportedException();
        }
    }
}
