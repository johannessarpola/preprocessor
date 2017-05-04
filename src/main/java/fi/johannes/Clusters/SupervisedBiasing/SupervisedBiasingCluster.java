/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing;

import fi.johannes.Abstractions.Cluster;
import fi.johannes.Abstractions.GenericService;
import fi.johannes.Core.AppConf;
import fi.johannes.Core.AppConf.SupportedProcessingMethods;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Core.ClusterMapping;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Logging.Logging;

import java.util.List;

/**
 * It's the cluster to bias based on data owner tabular datas
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class SupervisedBiasingCluster extends Cluster {
    
    public SupervisedBiasingCluster(){
        super(ClusterMapping.ClusterEnums.SupervisedBiasing);
    }

    @Override
    public String processLine(String line, SupportedProcessingMethods method) throws ServiceNotReadyException, ClusterNoteadyException, UnhandledServiceException {
        if(this.isClusterReady){
            GenericService serv = services.get(selectedStrategy);
            if(serv.isServiceReady()){
               line = serv.processLine(line, method, biasingSize);
            }
            else{
                throw new ServiceNotReadyException();
            }
        }
        else {
            throw new ClusterNoteadyException();
        }
        return line;
        
    }

    @Override
    public void initClusterWithConf(AppConf conf) {
        try {
            super.setConf(conf);
            serviceMap = new SupervisedBiasingStrategyMap(clusterId);
            addServices();
            this.isClusterReady = true;
        } catch (StrategyNotSupportedException ex) {
            Logging.logStackTrace_Error(getClass(), ex);
        }
    }

    @Override
    public void buildStrategy(SupportedProcessingStrategy strategy, List<String> documents) {
        services.get(strategy).build(documents);
    }
    
    private void addServices() throws StrategyNotSupportedException {
        for(SupportedProcessingStrategy strategy : strategies){
            GenericService gs = serviceMap.initializeStrategy(strategy);
            services.put(strategy, gs);
        }
        
    }
    
    
}
