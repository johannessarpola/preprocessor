/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core;

import fi.johannes.Global.Options;

public class ConnectionMaster {
    ClusterConnection watson = new ClusterConnection(Options.SupportedClusters.Watson);
    ClusterConnection tfidf = new ClusterConnection(Options.SupportedClusters.TFIDF);
    ClusterConnection supervised = new ClusterConnection(Options.SupportedClusters.TableBiasing);
}
