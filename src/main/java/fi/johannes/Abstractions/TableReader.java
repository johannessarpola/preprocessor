/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions;

import fi.johannes.Core.AppConf.SupportedTableStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Interface for different toolsets to read tabular data to bias vectors
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
q */
public abstract class TableReader<E> {
    
    protected File file;
    
    public TableReader(String filepath) throws FileNotFoundException{
        file = new File(filepath);

    }
    
    public abstract List<List<E>> retrieveRows();
    public abstract List<E> retrieveHeaders();
    public abstract void reset();
    public abstract SupportedTableStrategy getName();
    
}
