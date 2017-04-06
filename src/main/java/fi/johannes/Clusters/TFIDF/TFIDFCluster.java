/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.TFIDF;

import fi.johannes.Abstractions.Core.Cluster;
import fi.johannes.Abstractions.Core.GenericService;
import fi.johannes.Core.App;
import fi.johannes.Core.ClusterMapping;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Logging.GenLogging;

import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TFIDFCluster extends Cluster {

    public TFIDFCluster() {
        super(ClusterMapping.ClusterEnums.TFIDF);
    }

    @Override
    public String processLine(String line, App.SupportedProcessingParadigms method) throws ServiceNotReadyException, ClusterNoteadyException, UnhandledServiceException {
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
    @Override
    public void buildCluster() {
        try {
            map = new TFIDFStrategyMap(id);
            addServices();
        } catch (StrategyNotSupportedException ex) {
            GenLogging.logStackTrace_Error(getClass(), ex);
        }

    }
    private void addServices() throws StrategyNotSupportedException {
        for (App.SupportedProcessingStrategy s : strategies) {
            GenericService gs = map.initializeStrategy(s);
            services.put(s, gs);
        }
    }

    @Override
    public void buildStrategy(App.SupportedProcessingStrategy strategy, List<String> documents) {
        services.get(strategy).build(documents);
    }


}
