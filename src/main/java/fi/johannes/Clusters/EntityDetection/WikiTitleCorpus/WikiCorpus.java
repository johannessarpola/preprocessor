/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection.WikiTitleCorpus;

import fi.johannes.Clusters.EntityDetection.Internal.BloomfilterCorpus;
import fi.johannes.Core.App;
import fi.johannes.Utilities.Logging.GenLogging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://dumps.wikimedia.org/backup-index.html
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikiCorpus extends BloomfilterCorpus {

    private WikiBloomfilter wikiBloomfilter;
    private String pathToWikis;
    private double accuracy;

    public WikiCorpus(String pathToWikis) {
        super(App.SupportedCorpuses.WikipediaCorpus);
        this.accuracy =  0.01;;
        this.pathToWikis = pathToWikis;
        init();
    }

    @Override
    public void buildCorpus() {
        init();
    }

    private void init() {
        try {
            Stream<String> titles = titleStream(pathToWikis);
            wikiBloomfilter = new WikiBloomfilter(accuracy, titles);
        } catch (Exception ex) {
            GenLogging.logStackTrace_Error(this, ex);
        }
    }
    private Stream<String> titleStream(String path) throws IOException {
        return Files.list(Paths.get(path)).parallel()
                .map(p -> {
                    try {
                        return Optional.<Set<String>>of(Files.lines(p).collect(Collectors.toSet()));
                    } catch (IOException e) { return Optional.<Set<String>>empty(); }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .map(WikiTransformer::transformTitle);
    }


    public boolean mightContain(String str) {
        return wikiBloomfilter.mightContain(str);
    }

    @Override
    public double reliabilityOfContain() {
        return wikiBloomfilter.reliability();
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
