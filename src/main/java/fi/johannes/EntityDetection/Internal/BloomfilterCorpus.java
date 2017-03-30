/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.EntityDetection.Internal;

import fi.johannes.Global.Options;

/**
 * Is a corpus using Bloomfilter
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class BloomfilterCorpus implements EntityCorpus {
    Options.SupportedCorpuses id;
    
    public BloomfilterCorpus(Options.SupportedCorpuses id) {
        this.id = id;
    }
    /**
     * Basically we contract the doesContain to mightContain (a bit silly)
     * @param word
     * @return 
     */
    public boolean doesContain(String word){
        return mightContain(word);
    }
    public abstract boolean mightContain(String word);
    public abstract void buildCorpus();
    public abstract double reliabilityOfContain();
    public Options.SupportedCorpuses getId(){
        return id;
    }
}
