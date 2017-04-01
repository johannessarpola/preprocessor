/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.TableReaders;

import fi.johannes.Abstractions.Structures.TableReader;
import fi.johannes.Core.Options;
import fi.johannes.Core.Options.SupportedTableStrategy;
import org.apache.commons.lang.NotImplementedException;

import java.io.FileNotFoundException;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableStrategyMapper {
    public static TableReader getTableReader(SupportedTableStrategy strategy, String filepath) throws FileNotFoundException{
        if(strategy==Options.SupportedTableStrategy.xlsx){
            return new XLSXReader(filepath);
        }
        else {
            throw new NotImplementedException(strategy+" strategy not done yet");
        }
    }
}
