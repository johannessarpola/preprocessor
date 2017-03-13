/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.Strategies;

import Abstractions.Core.GenericService;
import Clusters.SupervisedBiasing.Internal.StringTableHierarchy;
import Clusters.SupervisedBiasing.Internal.TableBiasingConfiguration;
import Clusters.SupervisedBiasing.TableWrappers.StringTableContainerWrapper;
import Global.Options;
import Utilities.GeneralUtilities;
import Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import Utilities.Logging.CustomExceptions.UnevenSizedListsException;
import Utilities.Logging.CustomExceptions.UnhandledServiceException;
import Utilities.Logging.GeneralLogging;
import Utilities.Structures.FinalizedPair;

import java.util.ArrayList;
import java.util.List;

import static Clusters.SupervisedBiasing.Strategies.TableBiasingServiceMethods.getWeightsForLine;


/**
 * Basically checks each token and it's position in tabular data Hierarchy uses
 * XLSX
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBiasingService extends GenericService {

    // TODO Why would we need multiple wrappers?
    private List<StringTableContainerWrapper> tcws;
    List<StringTableHierarchy> ths;

    public TableBiasingService() {
        super(Options.SupportedProcessingStrategy.SupervisedBiasingWithTable);
        init();
    }

    private void init() {
        List<String> paths = TableBiasingConfiguration.getResourcePaths();
        tcws = new ArrayList();
        for (String p : paths) {
            // Create Wrapper for each resource file
            StringTableContainerWrapper tcw = new StringTableContainerWrapper(p);
            if (tcw.isReady()) {
                tcws.add(tcw);
            } else {
                GeneralLogging.logMessage_Error(getClass(), Utilities.Logging.Messages.ClustersDomain.msg_info_noTablesAdded);
            }
        }
    }

    @Override
    public void build(List<String> documents) {
        ths = getHierarchies();
        this.isServiceReady = true;
    }

    private String processingLogic(String line, int biasingSize) throws UnevenSizedListsException {
        List<String> splitLine = GeneralUtilities.splitWithWhitespace(line);
        //List<StringTableHierarchy> ths = getHierarchies();
        // This should be in the same order as the words
        List<Double> weights = getWeightsForLine(splitLine, ths);
        List<FinalizedPair<String, Double>> highest = TableBiasingServiceMethods.getHighestWords(splitLine, weights, biasingSize);
        String str = TableBiasingServiceMethods.flatten(highest);
        return str;
    }

    /**
     * Appends the stuff into the end
     *
     * @param line
     * @param biasingSize
     * @return
     * @throws ServiceNotReadyException
     */
    @Override
    public String processLineByAppend(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        if(!isServiceReady) throw new ServiceNotReadyException();
        try {
            String processed = processingLogic(line, biasingSize);
            return line + processed;
        } catch (UnevenSizedListsException ex) {
            // TODO Fix logging
            throw new UnhandledServiceException();
        }
    }

    /**
     * Replaces thel ine with the weigthed stuff
     *
     * @param line
     * @param biasingSize
     * @return
     * @throws ServiceNotReadyException
     */
    @Override
    public String processLineByReplace(String line, int biasingSize) throws ServiceNotReadyException, UnhandledServiceException {
        if(!isServiceReady) throw new ServiceNotReadyException();
        try {
            return processingLogic(line, biasingSize);
        } catch (UnevenSizedListsException ex) {
            // TODO Fix logging
            throw new UnhandledServiceException();
        }
    }

    @Override
    public void clear() {
        tcws.stream().forEach((tw) -> {
            tw.clear();
        });
    }

    private List<StringTableHierarchy> getHierarchies() {
        List<StringTableHierarchy> ths = new ArrayList<>();
        for (StringTableContainerWrapper tcw : tcws) {
            ths.add(tcw.createTableHierarchy());
        }
        return ths;
    }
}
