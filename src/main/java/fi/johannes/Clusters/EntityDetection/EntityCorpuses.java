/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.EntityDetection;

import fi.johannes.Clusters.EntityDetection.Internal.EntityCorpus;
import fi.johannes.Clusters.EntityDetection.WikipediaTitles.WikiCorpus;
import fi.johannes.Core.Options;
import fi.johannes.Utilities.Logging.CustomExceptions.CorpusNotAvailableException;
import fi.johannes.Utilities.Logging.GeneralLogging;

import java.util.*;

/**
 * Class to hold different bloomfilters to weight against
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class EntityCorpuses {
    // TODO Maybe implement the variations with Damerau-Levenhstein(?)
    // TODO User's table of 'higher' ranked words (it's not really a corpus but hierarchial structure)

    public static final Map<Options.SupportedCorpuses, EntityCorpus> CORPUSES;
    List<EntityCorpus> corpuses;

    static {

        Map<Options.SupportedCorpuses, EntityCorpus> tMap = new HashMap<>();
        tMap.put(Options.SupportedCorpuses.Wikipedia, new WikiCorpus());
        CORPUSES = Collections.unmodifiableMap(tMap);
    }

    public static EntityCorpus getService(Options.SupportedCorpuses s) throws CorpusNotAvailableException {
        if (CORPUSES.containsKey(s)) {
            return CORPUSES.get(s);
        } else {
            throw new CorpusNotAvailableException();
        }
    }

    public EntityCorpuses(Options.SupportedCorpuses... corpuses){
        this.corpuses = new ArrayList<>();
        for(Options.SupportedCorpuses c : corpuses) {
            try {
                this.corpuses.add(getService(c));
            } catch (CorpusNotAvailableException ex) {
                GeneralLogging.logStackTrace_Error(this.getClass(), ex);
            }
        }
    }
    
    public List<Options.SupportedCorpuses> doesCorpusesContain(String word){
        List<Options.SupportedCorpuses> l = new ArrayList<>();
        for(EntityCorpus c : corpuses){
            if(c.doesContain(word)) {
                l.add(c.getId());
            }
        }
        return l;
    }
    
}
