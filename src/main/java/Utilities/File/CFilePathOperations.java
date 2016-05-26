/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.File;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Johannes
 */
public class CFilePathOperations {
    
    public Path stringToPath(String str) throws FileNotFoundException{
        Path p = Paths.get(str);
        if(Files.exists(p)) return p;
        else {
            throw new FileNotFoundException();
        }
        
    }
    
}
