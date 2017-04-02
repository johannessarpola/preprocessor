/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

import fi.johannes.Abstractions.Core.GenericCluster;
import fi.johannes.Clusters.SupervisedBiasing.SupervisedBiasingCluster;
import fi.johannes.Clusters.TFIDF.TFIDFCluster;
import fi.johannes.Clusters.Watson.DeveloperCloudCluster;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Static mapping of fi.johannes.Clusters -> Strategies
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ClusterMapping {


    /**
     * Maps Cluster (Enum):Cluster
     */
    private static final Map<ClusterMapping.SupportedClusters, GenericCluster> CLUSTERS;

    static {
        Map<ClusterMapping.SupportedClusters, GenericCluster> tMap = new HashMap<>();
        tMap.put(ClusterMapping.SupportedClusters.TFIDF, new TFIDFCluster());
        tMap.put(ClusterMapping.SupportedClusters.Watson, new DeveloperCloudCluster());
        tMap.put(ClusterMapping.SupportedClusters.TableBiasing, new SupervisedBiasingCluster());
        CLUSTERS = Collections.unmodifiableMap(tMap);
    }

    /**
     * Maps the fi.johannes.Clusters to Strategies 1:N
     */
    private static final Map<SupportedClusters, App.SupportedProcessingStrategy[]> CLUSTERSTOSERVICES;
    static {
        Map<SupportedClusters, App.SupportedProcessingStrategy[]> tMap = new HashMap<>();
        tMap.put(SupportedClusters.TFIDF, new App.SupportedProcessingStrategy[]{App.SupportedProcessingStrategy.TFIDF_Keywords, App.SupportedProcessingStrategy.TFIDF_Combined,
            App.SupportedProcessingStrategy.TFIDF_WordNgram, App.SupportedProcessingStrategy.TFIDF_KeywordsFirst});
        tMap.put(SupportedClusters.Watson, new App.SupportedProcessingStrategy[]{App.SupportedProcessingStrategy.ConceptInsights, App.SupportedProcessingStrategy.Alchemy});
        tMap.put(SupportedClusters.TableBiasing, new App.SupportedProcessingStrategy[]{App.SupportedProcessingStrategy.SupervisedBiasingWithTable});
        CLUSTERSTOSERVICES = Collections.unmodifiableMap(tMap);
    }

    public static GenericCluster getCluster(ClusterMapping.SupportedClusters c) {
        return CLUSTERS.get(c);
    }
    /**
     * Gets the correct strategy for a Cluster id
     * @param id Cluster id 
     * @return 
     */
    public static App.SupportedProcessingStrategy[] getStrategies(SupportedClusters id) {
        return CLUSTERSTOSERVICES.get(id);
    }


    public enum SupportedClusters {
        Watson, TFIDF, TableBiasing
    }
}
