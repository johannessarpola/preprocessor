/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection.Internal;

import fi.johannes.Core.App;
import fi.johannes.Core.AppConf.SupportedCorpuses;

/**
 * EntityCorpus is a set where you check if something exists
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public interface EntityCorpus {
    
    public void buildCorpus();
    public boolean doesContain(String word);
    public double reliabilityOfContain();
    public SupportedCorpuses getId();
}
