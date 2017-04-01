/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Processing;

import fi.johannes.Core.Options;
import fi.johannes.Utilities.Logging.GeneralLogging;

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
    private String filepath;
    
    // TODO Could be db for different languages
    public Stopwords(String stopwordsFilename){
        this.filepath = Options.RESOURCESDIR+stopwordsFilename;
        init();
    }
    public Stopwords(){
        this.filepath = Options.STOPWORDSPATH;
        init();
    }

    private void init() {
        try {
            stopWords.addAll(importStopwords());
        } catch (IOException ex) {
            GeneralLogging.logStackTrace_Error(getClass(), ex);
        }
    }

    private List<String> importStopwords() throws IOException {
        List<String> words = Files.readAllLines(Paths.get(filepath));
        return words;
    }
    public Set<String> getStopwords(){
        return stopWords;
    }
    
}
