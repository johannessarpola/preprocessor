/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing.Internal;

import fi.johannes.Core.Options;
import fi.johannes.Utilities.File.CFileOperations;
import fi.johannes.Utilities.Logging.CustomExceptions.TableNotSupportedException;

import java.io.FileNotFoundException;

/**
 * Maps the extensions to correct strategy
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ExtensionStrategyMapper {
    /**
     * Maps file (extension) to correct SupportedTableStrategy <enum> 
     * @param fileExtension
     * @return SupportedTableStrategy
     * @throws TableNotSupportedException, FileNotFoundException
     */
    public static Options.SupportedTableStrategy getTableStrategy(String filepath) throws TableNotSupportedException, FileNotFoundException{
        String fileExtension = CFileOperations.getFileExtension(filepath);
        fileExtension = fileExtension.toLowerCase();
        switch(fileExtension){
            case "xlsx":
                return Options.SupportedTableStrategy.xlsx;
            default:
                throw new TableNotSupportedException();
        }
    }
}
