/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Core.Processor;

import fi.johannes.ArticleFactory.ChunkArticleBuilder;
import fi.johannes.Core.Options;
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
// TODO Coupled to paths as well
@Ignore public class ArticleFactoryTest {

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
