/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import fi.johannes.Core.Options;
import fi.johannes.Utilities.File.CFileOperations;
import org.junit.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Johannes töissä
 */
public class ReutersArticlesTest {
    // TODO Implement TemporaryFolder() for all folders, no need for "real" folders

    static String testArticlesPath = Options.WORKINGDIR + "/testres/articles11.csv";
    static String testArticleName = "articles11.csv";

    public ReutersArticlesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    // TODO Missing the files as well
    @Ignore public void testConstruct() throws IOException {
        List<char[]> content = CFileOperations.getFileContentAsChars(testArticlesPath);
        int linecount = CFileOperations.countLines(testArticlesPath);
        ReutersArticles ra = new ReutersArticles(content, testArticleName);
        Map<String, String> article = ra.getArticle(0);
        String[] keys = (String[]) article.keySet().toArray(new String[article.keySet().size()]);
        Assert.assertTrue(keys.length > 0);

        Assert.assertTrue(linecount == ra.getArticleCount() + 1);

        String first = article.get(keys[0]);
        String second = article.get(keys[1]);
        String third = article.get(keys[2]);

        Assert.assertTrue(first.length() > 0);
        Assert.assertTrue(second.length() > 0);
        Assert.assertTrue(third.length() > 0);
        Assert.assertFalse(third.equals(first));
        Assert.assertFalse(second.equals(first));
        Assert.assertFalse(third.equals(second));
    }

}
