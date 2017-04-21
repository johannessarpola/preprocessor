/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection;

import fi.johannes.Clusters.EntityDetection.Internal.EntityCorpus;
import fi.johannes.Clusters.EntityDetection.WikiTitleCorpus.WikiCorpus;
import fi.johannes.Core.AppConf.SupportedCorpuses;
import fi.johannes.Utilities.Logging.CustomExceptions.CorpusNotAvailableException;
import fi.johannes.Utilities.Logging.GenLogging;

import java.util.*;

/**
 * Class to hold different bloomfilters to weight against
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class EntityCorpuses {

    public static final Map<SupportedCorpuses, EntityCorpus> CORPUSES;
    List<EntityCorpus> corpuses;

    static {

        Map<SupportedCorpuses, EntityCorpus> tMap = new HashMap<>();
        tMap.put(SupportedCorpuses.WikipediaCorpus, new WikiCorpus("wiki"));
        CORPUSES = Collections.unmodifiableMap(tMap);
    }

    public static EntityCorpus getService(SupportedCorpuses s) throws CorpusNotAvailableException {
        if (CORPUSES.containsKey(s)) {
            return CORPUSES.get(s);
        } else {
            throw new CorpusNotAvailableException();
        }
    }

    public EntityCorpuses(SupportedCorpuses... corpuses){
        this.corpuses = new ArrayList<>();
        for(SupportedCorpuses c : corpuses) {
            try {
                this.corpuses.add(getService(c));
            } catch (CorpusNotAvailableException ex) {
                GenLogging.logStackTrace_Error(this.getClass(), ex);
            }
        }
    }
    
    public List<SupportedCorpuses> doesCorpusesContain(String str){
        List<SupportedCorpuses> containedIn = new ArrayList<>();
        for(EntityCorpus c : corpuses){
            if(c.doesContain(str)) {
                containedIn.add(c.getId());
            }
        }
        return containedIn;
    }
    
}
