/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Corpuses.WikipediaTitles;

import Corpuses.Internal.BloomfilterCorpus;
import Global.Options;
import Utilities.File.CFolderOperations;
import Utilities.Logging.GeneralLogging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * https://dumps.wikimedia.org/backup-index.html
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikiCorpus extends BloomfilterCorpus{

    // TODO Cronjob: Update Corpus
    WikiFilter wikifilter;
    String pathToWikis;
    double accuracy;

    public WikiCorpus(String pathToWikis, double accuracy) {
        super(Options.SupportedCorpuses.Wikipedia);
        this.accuracy = accuracy;
        this.pathToWikis = pathToWikis;
        init();
    }

    public WikiCorpus(double accuracy) {
        super(Options.SupportedCorpuses.Wikipedia);
        this.accuracy = accuracy;
        pathToWikis = WikiPaths.WIKIFOLDER;
        init();
    }

    public WikiCorpus() {
        super(Options.SupportedCorpuses.Wikipedia);
        this.accuracy = 0.01;
        pathToWikis = WikiPaths.WIKIFOLDER;
        init();
    }
    @Override
    public void buildCorpus(){
        init();
    }
    private void init() {
        try {
            // int numberofwikis = CFolderOperations.countFiles(pathToWikis); (?)
            // TODO Create index for alphabets from wiki titles. 
            // Can achieve by HashSet and contains, and simple iterator (if contains do nothing else charat(0) -> iterator)
            LinkedList<List<String>> lwikis = CFolderOperations.readAllFilesInFolder(pathToWikis);
            ListIterator<List<String>> iter = lwikis.listIterator();
            List<String> titles = new ArrayList<>();
            while(iter.hasNext()) {
                titles.addAll(iter.next());
                iter.remove();
            }
            wikifilter = new WikiFilter(accuracy, titles);
        } catch (IOException ex) {
            GeneralLogging.logStackTrace_Error(this, ex);
        }
    }

    public boolean mightContain(String str) {
        return wikifilter.mightContain(str);
    }
    // TODO Add a fuzzy search for strings

    @Override
    public double reliabilityOfContain() {
        return wikifilter.reliability();
    }

}
