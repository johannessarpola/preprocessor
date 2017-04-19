/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Json;

import fi.johannes.Core.App;
import fi.johannes.Utilities.File.CFileOperations;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johannes töissä
 */
public class JsonReaderTest {

    static String testJson;
    static String targetField = "1.2.3.4.5.6.7.test";
    static String targetValue = "ok";

    public JsonReaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        testJson = App.getResource("test.json").getFile().getAbsolutePath();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of readJson method, of class JsonReader.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testReadJson() throws Exception {
        System.out.println("readJson");
        String filepath = App.getResource("test.json").getFile().getAbsolutePath();
        String jsonString = CFileOperations.fileContentToString(filepath);
        JsonParser parser = new JsonParser();
        JsonObject expResult = parser.parse(jsonString).getAsJsonObject();
        String result = JsonReader.transformToPrettyPrint(JsonReader.readJson(filepath));
        jsonString = JsonReader.transformToPrettyPrint(result);
        Assert.assertTrue(result.length() > 0);
        Assert.assertEquals(jsonString.length(), result.length());
    }

    /**
     * Test of accessField method, of class JsonReader.
     */
    @Test
    public void testAccessField() {
        System.out.println("accessField");

        JsonObject json;
        try {
            json = JsonReader.readJson(testJson);
            String result = JsonReader.accessField(json, targetField);
            String expResult = targetValue;
            Assert.assertTrue(expResult.equals(result));
        } catch (IOException ex) {
            Logger.getLogger(JsonReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
