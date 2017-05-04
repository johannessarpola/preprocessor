package fi.johannes.Clusters.EntityDetection;

import fi.johannes.Abstractions.Cluster;
import fi.johannes.Clusters.EntityDetection.Internal.EntityCorpus;
import fi.johannes.Clusters.EntityDetection.WikiTitleCorpus.WikiCorpus;
import fi.johannes.Core.AppConf;
import fi.johannes.Core.AppConf.SupportedProcessingStrategy;
import fi.johannes.Core.ClusterMapping.ClusterEnums;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;

import java.util.List;

/**
 * Created by Johannes on 20.4.2017.
 */
public class EntityDetectionCluster extends Cluster {
    private EntityDetectionService service;
    private EntityCorpus selectedCorpus;

    public EntityDetectionCluster() {
        super(ClusterEnums.EntityDetection);
    }

    @Override
    public void initClusterWithConf(AppConf conf) {
        assert conf != null;
        super.setConf(conf);
        this.isClusterReady = true;
    }

    @Override
    public String processLine(String line, AppConf.SupportedProcessingMethods method) throws ServiceNotReadyException, UnhandledServiceException {
        if (service != null && service.isServiceReady()) {
            switch (method) {
                case Replace:
                    return service.processLineByReplace(line, 0);
                case Append:
                    return service.processLineByAppend(line, 0);
                default:
                    throw new UnhandledServiceException();
            }
        }
        else {
            throw new ServiceNotReadyException();
        }
    }


    @Override
    public void buildStrategy(SupportedProcessingStrategy strategy, List<String> documents) {
        // todo
        switch (strategy) {
            case WikipediaTitles:
                selectedCorpus = new WikiCorpus(super.getConf().getPaths().getEntities());
                service = new EntityDetectionService(selectedCorpus);
                service.build(null); // hnngh
            default:
                selectedCorpus = new WikiCorpus(super.getConf().getPaths().getEntities());
                service = new EntityDetectionService(selectedCorpus);
                service.build(null);
        }
    }
}
