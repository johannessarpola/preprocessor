/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.File;

import fi.johannes.Core.App;
import fi.johannes.Utilities.Resources.ResourceList;
import org.junit.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CFileOperationsTest {

    public CFileOperationsTest() {
    }


    /**
     * Test of fileContentToString method, of class CFileOperations.
     */
    @Test
    public void testFileContentToString() throws Exception {
        System.out.println("fileContentToString");
        String filepath = App.getStopwordsResource().getFile().getPath();
        List<String> lines = Files.readAllLines(Paths.get(App.getStopwordsResource().getFile().getPath()));
        String[] result = CFileOperations.fileContentToString(filepath).split("\n");
        assertEquals(lines.size(), result.length);
    }

    /**
     * Test of getFileContentAsBytes method, of class CFileOperations.
     */
    @Test
    public void testFileContentUTF8() throws Exception {
        System.out.println("fileContentUTF8");
        String filepath = App.getStopwordsResource().getFile().getPath();
        List<String> lines = Files.readAllLines(Paths.get(App.getStopwordsResource().getFile().getPath()));
        List<byte[]> result = CFileOperations.getFileContentAsBytes(filepath);
        assertEquals(lines.size(), result.size());
        
    }

    /**
     * Test of countLines method, of class CFileOperations.
     */
    @Test
    public void testCountLines() throws Exception {
        System.out.println("countLines");
        String filepath = App.getStopwordsResource().getFile().getPath();
        int expResult = Files.readAllLines(Paths.get(App.getStopwordsResource().getFile().getPath())).size()-1; // Reads a line in the end which is the last caraige return
        int result = CFileOperations.countLines(filepath);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFilesInFolder method, of class CFileOperations.
     */

    @Test
    public void testGetFilesInFolder() throws IOException {
        System.out.println("getFilesInFolder");
        int count = (int) Files.list(Paths.get("src/main/resources")).count();
        File resourcesDirectory = new File("src/main/resources");
        File[] result = CFolderOperations.getFilesInFolder(resourcesDirectory.getAbsolutePath());
        assertEquals(count, result.length);

    }

}
