/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection.WikiTitleCorpus;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikiBloomfilter {

    private BloomFilter<String> filter;
    private long size;
    private double buffer = 1.2;
    private double accuracy = 0.01;

    /**
     * @param accuracy
     * @param set
     */
    public WikiBloomfilter(double accuracy, Set<String> set) {
        this.accuracy = accuracy;
        this.size = set.size();
        init();
        addStrings(set);
    }

    private void addStrings(Set<String> list) {
        list.forEach(s -> filter.put(s));
    }

    private void createBloomfilter(long size, double accuracy) {
        filter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, accuracy);
    }

    public boolean mightContain(String str) {
        return filter.mightContain(str);
    }

    private void init() {
        // Add buffer to size since bloomFilter doesn't work if it exceeds size
        this.size = (int) (size * buffer);
        createBloomfilter(size, accuracy);
    }

    public double reliability() {
        return filter.expectedFpp();
    }
}
