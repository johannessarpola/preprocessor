/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.Watson;

import fi.johannes.Abstractions.Core.GenericCluster;
import fi.johannes.Clusters.Watson.Internal.WatsonConnector;
import fi.johannes.Clusters.Watson.Internal.WatsonCredentialsStorage;
import fi.johannes.Clusters.Watson.Strategies.AlchemyWrapper;
import fi.johannes.Core.Options;
import fi.johannes.Core.Options.SupportedProcessingParadigms;
import fi.johannes.Core.Options.SupportedProcessingStrategy;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Logging.GeneralLogging;
import com.ibm.watson.developer_cloud.service.WatsonService;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johannes töissä
 */
public class DeveloperCloudCluster extends GenericCluster {

    private static HashMap<SupportedProcessingStrategy, WatsonConnector> connectors;
    //private static HashMap<SupportedProcessingStrategy, GenericService> services;
    private static WatsonCredentialsStorage credentials;

//    private SupportedProcessingStrategy selectedStrategy;
//    boolean readytoUse = false;
    public DeveloperCloudCluster() {
        super(Options.SupportedClusters.Watson);
        credentials = new WatsonCredentialsStorage();
        connectors = new HashMap();
    }

    private void init() {
        setupServiceWrappers();
    }

    /**
     * Sets the access rules for different services
     */
    private void setupServiceWrappers() {
        setupAlchemyWrapper(SupportedProcessingStrategy.Alchemy);
        this.isClusterReady = true;
    }

    private void setupAlchemyWrapper(SupportedProcessingStrategy k) {
        //cloud.get(k).setApiKey(credentials.access("Keys.Alchemy Language"));
        AlchemyWrapper alc = new AlchemyWrapper();
        alc.connectWith(credentials);
        services.put(k, alc);
        connectors.put(k, alc);  // copy ref to here also
    }

    /**
     * Gets the service called something
     *
     * @param name
     * @return
     */
    protected WatsonService getWatsonService(SupportedProcessingStrategy name) {
        return connectors.get(name).getServiceDirectly();
    }

    @Override
    public String processLine(String line, SupportedProcessingParadigms method) throws ServiceNotReadyException, UnhandledServiceException, ClusterNoteadyException {
        if (this.isClusterReady) {
            if(!services.get(selectedStrategy).isServiceReady()) throw new ServiceNotReadyException();
            else line = services.get(selectedStrategy).processLine(line, method, biasingSize);
            return line;
        } else {
            throw new ClusterNoteadyException();
        }
    }

    @Override
    public void buildCluster() {
        
        if (!isClusterReady) {
            WatsonStrategyMap mapLocal = new WatsonStrategyMap(id);
            //init();
            for (SupportedProcessingStrategy strategy : strategies) {
                try {
                    WatsonConnector strategyService = mapLocal.initializeStrategy(strategy);
                    strategyService.connectWith(credentials); // This is done only once
                    services.put(strategy, strategyService);
                    connectors.put(strategy, strategyService); // TODO See if it's needed. Not sure.
                } catch (StrategyNotSupportedException ex) {
                    Logger.getLogger(DeveloperCloudCluster.class.getName()).log(Level.SEVERE, null, ex);
                    GeneralLogging.logStackTrace_Error(getClass(), ex);

                }
            }
            this.map = mapLocal;

        }
    }

    @Override
    public void buildStrategy(SupportedProcessingStrategy strategy, List<String> documents) {
        services.get(strategy).build(documents); // This is really confusing as some require and some not
        connectors.get(strategy).connectWith(credentials);
    }

}
