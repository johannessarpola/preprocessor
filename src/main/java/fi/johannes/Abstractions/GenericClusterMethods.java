/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions;

import fi.johannes.Core.App;
import fi.johannes.Core.AppConf;
import fi.johannes.Core.AppConf.*;
import fi.johannes.Core.ClusterMapping.ClusterEnums;
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
    public String processLine(String line, SupportedProcessingMethods method) throws ServiceNotReadyException, ClusterNoteadyException, UnhandledServiceException;
    public ClusterEnums getClusterId();
    public boolean isClusterReady();
    public void clear();
    public void setBiasingSize(int size);
    public void initClusterWithConf(AppConf conf); // List<String> documents
    public void buildStrategy(SupportedProcessingStrategy strategy, List<String> documents);
}
