/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Mappings;

import Abstractions.GenericService;
import Clusters.TFIDF.Strategies.CombinedExtractor;
import Clusters.TFIDF.Strategies.KeywordExtractor;
import Clusters.TFIDF.Strategies.KeywordsFirstExtractor;
import Clusters.TFIDF.Strategies.WordNgramExtractor;
import Clusters.Watson.Strategies.AlchemyWrapper;
import Clusters.Watson.Strategies.ConceptsInsightsWrapper;
import Global.Options;
import Global.Options.SupportedProcessingStrategy;
import Utilities.Logging.CustomExceptions.StrategyNotSupported;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StrategiesServices {
    /**
     * Cluster scoped
     * 1:1 Enum(SupportedProcessingStrategy) to GenericService
     */
        protected static final Map<Options.SupportedProcessingStrategy, GenericService> SERVICES;
        static {
            Map<Options.SupportedProcessingStrategy, GenericService> tMap = new HashMap<>();
            tMap.put(SupportedProcessingStrategy.TFIDF_Keywords, new KeywordExtractor());
            tMap.put(SupportedProcessingStrategy.TFIDF_WordNgram, new WordNgramExtractor());
            tMap.put(SupportedProcessingStrategy.TFIDF_Combined, new CombinedExtractor());
            tMap.put(SupportedProcessingStrategy.TFIDF_KeywordsFirst, new KeywordsFirstExtractor());
            tMap.put(SupportedProcessingStrategy.Alchemy, new AlchemyWrapper());
            tMap.put(SupportedProcessingStrategy.ConceptInsights, new ConceptsInsightsWrapper());
            SERVICES = Collections.unmodifiableMap(tMap);
        }
        
        
        
    public static GenericService getService(SupportedProcessingStrategy s) throws StrategyNotSupported {
        if(SERVICES.containsKey(s)) {
            return SERVICES.get(s);
        }
        else {
            throw new StrategyNotSupported();
        }
    }
}
