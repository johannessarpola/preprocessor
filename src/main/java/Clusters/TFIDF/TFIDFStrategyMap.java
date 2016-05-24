/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TFIDF;

import Abstractions.Core.GenericService;
import Abstractions.Core.ClustersStrategyMap;
import Clusters.TFIDF.Strategies.CombinedExtractor;
import Clusters.TFIDF.Strategies.KeywordExtractor;
import Clusters.TFIDF.Strategies.KeywordsFirstExtractor;
import Clusters.TFIDF.Strategies.WordNgramExtractor;
import Global.Options;
import Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

/**
 * Maps strategy to object (TFIDF)
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TFIDFStrategyMap extends ClustersStrategyMap<GenericService> {
    
    public TFIDFStrategyMap(Options.SupportedClusters id){
        super(id);
    }
    
    @Override
    public GenericService initializeStrategy(Options.SupportedProcessingStrategy strategy) throws StrategyNotSupportedException{
        switch(strategy){
            case TFIDF_Combined: return new CombinedExtractor();
            case TFIDF_Keywords: return new KeywordExtractor();
            case TFIDF_KeywordsFirst: return new KeywordsFirstExtractor();
            case TFIDF_WordNgram: return new WordNgramExtractor();
            default: throw new StrategyNotSupportedException();
        }
    }

}
