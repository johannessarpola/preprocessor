/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Corpuses;

import Corpuses.Internal.Corpus;
import Corpuses.WikipediaTitles.WikiCorpus;
import Global.Options;
import Utilities.Logging.CustomExceptions.CorpusNotAvailableException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Hold the mapping to Corpuses
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class CorpusMap {

    public static final Map<Options.SupportedCorpuses, Corpus> CORPUSES;
    static {

        Map<Options.SupportedCorpuses, Corpus> tMap = new HashMap<>();
        tMap.put(Options.SupportedCorpuses.Wikipedia, new WikiCorpus());
        CORPUSES = Collections.unmodifiableMap(tMap);
    }

    public static Corpus getService(Options.SupportedCorpuses s) throws CorpusNotAvailableException {
        if (CORPUSES.containsKey(s)) {
            return CORPUSES.get(s);
        } else {
            throw new CorpusNotAvailableException();
        }
    }
}
