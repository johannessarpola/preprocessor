/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson;

import Abstractions.GenericCluster;
import Clusters.Watson.Internal.WatsonConnector;
import Clusters.Watson.Internal.WatsonCredentialsStorage;
import Clusters.Watson.Strategies.AlchemyWrapper;
import Clusters.Watson.Strategies.ConceptsInsightsWrapper;
import Global.Options;
import Global.Options.SupportedProcessingParadigms;
import Global.Options.SupportedProcessingStrategy;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import com.ibm.watson.developer_cloud.service.WatsonService;
import java.util.HashMap;
import java.util.List;

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
    }

    private void init() {
        setupServiceWrappers();
    }

    /**
     * Sets the access rules for different services
     */
    private void setupServiceWrappers() {
        setupConceptInsightsWrapper(SupportedProcessingStrategy.ConceptInsights);
        setupAlchemyWrapper(SupportedProcessingStrategy.Alchemy);
        this.isClusterReady = true;
    }

    private void setupConceptInsightsWrapper(SupportedProcessingStrategy k) {
        ConceptsInsightsWrapper ciw = new ConceptsInsightsWrapper();
        ciw.connect(credentials);
        services.put(k, ciw);
        connectors.put(k, ciw); // copy ref to here also
    }

    private void setupAlchemyWrapper(SupportedProcessingStrategy k) {
        //cloud.get(k).setApiKey(credentials.access("Keys.Alchemy Language"));
        AlchemyWrapper alc = new AlchemyWrapper();
        alc.connect(credentials);
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
    public String processLine(String line, SupportedProcessingParadigms method) throws ServiceNotReadyException {
        if (this.isClusterReady) {
            line = services.get(selectedStrategy).processLine(line, method, biasingSize);
            return line;
        } else {
            throw new ServiceNotReadyException();
        }
    }

    @Override
    public void buildCluster() {
        if (!isClusterReady) {
            init();
        }
    }

    @Override
    public void buildStrategy(SupportedProcessingStrategy strategy, List<String> documents) {
        services.get(strategy).build(documents); // This is really confusing as some require and some not
        connectors.get(strategy).connect(credentials);
    }

}
