/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.EntityDetection.WikipediaTitles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikipediaTitles {
    private Set<String> titles;
    
    /**
     * Creates 
     * @param lines
     */
    public WikipediaTitles(List<String> lines){
        titles = new HashSet<>();
        // TODO Operate on lines 'underscores' and such
        titles = addLines(lines);
    }
    public Set<String> getTitles() {
        return titles;
    }

    public void setTitles(Set<String> titles) {
        this.titles = titles;
    }

    private Set<String> addLines(List<String> lines) {
        Set<String> local = new HashSet<>(); 
        local.addAll(lines);
        return local;
    }
    
}
