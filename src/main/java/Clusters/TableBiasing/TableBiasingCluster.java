/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TableBiasing;

import Abstractions.GenericCluster;
import Abstractions.GenericService;
import Global.Options;
import Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import java.util.List;

/**
 * It's the cluster to bias based on data owner tabular datas
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBiasingCluster extends GenericCluster{
    
    public TableBiasingCluster(){
        super(Options.SupportedClusters.TableBiasing);
    }

    @Override
    public String processLine(String line, Options.SupportedProcessingParadigms method) throws ServiceNotReadyException, ClusterNoteadyException {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildStrategy(Options.SupportedProcessingStrategy strategy, List<String> documents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
