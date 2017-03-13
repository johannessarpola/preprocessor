/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Corpuses;

import Corpuses.Internal.Corpus;
import Global.Options;
import Utilities.Logging.CustomExceptions.CorpusNotAvailableException;
import Utilities.Logging.GeneralLogging;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to hold different bloomfilters to weight against
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class Corpuses {
    // TODO User's table of 'higher' ranked words (it's not really a corpus but hierarchial structure)
    // TODO Maybe implement the variations with Damerau-Levenhstein(?)
    
    List<Corpus> corpuses; 
    
    public Corpuses(Options.SupportedCorpuses... corpuses){
        this.corpuses = new ArrayList<>();
        for(Options.SupportedCorpuses c : corpuses) {
            try {
                this.corpuses.add(CorpusMap.getService(c));
            } catch (CorpusNotAvailableException ex) {
                GeneralLogging.logStackTrace_Error(this.getClass(), ex);
            }
        }
    }
    
    public List<Options.SupportedCorpuses> doesCorpusesContain(String word){
        List<Options.SupportedCorpuses> l = new ArrayList<>();
        for(Corpus c : corpuses){
            if(c.doesContain(word)) {
                l.add(c.getId());
            }
        }
        return l;
    }
    
}
