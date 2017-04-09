/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import fi.johannes.Core.App;
import fi.johannes.Utilities.File.CFileOperations;
import org.junit.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Johannes töissä
 */
public class ReutersArticlesTest {
    public ReutersArticlesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testConstruct() throws IOException {
        String absolutePath = App.getResource("headlines/headlines-docs-100.csv").getFile().getAbsolutePath();
        List<char[]> content = CFileOperations.getFileContentAsChars(absolutePath);
        int linecount = CFileOperations.countLines(absolutePath);
        ReutersArticles ra = new ReutersArticles(content, "headlines-docs-100.csv");
        Map<String, String> article = ra.getArticleAsMap(0);
        String[] keys = (String[]) article.keySet().toArray(new String[article.keySet().size()]);
        assertTrue(keys.length > 0);

        assertTrue(linecount == ra.getArticleCount() + 1);

        String first = article.get(keys[0]);
        String second = article.get(keys[1]);
        String third = article.get(keys[2]);

        assertTrue(first.length() > 0);
        assertTrue(second.length() > 0);
        assertTrue(third.length() > 0);
        Assert.assertFalse(third.equals(first));
        Assert.assertFalse(second.equals(first));
        Assert.assertFalse(third.equals(second));
    }

    @Test
    public void testDto() throws IOException {
        String absolutePath = App.getResource("headlines/headlines-docs-100.csv").getFile().getAbsolutePath();
        List<char[]> content = CFileOperations.getFileContentAsChars(absolutePath);
        ReutersArticles ra = new ReutersArticles(content, "headlines-docs-100.csv");
        ReutersArticles.ArticleDto dto = ReutersArticles.ArticleDto.from(ra.getArticle(0));
        assertTrue(dto.getDate() != null);
        assertTrue(dto.getTitle() != null);
        assertTrue(dto.getStory() != null);

    }

}
