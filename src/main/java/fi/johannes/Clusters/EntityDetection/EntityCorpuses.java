/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection;

import fi.johannes.Clusters.EntityDetection.Internal.EntityCorpus;
import fi.johannes.Clusters.EntityDetection.WikiTitleCorpus.WikiCorpus;
import fi.johannes.Core.App;
import fi.johannes.Utilities.Logging.CustomExceptions.CorpusNotAvailableException;
import fi.johannes.Utilities.Logging.GenLogging;

import java.util.*;

/**
 * Class to hold different bloomfilters to weight against
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class EntityCorpuses {

    public static final Map<App.SupportedCorpuses, EntityCorpus> CORPUSES;
    List<EntityCorpus> corpuses;

    static {

        Map<App.SupportedCorpuses, EntityCorpus> tMap = new HashMap<>();
        tMap.put(App.SupportedCorpuses.WikipediaCorpus, new WikiCorpus("wiki"));
        CORPUSES = Collections.unmodifiableMap(tMap);
    }

    public static EntityCorpus getService(App.SupportedCorpuses s) throws CorpusNotAvailableException {
        if (CORPUSES.containsKey(s)) {
            return CORPUSES.get(s);
        } else {
            throw new CorpusNotAvailableException();
        }
    }

    public EntityCorpuses(App.SupportedCorpuses... corpuses){
        this.corpuses = new ArrayList<>();
        for(App.SupportedCorpuses c : corpuses) {
            try {
                this.corpuses.add(getService(c));
            } catch (CorpusNotAvailableException ex) {
                GenLogging.logStackTrace_Error(this.getClass(), ex);
            }
        }
    }
    
    public List<App.SupportedCorpuses> doesCorpusesContain(String str){
        List<App.SupportedCorpuses> containedIn = new ArrayList<>();
        for(EntityCorpus c : corpuses){
            if(c.doesContain(str)) {
                containedIn.add(c.getId());
            }
        }
        return containedIn;
    }
    
}
