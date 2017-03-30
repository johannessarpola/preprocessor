/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Json;

import fi.johannes.Utilities.File.CFileOperations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Johannes töissä
 */
public class JsonReader {

    public static JsonObject readJson(String filepath) throws IOException {
        String jsonContent = CFileOperations.fileContentToString(filepath);
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonContent).getAsJsonObject();
        return json;
    }
    public static JsonObject createJson(String jsonContent) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonContent).getAsJsonObject();
        return json;
    }

    public static String transformToPrettyPrint(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);
        return prettyJson;
    }

    public static String transformToPrettyPrint(JsonObject json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);
        return prettyJson;
    }
    // TODO Should throw exception if field is not found
    public static String accessField(JsonObject json, String field) {
        String[] fields = new String[1];
        fields[0] = field;
        if (field.contains(".")) {
            String[] f = field.split("\\.");
            fields = new String[f.length];
            fields = f;
        }
        String value = "";
        int i = 0;
        ArrayList<JsonObject> jss = new ArrayList<>();
        for (String f : fields) {
            if (i == fields.length - 1) {
                // Get the previous element
                JsonObject temp = jss.get(i - 1);
                value = temp.get(f).getAsString();
                break;
            }
            if (i == 0) {
                jss.add((JsonObject) json.get(f));

            } else {
                jss.add((JsonObject) jss.get(i-1).get(f));
            }
            i++;
        }
        return value;
    }
}
