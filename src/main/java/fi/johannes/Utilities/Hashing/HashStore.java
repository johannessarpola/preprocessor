/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Hashing;

import fi.johannes.Utilities.GeneralUtilities;
import fi.johannes.Utilities.Logging.Logging;
import fi.johannes.Utilities.S;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class HashStore {

    /**
     * Stores index of a list of strings based on the hash of it to a map
     */
    private final int digestLength;
    private Map<String, Integer> keystore;
    
    public HashStore(int sizetoDigest) {
        this.digestLength = sizetoDigest;
        keystore = new ConcurrentHashMap<>();
    }
    public void storeKey(String line, int index){
        storeKey(GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line), index);
    }

    public void storeKey(List<String> stringList, int index){
        storeKey(stringList,index,digestLength);
    }

    public void storeKey(List<String> stringList, int index, int digestLength) {
        int dgstsize = this.digestLength < stringList.size() ? this.digestLength : stringList.size() -1;
        String key = HashMethods.createHashKey(stringList, dgstsize);
        if(keystore.containsKey(key)){
            // no fancy stuff just try to recalc digest with whole list
            if(dgstsize < stringList.size() -1){
                storeKey(stringList,index,Integer.MAX_VALUE);
            }
            else {
                Logging.logMessageError(this.getClass(), S.fmt("Hash collision with key: %s", key));
            }
        }
        else {
            keystore.put(key, index); // These need to be unique
        }
    }
    public Integer getIndex(String line){
        String accessKey = HashMethods.createHashKey(line, digestLength);
        Integer ret = this.keystore.get(accessKey);
        return ret;
    }
    public int getSizeToDigest() {
        return digestLength;
    }

    public Integer get(String keyForLine) {
        return keystore.get(keyForLine);
    }


}
