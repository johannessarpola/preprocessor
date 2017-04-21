/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.UnsupervisedBiasing;

import fi.johannes.Abstractions.Core.ClustersStrategyMap;
import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Clusters.UnsupervisedBiasing.Strategies.CombinedExtractor;
import fi.johannes.Clusters.UnsupervisedBiasing.Strategies.KeywordExtractor;
import fi.johannes.Clusters.UnsupervisedBiasing.Strategies.KeywordsFirstExtractor;
import fi.johannes.Clusters.UnsupervisedBiasing.Strategies.WordNgramExtractor;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Core.ClusterMapping;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 * Maps strategy to object (UnsupervisedBiasing)
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class UnsupervisedStrategyMap extends ClustersStrategyMap<GenericService> {
    
    public UnsupervisedStrategyMap(ClusterMapping.ClusterEnums id){
        super(id);
    }
    
    @Override
    public GenericService initializeStrategy(SupportedProcessingStrategy strategy) throws StrategyNotSupportedException{
        switch(strategy){
            case TFIDF_Combined: return new CombinedExtractor();
            case TFIDF_Keywords: return new KeywordExtractor();
            case TFIDF_KeywordsFirst: return new KeywordsFirstExtractor();
            case TFIDF_WordNgram: return new WordNgramExtractor();
            default: throw new StrategyNotSupportedException();
        }
    }

}
