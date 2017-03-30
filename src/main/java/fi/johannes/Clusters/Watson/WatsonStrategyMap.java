/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.Watson;

import fi.johannes.Abstractions.Core.ClustersStrategyMap;
import fi.johannes.Clusters.Watson.Internal.WatsonConnector;
import fi.johannes.Clusters.Watson.Strategies.AlchemyWrapper;
import fi.johannes.Global.Options;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WatsonStrategyMap extends ClustersStrategyMap<WatsonConnector> {

    public WatsonStrategyMap(Options.SupportedClusters id) {
        super(id);
    }

    @Override
    public WatsonConnector initializeStrategy(Options.SupportedProcessingStrategy strategy) throws StrategyNotSupportedException {
        switch (strategy) {
            // case ConceptInsights: return new ConceptsInsightsWrapper(); DISCONTINUED
            case Alchemy: return new AlchemyWrapper ();
            default: throw new StrategyNotSupportedException();
        }
    }
}
