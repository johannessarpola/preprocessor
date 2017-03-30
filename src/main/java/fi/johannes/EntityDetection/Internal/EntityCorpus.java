/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.EntityDetection.Internal;

import fi.johannes.Global.Options;

/**
 * EntityCorpus is a set where you check if something exists
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public interface EntityCorpus {
    
    public void buildCorpus();
    public boolean doesContain(String word);
    public double reliabilityOfContain();
    public Options.SupportedCorpuses getId();
}
