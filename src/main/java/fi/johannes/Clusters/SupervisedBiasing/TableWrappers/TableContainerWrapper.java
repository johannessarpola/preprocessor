/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing.TableWrappers;

import fi.johannes.Clusters.SupervisedBiasing.Internal.ExtensionStrategyMapper;
import fi.johannes.Clusters.SupervisedBiasing.Internal.TableHierarchy;
import fi.johannes.Global.Options;
import fi.johannes.TableReaders.TableContainer;
import fi.johannes.Utilities.Logging.CustomExceptions.TableNotSupportedException;
import fi.johannes.Utilities.Logging.GeneralLogging;

import java.io.FileNotFoundException;

/**
 * Wraps the TableReader here
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableContainerWrapper<T> {
    TableContainer<T> tableContainer;
    private boolean isReady = false;

    public TableContainerWrapper(String filepath) {
        Options.SupportedTableStrategy strategy = deductStrategy(filepath);
        createReader(strategy, filepath);
    }
    private void createReader(Options.SupportedTableStrategy strategy, String filepath) {
            tableContainer = new TableContainer(strategy,filepath);
    }
    public TableHierarchy<T> createTableHierarchy(){
        return new TableHierarchy(tableContainer.getTable());
    }
    /**
     * Deducts the correct strategy by using internal mapper
     * @param filepath
     * @return 
     */
    private Options.SupportedTableStrategy deductStrategy(String filepath) {
        try {
            Options.SupportedTableStrategy s = ExtensionStrategyMapper.getTableStrategy(filepath);
            isReady= true;
            return s;
        } catch (TableNotSupportedException | FileNotFoundException ex) {
            GeneralLogging.logStackTrace_Fatal(getClass(), ex);
        }
        isReady = false;
        return null;
    }

    public boolean isReady() {
        return isReady;
    }
    public void clear(){
        tableContainer.clear();
    }
}