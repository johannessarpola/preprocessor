/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection;

import fi.johannes.Clusters.EntityDetection.Internal.EntityCorpus;
import fi.johannes.Clusters.EntityDetection.WikipediaTitles.WikiCorpus;
import fi.johannes.Core.App;
import fi.johannes.Utilities.Logging.CustomExceptions.CorpusNotAvailableException;
import fi.johannes.Utilities.Logging.GenLogging;

import java.util.*;

/**
 * Class to hold different bloomfilters to weight against
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class EntityCorpuses {
    // TODO Maybe implement the variations with Damerau-Levenhstein(?)
    // TODO User's table of 'higher' ranked words (it's not really a corpus but hierarchial structure)

    public static final Map<App.SupportedCorpuses, EntityCorpus> CORPUSES;
    List<EntityCorpus> corpuses;

    static {

        Map<App.SupportedCorpuses, EntityCorpus> tMap = new HashMap<>();
        tMap.put(App.SupportedCorpuses.WikipediaCorpus, new WikiCorpus());
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
    
    public List<App.SupportedCorpuses> doesCorpusesContain(String word){
        List<App.SupportedCorpuses> l = new ArrayList<>();
        for(EntityCorpus c : corpuses){
            if(c.doesContain(word)) {
                l.add(c.getId());
            }
        }
        return l;
    }
    
}
