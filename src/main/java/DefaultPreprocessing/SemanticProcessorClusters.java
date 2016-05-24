/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DefaultPreprocessing;

import Abstractions.Core.GenericCluster;
import Abstractions.Core.GenericClusterMethods;
import Global.Options.SupportedProcessingParadigms;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class SemanticProcessorClusters {

    private List<GenericCluster> clusters;
    private SupportedProcessingParadigms selectedMethod;
    private List<String> clusterNames;

    public SemanticProcessorClusters() {

        init();
    }

    private void init() {

//        for (GenericClusterMethods cluster : semanticProcessingClusters) {
//            clusters.add(cluster.getInstance());
//            clusterNames.add(cluster.getId());
//        }

    }

    public String processLineWithAllServices(String line) throws ServiceNotReadyException {
        for (GenericClusterMethods c : clusters) {
            //line = c.processLine(line, selectedMethod);
        }
        return line;
    }
}
