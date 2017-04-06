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
    ClusterConnection watson = new ClusterConnection(ClusterMapping.ClusterEnums.Watson);
    ClusterConnection tfidf = new ClusterConnection(ClusterMapping.ClusterEnums.TFIDF);
    ClusterConnection supervised = new ClusterConnection(ClusterMapping.ClusterEnums.TableBiasing);
}
