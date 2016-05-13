/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VectorOutput.Vector;

import Utilities.Structures.Finalized_Pair;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import java.util.Iterator;
import java.util.List;

/**
 * Class to recreate a vector String:Freq from TokenVector
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class RecreatedTokenVector<E>{
    
    private final long serial;
    private Multiset<E> recreatedVector;
    // TODO Serial
    // TODO Multiset of Word:Count
    public RecreatedTokenVector(TokenVector vector, List<E> universe){
        this.serial = vector.getSerial();
        create(vector, universe);
    }
    private void create(TokenVector vector, List<E> universe){
        Iterator<Finalized_Pair<Integer, Integer>> iter = vector.iterator();
        recreatedVector = HashMultiset.create();
        while(iter.hasNext()){
            Finalized_Pair<Integer, Integer> fp = iter.next();
            int index = fp.getItem();
            int count = fp.getValue();
            E word = universe.get(index);
            recreatedVector.add(word, count);
        }
    }
    public long getSerial() {
        return serial;
    }
    public Multiset<E> getVector() {
        return recreatedVector;
    }
    
}
