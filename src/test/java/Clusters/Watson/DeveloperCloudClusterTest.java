/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.Watson;

import Global.Options;
import static TestObjects.Paths.testfolder;
import static TestObjects.Paths.testoutput;
import Utilities.File.CFileOperations;
import Utilities.Json.JsonReader;
import Utilities.Structures.ReutersArticles;
import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.concept_insights.v2.ConceptInsights;
import com.ibm.watson.developer_cloud.concept_insights.v2.model.Annotations;
import com.ibm.watson.developer_cloud.concept_insights.v2.model.Graph;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Johannes töissä
 */
@Ignore public class DeveloperCloudClusterTest {
    // TODO Implement TemporaryFolder() for all folders, no need for "real" folders
    static String ConceptInsightsTestResponseFilePath = testoutput+"concept-insights-testresponse.json";
    static String exampleCall = "IBM Watson won the Jeopardy television show hosted by Alex Trebek";
    static String output = testoutput;
    static String testArticlesPath = testfolder;
    static String testArticleName = "D:\\Netbeans Projects\\Preprocessor\\raw data\\testing\\testres\\articles11.csv"; // fix the paths
    // TODO Clean up and paths to somewhere else
    public DeveloperCloudClusterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInsights() throws IOException {
        DeveloperCloudCluster dc = new DeveloperCloudCluster();
        dc.buildCluster();
        ConceptInsights ci = (ConceptInsights) dc.getWatsonService(Options.SupportedProcessingStrategy.ConceptInsights);
        Annotations annotations = ci.annotateText(Graph.WIKIPEDIA, exampleCall);
        // Reads file from already tested response
        JsonObject expJson = JsonReader.readJson(Options.WORKINGDIR + ConceptInsightsTestResponseFilePath);
        // Transforms respose to Json
        JsonObject result = JsonReader.createJson(annotations.toString());
        String expPretty = JsonReader.transformToPrettyPrint(expJson);
        String resultPretty = JsonReader.transformToPrettyPrint(result);
        Assert.assertTrue(expPretty.equals(resultPretty));
    }

    /**
     * Human test
     *
     * @throws IOException
     */
    @Test
    public void testInsights2() throws IOException {
        
        List<char[]> content = CFileOperations.getFileContentAsChars(testArticlesPath);
        ReutersArticles ra = new ReutersArticles(content, testArticleName);

        DeveloperCloudCluster dc = new DeveloperCloudCluster();
        ConceptInsights ci = (ConceptInsights) dc.getWatsonService(Options.SupportedProcessingStrategy.ConceptInsights);

        for (int i = 0; i < ra.getArticleCount(); i++) {
            Map<String, String> tm = ra.getArticle(i);
            String story = tm.get("story_text");
            String date = tm.get("msg_dt");
            String title = tm.get("message");
            Annotations annotations = ci.annotateText(Graph.WIKIPEDIA, story);
            String writeToPath = Options.WORKINGDIR + output + i+".json";
            FileUtils.writeStringToFile(new File(writeToPath),
                    annotations.toString());
        }

    }
}
