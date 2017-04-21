/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection.Internal;

import fi.johannes.Core.AppConf.SupportedCorpuses;

/**
 * Is a corpus using Bloomfilter
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class BloomfilterCorpus implements EntityCorpus {
    SupportedCorpuses id;
    
    public BloomfilterCorpus(SupportedCorpuses id) {
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
    public SupportedCorpuses getId(){
        return id;
    }
}
