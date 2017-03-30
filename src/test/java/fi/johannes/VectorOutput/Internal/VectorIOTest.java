/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.VectorOutput.Internal;

import fi.johannes.Utilities.File.CFileOperations;
import fi.johannes.Utilities.File.CFolderOperations;
import fi.johannes.VectorOutput.Vector.TokenVector;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class VectorIOTest {

    static String folder = fi.johannes.TestObjects.Paths.testfolder;
    static VectorIO vio;
    static List<TokenVector> ltv;
    static TokenVector v1 = fi.johannes.TestObjects.Objects.testTokenVector;
    static TokenVector v2 = fi.johannes.TestObjects.Objects.testTokenVector2;

    public VectorIOTest() throws NotDirectoryException {

    }

    @BeforeClass
    public static void setUpClass() throws NotDirectoryException {
        File f = CFolderOperations.createFolder(folder);
        vio = new VectorIO(f);
        ltv = new ArrayList<>();
        ltv.add(v1);
        ltv.add(v2);
    }
    @AfterClass
    public static void cleanup(){
        CFolderOperations.recursiveDelete(folder);
        
    }
    /**
     * Test of writeVectors method, of class VectorIO.
     */
    @Test
    public void testWriteVectors() throws Exception {
        vio.writeVectors(ltv);
        Assert.assertEquals(1, CFolderOperations.countFiles(folder));
        String resultingfilename = ltv.get(0).getSerial()+"..."+ltv.get(1).getSerial()+vio.getExtension();
        Assert.assertEquals(CFolderOperations.getFilenamesInFolder(folder).get(0), ltv.get(0).getSerial()+"..."+ltv.get(1).getSerial()+vio.getExtension());
        Assert.assertEquals(2, CFileOperations.countLines(folder+"/"+resultingfilename));
        
    }

}
