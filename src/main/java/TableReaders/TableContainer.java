/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableReaders;

import Global.Options.SupportedTableStrategy;
import Utilities.Structures.Table;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds the main logic of table reading and wraps the table-specific readers into interface
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableContainer {
    private List<String> header;
    private List<List<String>> rows;
    private Abstractions.TableReader tools;
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
            Logger.getLogger(TableContainer.class.getName()).log(Level.SEVERE, null, ex);
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
    private List<String> fetchHeader(){
        return tools.retrieveHeaders();
    }
    private List<List<String>> fetchRows(){
        return tools.retrieveRows();
    }
    private void init() {
        header = new ArrayList<>();
        rows = new ArrayList<>();
    }
    private void createReader(SupportedTableStrategy strategy, String filepath) throws FileNotFoundException {
        tools = TableStrategyMapper.getTableReader(strategy, filepath);
    }

    public List<String> getHeader() {
        return header;
    }

    public List<List<String>> getRows() {
        return rows;
    }
    public Table getTable(){
        return new Table(header, rows);
    }
}
