/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DefaultPreprocessing;

import Abstractions.GenericCluster;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Global.Options.SupportedProcessingParadigms;
import java.util.List;
import Abstractions.GenericClusterMethods;

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
            line = c.processLine(line, selectedMethod);
        }
        return line;
    }
}
