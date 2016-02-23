/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableReaders;

import Abstractions.TableReader;
import Global.Options;
import Global.Options.SupportedTableStrategy;
import java.io.FileNotFoundException;
import org.apache.commons.lang.NotImplementedException;

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
