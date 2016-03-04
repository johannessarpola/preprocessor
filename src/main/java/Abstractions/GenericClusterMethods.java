/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abstractions;

import Global.Options;
import Global.Options.SupportedClusters;
import Global.Options.SupportedProcessingStrategy;
import Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import Utilities.Logging.CustomExceptions.InvalidStrategyForClusterException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import java.util.List;

/**
 * Is the set of methods to use in Cluster
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public interface GenericClusterMethods {

    public void selectStrategy(SupportedProcessingStrategy strategy) throws InvalidStrategyForClusterException;
    public String processLine(String line, Options.SupportedProcessingParadigms method) throws ServiceNotReadyException, ClusterNoteadyException;
    public SupportedClusters getId();
    public boolean isClusterReady();
    //public void addVocabulary(List<String> documents);
    public void clear();
    public void setBiasingSize(int size);
    // Initializes the cluster and all services
    public void buildCluster(); // List<String> documents
    public void buildStrategy(Options.SupportedProcessingStrategy strategy, List<String> documents);
}
