/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.TableReaders;

import fi.johannes.Abstractions.TableReader;
import fi.johannes.Core.AppConf.SupportedTableStrategy;
import fi.johannes.Utilities.Logging.Logging;
import fi.johannes.Utilities.Structures.Table;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the main logic of table reading and wraps the table-specific readers into interface
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableContainer<E> {
    private List<E> header;
    private List<List<E>> rows;
    private TableReader tools;
    private boolean isReady;
    
    public TableContainer(SupportedTableStrategy strategy, String filepath){
        try {
            init();
            createReader(strategy, filepath);
            isReady = tools != null;
            if(isReady){
                getContent();
            }
        } catch (FileNotFoundException ex) {
             Logging.logStackTraceError(getClass(), ex);
        }
    }
    public void clear(){
        this.header.clear();
        this.rows.clear(); 
        isReady = false;
    }
    public void rebuild(){
        getContent();
    }
    private void getContent(){
        this.header = fetchHeader();
        this.rows = fetchRows();
        isReady = true;
    }
    private List<E> fetchHeader(){
        return tools.retrieveHeaders();
    }
    private List<List<E>> fetchRows(){
        return tools.retrieveRows();
    }
    private void init() {
        header = new ArrayList<>();
        rows = new ArrayList<>();
    }
    private void createReader(SupportedTableStrategy strategy, String filepath) throws FileNotFoundException {
        tools = TableStrategyMapper.getTableReader(strategy, filepath);
    }

    public List<E> getHeader() {
        return header;
    }

    public List<List<E>> getRows() {
        return rows;
    }
    public Table<E> getTable(){
        return new Table<>(header, rows);
    }
}
