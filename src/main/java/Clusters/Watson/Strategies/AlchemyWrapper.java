/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson.Strategies;

import Abstractions.GenericService;
import Clusters.Watson.Internal.Wrapper;
import Global.Options;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import com.ibm.watson.developer_cloud.service.WatsonService;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class AlchemyWrapper extends GenericService implements Wrapper{

    public AlchemyWrapper() {
        super(Options.SupportedProcessingStrategy.Alchemy);
    }

    @Override
    public void preloadDocuments(List<String> documents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void connect(WatsonService ws) {
        System.out.println(this.getClass()+" connect() not done yet");
    }

}
