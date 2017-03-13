/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Global.Options;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class Master {
    ClusterConnection watson = new ClusterConnection(Options.SupportedClusters.Watson);
    ClusterConnection tfidf = new ClusterConnection(Options.SupportedClusters.TFIDF);
}
