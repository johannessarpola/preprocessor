/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson.Strategies;

import Clusters.Watson.Internal.WatsonConnector;
import Clusters.Watson.Internal.WatsonCredentialsStorage;
import Global.Options;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.service.WatsonService;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class AlchemyWrapper extends WatsonConnector {

    AlchemyLanguage client;

    // TODO This class is not done yet
    public AlchemyWrapper() {
        super(Options.SupportedProcessingStrategy.Alchemy);

    }

    @Override
    public void build(List<String> documents) {
        // need to do nothing
    }
    // TODO Test Alchemy api
    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // TODO Test Alchemy api
    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        // Nothing to reinit
    }

    @Override
    public void connect(WatsonCredentialsStorage cs) {
        AlchemyLanguage api = new AlchemyLanguage();
        api.setApiKey(cs.access("Keys.Alchemy Language"));
        this.client = api;
        isServiceReady = true;
    }

    @Override
    public WatsonService getServiceDirectly() {
        return client;
    }

}
