/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing;

import fi.johannes.Abstractions.Core.GenericCluster;
import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Global.Options;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Logging.GeneralLogging;

import java.util.List;

/**
 * It's the cluster to bias based on data owner tabular datas
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class SupervisedBiasingCluster extends GenericCluster{
    
    public SupervisedBiasingCluster(){
        super(Options.SupportedClusters.TableBiasing);
    }

    @Override
    public String processLine(String line, Options.SupportedProcessingParadigms method) throws ServiceNotReadyException, ClusterNoteadyException, UnhandledServiceException {
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
    public void buildCluster() {
        try {
            map = new SupervisedBiasingStrategyMap(id);
            addServices();
        } catch (StrategyNotSupportedException ex) {
            GeneralLogging.logStackTrace_Error(getClass(), ex);
        }
    }

    @Override
    public void buildStrategy(Options.SupportedProcessingStrategy strategy, List<String> documents) {
        services.get(strategy).build(documents);
    }
    
    private void addServices() throws StrategyNotSupportedException {
        for(Options.SupportedProcessingStrategy strategy : strategies){
            GenericService gs = map.initializeStrategy(strategy);
            services.put(strategy, gs);
        }
        
    }
    
    
}
