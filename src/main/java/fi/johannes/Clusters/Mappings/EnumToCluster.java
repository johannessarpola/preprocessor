/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.Mappings;

import fi.johannes.Abstractions.Core.GenericCluster;
import fi.johannes.Clusters.SupervisedBiasing.SupervisedBiasingCluster;
import fi.johannes.Clusters.TFIDF.TFIDFCluster;
import fi.johannes.Clusters.Watson.DeveloperCloudCluster;
import fi.johannes.Core.Options;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class EnumToCluster {

    /**
     * Maps Cluster (Enum):Cluster
     */
    private static final Map<Options.SupportedClusters, GenericCluster> CLUSTERS;

    static {
        Map<Options.SupportedClusters, GenericCluster> tMap = new HashMap<>();
        tMap.put(Options.SupportedClusters.TFIDF, new TFIDFCluster());
        tMap.put(Options.SupportedClusters.Watson, new DeveloperCloudCluster());
        tMap.put(Options.SupportedClusters.TableBiasing, new SupervisedBiasingCluster());
        CLUSTERS = Collections.unmodifiableMap(tMap);
    }

    public static GenericCluster getCluster(Options.SupportedClusters c) {
        return CLUSTERS.get(c);
    }
}
