/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ConnectionMaster {
    ClusterConnection watson = new ClusterConnection(ClusterMapping.SupportedClusters.Watson);
    ClusterConnection tfidf = new ClusterConnection(ClusterMapping.SupportedClusters.TFIDF);
}
