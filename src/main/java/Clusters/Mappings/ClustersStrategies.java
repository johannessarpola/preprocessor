/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Mappings;

import Abstractions.GenericCluster;
import Clusters.TFIDF.TFIDFCluster;
import Clusters.TableBiasing.TableBiasingCluster;
import Clusters.Watson.DeveloperCloudCluster;
import Global.Options;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ClustersStrategies {

    /**
     * Maps the Clusters to Strategies 1:N
     */
    public static final Map<Options.SupportedClusters, Options.SupportedProcessingStrategy[]> CLUSTERSTOSERVICES;
    static {
        Map<Options.SupportedClusters, Options.SupportedProcessingStrategy[]> tMap = new HashMap<>();
        tMap.put(Options.SupportedClusters.TFIDF, new Options.SupportedProcessingStrategy[]{Options.SupportedProcessingStrategy.TFIDF_Keywords, Options.SupportedProcessingStrategy.TFIDF_Combined,
            Options.SupportedProcessingStrategy.TFIDF_WordNgram, Options.SupportedProcessingStrategy.TFIDF_KeywordsFirst});
        tMap.put(Options.SupportedClusters.Watson, new Options.SupportedProcessingStrategy[]{Options.SupportedProcessingStrategy.ConceptInsights, Options.SupportedProcessingStrategy.Alchemy});
        tMap.put(Options.SupportedClusters.TableBiasing, new Options.SupportedProcessingStrategy[]{Options.SupportedProcessingStrategy.TableBiasing});
        CLUSTERSTOSERVICES = Collections.unmodifiableMap(tMap);
    }
    public static final Map<Options.SupportedClusters, GenericCluster> CLUSTERS;
    static {
        Map<Options.SupportedClusters, GenericCluster> tMap = new HashMap<>();
        tMap.put(Options.SupportedClusters.TFIDF, new TFIDFCluster());
        tMap.put(Options.SupportedClusters.Watson, new DeveloperCloudCluster());
        tMap.put(Options.SupportedClusters.Watson, new TableBiasingCluster());
        CLUSTERS = Collections.unmodifiableMap(tMap);
    }
}
