/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TFIDF;

import Abstractions.GenericCluster;
import Clusters.Mappings.StrategiesServices;
import Global.Options;
import Global.Options.SupportedClusters;
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
    
    public TFIDFCluster(){
        super(SupportedClusters.TFIDF);
    }
    @Override
    public String processLine(String line, Options.SupportedProcessingParadigms method) throws ServiceNotReadyException {
        if (this.isClusterReady) {
            if (this.selectedStrategy == Options.SupportedProcessingStrategy.ConceptInsights) {
                line = services.get(selectedStrategy).processLine(line, method, this.biasingSize);
                return line;
            } // TODO switch based on selected strategy
            // TODO Process the line 
            // TODO return the processed
            else {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } else {
            throw new ServiceNotReadyException();
        }
    }

    //@Override
    private void addDocumentsToServices(Options.SupportedProcessingStrategy strategy, List<String> documents) {
        services.get(strategy).preloadDocuments(documents);
        
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void selectStrategy(Options.SupportedProcessingStrategy strategy) {
        this.selectedStrategy = strategy;
    }

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
        addDocumentsToServices(strategy, documents);
        this.readyStrategies.add(strategy);
    }

    @Override
    public void clearStrategy(Options.SupportedProcessingStrategy strategy) {
        this.services.get(strategy).clear();
    }
    
    
}
