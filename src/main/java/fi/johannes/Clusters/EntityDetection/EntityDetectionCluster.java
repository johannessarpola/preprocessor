package fi.johannes.Clusters.EntityDetection;

import fi.johannes.Abstractions.Core.Cluster;
import fi.johannes.Core.App;
import fi.johannes.Core.ClusterMapping;
import fi.johannes.Core.ClusterMapping.ClusterEnums;
import fi.johannes.Utilities.Logging.CustomExceptions.ClusterNoteadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;

import java.util.List;

/**
 * Created by Johannes on 20.4.2017.
 */
public class EntityDetectionCluster  extends Cluster {

    public EntityDetectionCluster() {
        super(ClusterEnums.EntityDetection);
    }

    @Override
    public String processLine(String line, App.SupportedProcessingMethods method) throws ServiceNotReadyException, ClusterNoteadyException, UnhandledServiceException {
        return null;
    }

    @Override
    public void buildCluster() {

    }

    @Override
    public void buildStrategy(App.SupportedProcessingStrategy strategy, List<String> documents) {

    }
}
