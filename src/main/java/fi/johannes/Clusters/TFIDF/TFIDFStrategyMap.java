/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.TFIDF;

import fi.johannes.Abstractions.Core.ClustersStrategyMap;
import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Clusters.TFIDF.Strategies.CombinedExtractor;
import fi.johannes.Clusters.TFIDF.Strategies.KeywordExtractor;
import fi.johannes.Clusters.TFIDF.Strategies.KeywordsFirstExtractor;
import fi.johannes.Clusters.TFIDF.Strategies.WordNgramExtractor;
import fi.johannes.Core.Options;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;

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
