/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Processing;

import fi.johannes.Core.App;
import fi.johannes.Utilities.Logging.GenLogging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Johannes Sarpola
 */
public class Stopwords {
    private Set<String> stopWords = new HashSet<>();
    protected String filepath;


    public Stopwords(){
        try {
            this.filepath = App.getStopwordsResource().getFile().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        try {
            stopWords.addAll(readStopWords());
        } catch (IOException ex) {
            GenLogging.logStackTrace_Error(getClass(), ex);
        }
    }

    private List<String> readStopWords() throws IOException {
        List<String> words = Files.readAllLines(Paths.get(filepath));
        return words;
    }
    public Set<String> getStopwords(){
        return stopWords;
    }
    
}
