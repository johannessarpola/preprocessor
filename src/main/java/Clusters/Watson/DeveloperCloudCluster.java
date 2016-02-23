/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson;

import Abstractions.GenericCluster;
import Clusters.Watson.Strategies.AlchemyWrapper;
import Clusters.Watson.Strategies.ConceptsInsightsWrapper;
import Global.Options;
import Global.Options.SupportedProcessingParadigms;
import Global.Options.SupportedProcessingStrategy;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Clusters.Watson.Internal.WatsonCredentialsStorage;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.concept_insights.v2.ConceptInsights;
import com.ibm.watson.developer_cloud.service.WatsonService;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johannes töissä
 */
public class DeveloperCloudCluster extends GenericCluster {

    private static HashMap<SupportedProcessingStrategy, WatsonService> servicesCloud;
    //private static HashMap<SupportedProcessingStrategy, GenericService> services;
    private static WatsonCredentialsStorage credentials;

//    private SupportedProcessingStrategy selectedStrategy;
//    boolean readytoUse = false;
    public DeveloperCloudCluster() {
        super(Options.SupportedClusters.Watson);
        servicesCloud = new HashMap<>();
        credentials = new WatsonCredentialsStorage();
        addServices();

        try {
            init();
        } catch (Exception ex) {
            Logger.getLogger(DeveloperCloudCluster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        setupServiceWrappers();
        this.isClusterReady = true;
    }

    /**
     * Adds supported *Watson* Services from IBM developer cloud
     */
    private void addServices() {
        servicesCloud.put(SupportedProcessingStrategy.ConceptInsights, new ConceptInsights());
        servicesCloud.put(SupportedProcessingStrategy.Alchemy, new AlchemyLanguage());
    }

    /**
     * Sets the access rules for different services
     */
    private void setupServiceWrappers() {
        Set<SupportedProcessingStrategy> keys = servicesCloud.keySet();
        for (SupportedProcessingStrategy k : keys) {
            WatsonService ser = servicesCloud.get(k);
            // Set user:pass for each Watson service
            ser.setUsernameAndPassword(credentials.access("credentials.username"), credentials.access("credentials.password"));
            if (k == SupportedProcessingStrategy.ConceptInsights) {
                setupConceptInsightsWrapper(k, ser);
            } else if (k == SupportedProcessingStrategy.Alchemy) {
                setupAlchemyWrapper(k, ser);
            }
        }
    }

    private void setupConceptInsightsWrapper(SupportedProcessingStrategy k, WatsonService ser) {
        ConceptsInsightsWrapper ciw = new ConceptsInsightsWrapper();
        ciw.connect(ser);
        services.put(k, ciw);
    }

    private void setupAlchemyWrapper(SupportedProcessingStrategy k, WatsonService ser) {
        servicesCloud.get(k).setApiKey(credentials.access("Keys.Alchemy Language"));
        AlchemyWrapper alc = new AlchemyWrapper();
        alc.connect(ser);
        services.put(k, alc);
    }

    /**
     * Gets the service called something
     *
     * @param name
     * @return
     */
    public WatsonService getWatsonService(SupportedProcessingStrategy name) {
        return servicesCloud.get(name);
    }

    /**
     * Returns the available services
     *
     * @return
     */
    public Set<SupportedProcessingStrategy> getServiceKeys() {
        return servicesCloud.keySet();
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
    public void clear() {
        try {
            init();
        } catch (Exception ex) {
            Logger.getLogger(DeveloperCloudCluster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void buildCluster() {
        if (!isClusterReady) {
            init();
        }
    }

    @Override
    public void clearStrategy(SupportedProcessingStrategy strategy) {
        services.get(strategy).clear();
    }

    @Override
    public void buildStrategy(SupportedProcessingStrategy strategy, List<String> documents) {
        services.get(strategy).preloadDocuments(documents);
    }
}
