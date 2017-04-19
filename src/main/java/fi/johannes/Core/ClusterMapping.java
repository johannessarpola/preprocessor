/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

import fi.johannes.Abstractions.Core.Cluster;
import fi.johannes.Clusters.SupervisedBiasing.SupervisedBiasingCluster;
import fi.johannes.Clusters.UnsupervisedBiasing.UnsupervisedBiasingCluster;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Static mapping of fi.johannes.Clusters -> Strategies
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ClusterMapping {

    public static Map<ClusterEnums, Cluster> getClusters() {
        return CLUSTERS;
    }

    public static Map<ClusterEnums, App.SupportedProcessingStrategy[]> getClustersToServices() {
        return CLUSTERS_TO_SERVICES;
    }

    /**
     * Maps Cluster (Enum):Cluster
     */
    private static final Map<ClusterEnums, Cluster> CLUSTERS;

    static {
        Map<ClusterEnums, Cluster> tMap = new HashMap<>();
        tMap.put(ClusterEnums.UnsupervisedBiasing, new UnsupervisedBiasingCluster());
        tMap.put(ClusterEnums.SupervisedBiasing, new SupervisedBiasingCluster());
        CLUSTERS = Collections.unmodifiableMap(tMap);
    }

    /**
     * Maps the fi.johannes.Clusters to Strategies 1:N
     */
    private static final Map<ClusterEnums, App.SupportedProcessingStrategy[]> CLUSTERS_TO_SERVICES;
    static {
        Map<ClusterEnums, App.SupportedProcessingStrategy[]> temp = new HashMap<>();
        temp.put(ClusterEnums.UnsupervisedBiasing, new App.SupportedProcessingStrategy[]{App.SupportedProcessingStrategy.TFIDF_Keywords, App.SupportedProcessingStrategy.TFIDF_Combined,
            App.SupportedProcessingStrategy.TFIDF_WordNgram, App.SupportedProcessingStrategy.TFIDF_KeywordsFirst});
        temp.put(ClusterEnums.SupervisedBiasing, new App.SupportedProcessingStrategy[]{App.SupportedProcessingStrategy.SupervisedBiasingWithTable});
        temp.put(ClusterEnums.EntityDetection, new App.SupportedProcessingStrategy[]{App.SupportedProcessingStrategy.WikipediaTitles});
        CLUSTERS_TO_SERVICES = Collections.unmodifiableMap(temp);
    }

    public static Cluster getCluster(ClusterEnums c) {
        return CLUSTERS.get(c);
    }
    /**
     * Gets the correct strategy for a Cluster id
     * @param id Cluster id 
     * @return 
     */
    public static App.SupportedProcessingStrategy[] getStrategies(ClusterEnums id) {
        return CLUSTERS_TO_SERVICES.get(id);
    }


    public enum ClusterEnums {
        UnsupervisedBiasing, SupervisedBiasing, EntityDetection
    }
}
