/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.UnsupervisedBiasing;

import fi.johannes.Abstractions.Cluster;
import fi.johannes.Abstractions.GenericService;
import fi.johannes.Core.AppConf;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Core.AppConf.SupportedProcessingMethods;
import fi.johannes.Core.ClusterMapping.ClusterEnums;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.StrategyNotSupportedException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import fi.johannes.Utilities.Logging.Logging;

import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class UnsupervisedBiasingCluster extends Cluster {

    public UnsupervisedBiasingCluster() {
        super(ClusterEnums.UnsupervisedBiasing);
    }

    @Override
    public String processLine(String line, SupportedProcessingMethods method) throws ServiceNotReadyException, ClusterNoteadyException, UnhandledServiceException {
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
    public void initClusterWithConf(AppConf conf) {
        try {
            super.setConf(conf);
            serviceMap = new UnsupervisedStrategyMap(clusterId);
            addServices();
            this.isClusterReady = true;
        } catch (StrategyNotSupportedException ex) {
            Logging.logStackTraceError(getClass(), ex);
        }

    }
    private void addServices() throws StrategyNotSupportedException {
        for (SupportedProcessingStrategy s : strategies) {
            GenericService gs = serviceMap.initializeStrategy(s);
            services.put(s, gs);
        }
    }

    @Override
    public void buildStrategy(SupportedProcessingStrategy strategy, List<String> documents) {
        services.get(strategy).build(documents);
    }


}
