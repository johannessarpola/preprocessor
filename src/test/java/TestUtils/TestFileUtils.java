/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtils;

import Utilities.File.CFolderOperations;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Johannes
 */
public class TestFileUtils {
    public static final String testFilesFolder = "."+File.separator+"testfiles"+File.separator;
    /**
     * Create file for testing and return Path
     * @param path
     * @return
     * @throws IOException 
     */
    public static Path createFile(String path) throws IOException{
        String nm = path;
        String p = testFilesFolder+nm;
        Path ps = Paths.get(p);
        Files.createParentDirs(ps.toFile());
        Files.write("Test file", ps.toFile(), Charset.forName("UTF-8"));
        return Paths.get(p);
    }
    public static void cleanTestFiles(){
        CFolderOperations.recursiveDelete(testFilesFolder);
    }
    public static void cleanTestFile(Path p){
        p.toFile().deleteOnExit();
    }
    public static String classDotsToSeparators(String clazz){
        return clazz.replace(".", File.separator);
    }
}
