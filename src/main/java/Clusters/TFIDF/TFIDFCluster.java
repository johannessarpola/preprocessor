/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TFIDF;

import Abstractions.GenericCluster;
import Abstractions.GenericService;
import Clusters.Mappings.StrategiesServices;
import Global.Options;
import Global.Options.SupportedClusters;
import Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Utilities.Logging.CustomExceptions.StrategyNotSupported;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TFIDFCluster extends GenericCluster {

    public TFIDFCluster() {
        super(SupportedClusters.TFIDF);
    }

    @Override
    public String processLine(String line, Options.SupportedProcessingParadigms method) throws ServiceNotReadyException, ClusterNoteadyException {
        if (this.isClusterReady) {
            GenericService serv = services.get(selectedStrategy);
            if (serv.isServiceReady()) {
                line = serv.processLine(line, method, this.biasingSize);
                return line;
            } else {
                throw new ServiceNotReadyException();
            }
        } else {
            throw new ClusterNoteadyException();
        }
    }

    //private void addDocumentsToServices(Options.SupportedProcessingStrategy strategy, List<String> documents) {
    //    services.get(strategy).build(documents);
    //}
    @Override
    public void buildCluster() {
        try {
            addServices();
        } catch (StrategyNotSupported ex) {
            Logger.getLogger(TFIDFCluster.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void addServices() throws StrategyNotSupported {
        Options.SupportedProcessingStrategy[] strategies = Clusters.Mappings.ClustersStrategies.CLUSTERSTOSERVICES.get(id);
        for (Options.SupportedProcessingStrategy s : strategies) {
            services.put(s, StrategiesServices.getService(s));
        }
    }

    @Override
    public void buildStrategy(Options.SupportedProcessingStrategy strategy, List<String> documents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
