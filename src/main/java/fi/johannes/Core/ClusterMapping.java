/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

import com.google.common.collect.ImmutableMap;
import fi.johannes.Abstractions.Core.Cluster;
import fi.johannes.Clusters.EntityDetection.EntityDetectionCluster;
import fi.johannes.Clusters.SupervisedBiasing.SupervisedBiasingCluster;
import fi.johannes.Clusters.UnsupervisedBiasing.UnsupervisedBiasingCluster;
import org.apache.commons.lang3.ClassUtils;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fi.johannes.Core.AppConf.*;
import static fi.johannes.Core.AppConf.SupportedProcessingStrategy.*;
import static fi.johannes.Core.ClusterMapping.ClusterEnums.*;

/**
 * Static mapping of fi.johannes.Clusters -> Strategies
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ClusterMapping {

    public enum ClusterEnums {
        UnsupervisedBiasing, SupervisedBiasing, EntityDetection
    }

    /**
     * Maps Cluster (Enum):Cluster
     */
    private static Map<ClusterEnums, Cluster> CLUSTERS;

    /**
     * Maps the fi.johannes.Clusters to Strategies 1:N
     */
    private static Map<ClusterEnums, SupportedProcessingStrategy[]> CLUSTERS_TO_SERVICES;

    private static void buildClustersToServicesMapping() {
        CLUSTERS_TO_SERVICES = Collections.unmodifiableMap(Stream.of(
                new SimpleEntry<>(UnsupervisedBiasing, new SupportedProcessingStrategy[]{TFIDF_Keywords, TFIDF_Combined, TFIDF_WordNgram, TFIDF_KeywordsFirst}),
                new SimpleEntry<>(SupervisedBiasing, new SupportedProcessingStrategy[]{SupervisedBiasingWithTable}),
                new SimpleEntry<>(EntityDetection, new SupportedProcessingStrategy[]{WikipediaTitles}))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }

    private static void buildClustersMapping() {
        CLUSTERS = Collections.unmodifiableMap(Stream.of(
                new SimpleEntry<>(UnsupervisedBiasing, new UnsupervisedBiasingCluster()),
                new SimpleEntry<>(SupervisedBiasing, new SupervisedBiasingCluster()),
                new SimpleEntry<>(EntityDetection, new EntityDetectionCluster()))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }

    public static Map<ClusterEnums, Cluster> getClusters() {
        if (CLUSTERS == null) {
            buildClustersMapping();
        }
        return CLUSTERS;
    }

    public static Map<ClusterEnums, SupportedProcessingStrategy[]> getClustersToServices() {
        if (CLUSTERS_TO_SERVICES == null) {
            buildClustersToServicesMapping();
        }
        return CLUSTERS_TO_SERVICES;
    }


    public static Cluster getCluster(ClusterEnums c) {
        Map<ClusterEnums, Cluster> clusters = getClusters();
        return clusters.get(c);
    }

    public static ClusterEnums whichCluster(SupportedProcessingStrategy strategy) {
        Optional<ClusterEnums> found = getClustersToServices()
                .entrySet()
                .stream()
                .filter(entry -> Arrays.stream(entry.getValue()).anyMatch(s -> s == strategy))
                .map(Map.Entry::getKey)
                .findFirst();
        assert found.isPresent(); // there's some forgotten mapping if this is not present
        return found.get();
    }


    /**
     * Gets the correct strategy for a Cluster clusterId
     *
     * @param id Cluster clusterId
     * @return
     */
    public static List<SupportedProcessingStrategy> getStrategies(ClusterEnums id) {
        Map<ClusterEnums, SupportedProcessingStrategy[]> clustersToServices = getClustersToServices();
        return Arrays.asList(clustersToServices.get(id));
    }

}
