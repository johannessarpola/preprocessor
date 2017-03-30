/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions.Core;

import fi.johannes.Global.Options;
import fi.johannes.Global.Options.SupportedClusters;
import fi.johannes.Global.Options.SupportedProcessingStrategy;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.InvalidStrategyForClusterException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;

import java.util.List;

/**
 * Is the set of methods to use in Cluster
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public interface GenericClusterMethods {

    public void selectStrategy(SupportedProcessingStrategy strategy) throws InvalidStrategyForClusterException;
    public String processLine(String line, Options.SupportedProcessingParadigms method) throws ServiceNotReadyException, ClusterNoteadyException, UnhandledServiceException;
    public SupportedClusters getId();
    public boolean isClusterReady();
    //public void addVocabulary(List<String> documents);
    public void clear();
    public void setBiasingSize(int size);
    // Initializes the cluster and all services
    public void buildCluster(); // List<String> documents
    public void buildStrategy(Options.SupportedProcessingStrategy strategy, List<String> documents);
}