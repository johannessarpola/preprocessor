/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TableBiasing.Strategies;

import Abstractions.GenericService;
import Clusters.TableBiasing.Internal.TableBiasingConfiguration;
import Clusters.TableBiasing.Wrappers.TableContainerWrapper;
import Global.Options;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Utilities.Logging.GeneralLogging;
import java.util.ArrayList;
import java.util.List;

/**
 * Basically checks each token and it's position in tabular data Hierarchy
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBiasing extends GenericService {

    private List<TableContainerWrapper> tcws;

    public TableBiasing() {
        super(Options.SupportedProcessingStrategy.Table);
        init();
    }

    private void init() {
        List<String> paths = TableBiasingConfiguration.getResourcePaths(); // TODO Implement resource paths from JSON conf
        tcws = new ArrayList();
        for (String p : paths) {
            // Create Wrapper for each resource file
            TableContainerWrapper tcw = new TableContainerWrapper(p);
            if (tcw.isReady()) {
                tcws.add(tcw);
            }
            else {
                GeneralLogging.logMessage_Error(getClass(),Utilities.Logging.Messages.ClustersDomain.msg_info_noTablesAdded);
            }
        }
    }

    // TableReaders: XLSX
    @Override
    public void preloadDocuments(List<String> documents) {
        // No need in this case
    }
    // It probably would be smarter to store K-V of the line where V would be weighed based on the processing 
    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException {
        // Add words n-times to the end of line depending on the place in hierarchy
        return "";
    }

    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException {
        // Replace the whole strings with word times n depending on weight
        return "";
    }

    @Override
    public void clear() {
        tcws.stream().forEach((tw) -> {
            tw.clear();
        });
    }

}
