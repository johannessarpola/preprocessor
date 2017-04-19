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
    ClusterConnection unsupervised = new ClusterConnection(ClusterMapping.ClusterEnums.UnsupervisedBiasing);
    ClusterConnection supervised = new ClusterConnection(ClusterMapping.ClusterEnums.SupervisedBiasing);
    ClusterConnection entitydetection = new ClusterConnection(ClusterMapping.ClusterEnums.EntityDetection);
}
