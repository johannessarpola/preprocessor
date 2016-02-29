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
import Global.Options.SupportedClusters;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ClustersToStrategies {

    /**
     * Maps the Clusters to Strategies 1:N
     */
    private static final Map<Options.SupportedClusters, Options.SupportedProcessingStrategy[]> CLUSTERSTOSERVICES;

    static {
        Map<Options.SupportedClusters, Options.SupportedProcessingStrategy[]> tMap = new HashMap<>();
        tMap.put(Options.SupportedClusters.TFIDF, new Options.SupportedProcessingStrategy[]{Options.SupportedProcessingStrategy.TFIDF_Keywords, Options.SupportedProcessingStrategy.TFIDF_Combined,
            Options.SupportedProcessingStrategy.TFIDF_WordNgram, Options.SupportedProcessingStrategy.TFIDF_KeywordsFirst});
        tMap.put(Options.SupportedClusters.Watson, new Options.SupportedProcessingStrategy[]{Options.SupportedProcessingStrategy.ConceptInsights, Options.SupportedProcessingStrategy.Alchemy});
        tMap.put(Options.SupportedClusters.TableBiasing, new Options.SupportedProcessingStrategy[]{Options.SupportedProcessingStrategy.TableBiasing});
        CLUSTERSTOSERVICES = Collections.unmodifiableMap(tMap);
    }
    private static final Map<Options.SupportedClusters, GenericCluster> CLUSTERS;

    static {
        Map<Options.SupportedClusters, GenericCluster> t2Map = new HashMap<>();
        t2Map.put(Options.SupportedClusters.TFIDF, new TFIDFCluster());
        t2Map.put(Options.SupportedClusters.Watson, new DeveloperCloudCluster());
        t2Map.put(Options.SupportedClusters.TableBiasing, new TableBiasingCluster());
        CLUSTERS = Collections.unmodifiableMap(t2Map);
    }

    public static Options.SupportedProcessingStrategy[] getStrategies(SupportedClusters c) {
        return CLUSTERSTOSERVICES.get(c);
    }

    public static GenericCluster getCluster(SupportedClusters c) {
        return CLUSTERS.get(c);
    }
}
