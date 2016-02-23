/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Corpuses.WikipediaTitles;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikiFilter {

    BloomFilter<String> filter;
    int size;
    double buffer = 1.2;
    double accuracy = 0.01;

    /**
     *
     * @param accuracy
     * @param items
     */
    public WikiFilter(double accuracy, List<String> items) {
        this.accuracy = accuracy;
        this.size = items.size();
        init();
        addStrings(items);
    }
    private void addStrings(List<String> list) {
        // TODO Add common mutations too ?
        list.stream().forEach((s) -> {
            filter.put(s);
        });
    }

    private void createBloomfilter(int size, double accuracy) {
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
    public double reliability(){
        return filter.expectedFpp();
    }
}
