/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.File;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CFolderOperationsTest {

    static String testfolder = System.getProperty("user.dir") + "/src/test/resources/testfolder";

    public CFolderOperationsTest() {
    }
    /**
     * Test of getFilesInFolder method, of class CFolderOperations.
     */
    @Test
    public void testAllFileMethods() throws IOException {
        CFolderOperations.recursiveDelete(testfolder + "1");
        CFolderOperations.createFolder(testfolder + "1");
        new File(testfolder + "1" + "/file").createNewFile();
        new File(testfolder + "1" + "/file2").createNewFile();
        Assert.assertEquals(2, CFolderOperations.getFilenamesInFolder(testfolder + "1").size());
        Assert.assertEquals(2, CFolderOperations.getFilenamesInFolder(testfolder + "1").size());
        Assert.assertEquals(2, CFolderOperations.readAllFilesInFolder(testfolder + "1").size());
        CFolderOperations.recursiveDelete(testfolder + "1");
        int i =0;
    }

    /**
     * Test of countFiles method, of class CFolderOperations.
     */
    @Test
    public void testCountFiles() throws IOException {
        CFolderOperations.recursiveDelete(testfolder + "2");
        CFolderOperations.createFolder(testfolder + "2");
        File f = new File(testfolder + "2" + "/file");
        f.createNewFile();
        Assert.assertEquals(1, CFolderOperations.countFiles(testfolder + "2"));
        CFolderOperations.recursiveDelete(testfolder + "2");
    }

    @Test
    public void testFolderCreationAndExist() throws NotDirectoryException {
        CFolderOperations.recursiveDelete(testfolder + "3");
        CFolderOperations.createFolder(testfolder + "3");
        assertThat(CFolderOperations.createFolder(testfolder + "3"), is(not(nullValue())));
        assertThat(CFolderOperations.doesFolderExist(testfolder + "3"), is(true));
        CFolderOperations.recursiveDelete(testfolder + "3");
    }

}
