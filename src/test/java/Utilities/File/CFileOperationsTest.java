/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.File;

import Global.Options;
import Utilities.File.CFileOperations;
import Utilities.File.CFolderOperations;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CFileOperationsTest {
// TODO These tests need a update to modern standard e.g FolderOperation tests cleanup and such
    
    public CFileOperationsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of fileContentToString method, of class CFileOperations.
     */
    @Test
    public void testFileContentToString() throws Exception {
        System.out.println("fileContentToString");
        String filepath = Options.STOPWORDSPATH;
        List<String> lines = Files.readAllLines(Paths.get(Options.STOPWORDSPATH));
        String[] result = CFileOperations.fileContentToString(filepath).split("\n");
        Assert.assertEquals(lines.size(), result.length);
    }

    /**
     * Test of getFileContentAsBytes method, of class CFileOperations.
     */
    @Test
    public void testFileContentUTF8() throws Exception {
        System.out.println("fileContentUTF8");
        String filepath = Options.STOPWORDSPATH;
        List<String> lines = Files.readAllLines(Paths.get(Options.STOPWORDSPATH));
        List<byte[]> result = CFileOperations.getFileContentAsBytes(filepath);
        Assert.assertEquals(lines.size(), result.size());
        
    }

    /**
     * Test of countLines method, of class CFileOperations.
     */
    @Test
    public void testCountLines() throws Exception {
        System.out.println("countLines");
        String filepath = Options.STOPWORDSPATH;
        int expResult = Files.readAllLines(Paths.get(Options.STOPWORDSPATH)).size()-1; // Reads a line in the end which is the last caraige return
        int result = CFileOperations.countLines(filepath);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getFilesInFolder method, of class CFileOperations.
     */
    // TODO Coupled to path as well, FIX
    @Test
    @Ignore public void testGetFilesInFolder() {
        System.out.println("getFilesInFolder");
        File[] expResult = new File(Options.CHUNKS).listFiles();
        File[] result = CFolderOperations.getFilesInFolder(Options.CHUNKS);
        int i =0;
        for(File f : expResult) {
            Assert.assertTrue(expResult[i].getName().equals(result[i].getName()));
            i++;
        }
    }

}
