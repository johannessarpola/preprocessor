/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Hashing;

import fi.johannes.Utilities.GeneralUtilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class HashStore {

    /**
     * Stores index of a list of strings based on the hash of it to a map
     */
    private int digestLength;
    private Map<String, Integer> keystore;
    
    public HashStore(int sizetoDigest) {
        this.digestLength = sizetoDigest;
        keystore = new HashMap<>();
    }
    public void storeKey(String line, int index){
        storeKey(GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line), index);
    }
    
    /**
     *
     * @param stringList
     * @param index
     */
    public void storeKey(List<String> stringList, int index) {
        int dgstsize = this.digestLength;
        String key = "";
        if (this.digestLength > stringList.size()) {
            dgstsize = stringList.size() - 1;
        }
        key = HashMethods.deduct128MurMurKey(stringList, dgstsize);
        keystore.put(key, index);
    }
    public Integer getIndex(String line){
        String accessKey = HashMethods.deduct128MurMurKey(line, digestLength);
        Integer ret = this.keystore.get(accessKey);
        return ret;
    }
    public int getSizeToDigest() {
        return digestLength;
    }

    public void setSizeToDigest(int sizeToDigest) {
        this.digestLength = sizeToDigest;
    }

    public Integer get(String keyForLine) {
        return keystore.get(keyForLine);
    }


}
