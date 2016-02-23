/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor;

import ArticleFactory.ChunkArticleBuilder;
import Global.Options;
import Utilities.File.CFileOperations;
import Utilities.File.CFolderOperations;
import Utilities.Structures.ReutersArticles;
import java.io.IOException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
        ChunkArticleBuilder art = new ChunkArticleBuilder();

        System.out.println("buildArticleChunk");
        int expLength = CFileOperations.countLines(art.getChunk(0)) - 1; // Header is removed from size below
        int length = art.buildArticleChunk().getNumberOfArticles();
        Assert.assertEquals(expLength, length);

        ReutersArticles result = art.buildArticleChunk();
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(result.getArticle(i).toString().length() > 0);
        }
        String resultName = result.getSourceFilename();
        String expResultName = art.getChunkName(1);
        Assert.assertEquals(resultName, expResultName);

    }

    @Test
    public void testAllArticles() throws IOException {
        ChunkArticleBuilder art = new ChunkArticleBuilder();
        ReutersArticles result;
        List<String> chunks = CFolderOperations.getFilenamesInFolder(Options.CHUNKS);
        int chunksNumber = art.getNumberOfChunks();
        for (int i = 0; i < 5; i++) {
            result = art.buildArticleChunk();
            String resultName = result.getSourceFilename();
            System.out.println(resultName);
            Assert.assertTrue(chunks.contains(resultName));
        }

    }
}
