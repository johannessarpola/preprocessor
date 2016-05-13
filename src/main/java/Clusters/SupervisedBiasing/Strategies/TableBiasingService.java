/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.Strategies;

import Abstractions.GenericService;
import Clusters.SupervisedBiasing.Internal.TableBiasingConfiguration;
import Clusters.SupervisedBiasing.TableWrappers.TableContainerWrapper;
import Global.Options;
import Utilities.GeneralUtilityMethods;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Utilities.Logging.GeneralLogging;
import java.util.ArrayList;
import java.util.List;

/**
 * Basically checks each token and it's position in tabular data Hierarchy uses XLSX
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBiasingService extends GenericService {
    // TODO Why would we need multiple wrappers?
    private List<TableContainerWrapper> tcws;

    public TableBiasingService() {
        super(Options.SupportedProcessingStrategy.SupervisedBiasingWithTable);
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

    @Override
    public void build(List<String> documents) {
        // No need in this case
    }
    // It probably would be smarter to store K-V of the line where V would be weighed based on the processing 
    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException {
/*        List<TableHierarchy> ths = new ArrayList<>();
        for(TableContainerWrapper tcw : tcws){
            ths.add(tcw.getTableHierarchy());
        }
*/
        List<String> splitLine = GeneralUtilityMethods.splitWithWhitespace(line);
        // TODO Get weight for each word
        // TODO Return result
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
