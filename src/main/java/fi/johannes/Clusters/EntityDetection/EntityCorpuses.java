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

    private List<EntityCorpus> corpuses;

    private static final Map<SupportedCorpuses, EntityCorpus> CORPUSES;
    // todo Could be somewhere better
    static {
        Map<SupportedCorpuses, EntityCorpus> tMap = new HashMap<>();
        tMap.put(SupportedCorpuses.WikipediaCorpus, new WikiCorpus("wiki"));
        CORPUSES = Collections.unmodifiableMap(tMap);
    }

    public static EntityCorpus getCorpus(SupportedCorpuses s) throws CorpusNotAvailableException {
        if (CORPUSES.containsKey(s)) {
            return CORPUSES.get(s);
        } else {
            throw new CorpusNotAvailableException();
        }
    }

    public EntityCorpuses(List<SupportedCorpuses> corpuses){
        this.corpuses = new ArrayList<>();
        for(SupportedCorpuses c : corpuses) {
            try {
                this.corpuses.add(getCorpus(c));
            } catch (CorpusNotAvailableException ex) {
                GenLogging.logStackTrace_Error(this.getClass(), ex);
            }
        }
    }

    /**
     * Returns list of corpuses in which the word is contained. Could use some kind of
     * measure on how sure can you be that it's an entity if it's contained in multiple corpuses.
     * @param str
     * @return
     */
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
