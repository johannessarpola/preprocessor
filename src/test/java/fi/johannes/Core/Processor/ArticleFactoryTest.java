/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core.Processor;

import fi.johannes.ArticleFactory.ChunkArticleBuilder;
import fi.johannes.Core.App;
import fi.johannes.Utilities.File.CFileOperations;
import fi.johannes.Utilities.File.CFolderOperations;
import fi.johannes.Utilities.Structures.ReutersArticles;
import org.junit.*;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ArticleFactoryTest {

    public ArticleFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of buildArticleChunk method, of class ChunkArticleBuilder.
     */
    @Test
    public void testBuildArticleChunk() throws Exception {
        String respath = App.getResource("headlines/chunks").getFile().getAbsolutePath();
        ChunkArticleBuilder art = new ChunkArticleBuilder(respath);
        System.out.println("buildArticleChunk");
        int expLength = CFileOperations.countLines(art.getChunk(0)) - 1; // Header is removed from size below
        int length = art.buildArticleChunk().getNumberOfArticles();
        Assert.assertEquals(expLength, length);

        ReutersArticles result = art.buildArticleChunk();
        for (int i = 0; i < 5; i++) {
            Assert.assertTrue(result.getArticleAsMap(i).toString().length() > 0);
        }
        String resultName = result.getSourceFilename();
        String expResultName = art.getChunkName(1);
        Assert.assertEquals(resultName, expResultName);

    }

    @Test
    public void testAllArticles() throws IOException {
        String respath = App.getResource("headlines/chunks").getFile().getAbsolutePath();
        ChunkArticleBuilder art = new ChunkArticleBuilder(respath);
        ReutersArticles result;
        List<String> chunks = CFolderOperations.getFilenamesInFolder(respath);
        for (int i = 0; i < 5; i++) {
            result = art.buildArticleChunk();
            String resultName = result.getSourceFilename();
            System.out.println(resultName);
            Assert.assertTrue(chunks.contains(resultName));
        }

    }
}
