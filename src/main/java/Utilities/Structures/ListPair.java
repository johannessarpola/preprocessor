/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import Utilities.Logging.CustomExceptions.UnevenSizedListsException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * List wrapper which uses the finalized pair
 *
 * @author Johannes
 */
public class ListPair<K, V> implements Iterable<FinalizedPair> {
    // TODO Implement Iterator();

    List<K> keys;
    List<V> values;
    private FinalizedPair<K, V> pair;
    private boolean operational;
    
    public ListPair(){
        keys = new ArrayList<>();
        values = new ArrayList<>();
    };
    public ListPair(List<K> keys, List<V> values) throws UnevenSizedListsException {
        operational = false;
        validate(keys, values);
        this.keys = keys;
        this.values = values;
    }

    public FinalizedPair<K, V> getPair(int index) {
        pair = new FinalizedPair(keys.get(index), values.get(index));
        return pair;
    }
    private void remove(int index){
        keys.remove(index);
        values.remove(index);
    }
    
    private int size() {
        return keys.size();
    }
    private void validate(List<K> keys, List<V> values) throws UnevenSizedListsException {
        if (keys.size() != values.size()) {
            throw new UnevenSizedListsException();
        } else {
            operational = true;
        }
    }
    public boolean isOperational() {
        return operational;
    }
    @Override
    public Iterator<FinalizedPair> iterator() {
        ListPair instance = this;
        Iterator<FinalizedPair> it = new Iterator<FinalizedPair>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < instance.size() && instance.getPair(currentIndex) != null;
            }

            @Override
            public FinalizedPair next() {
                return instance.getPair(currentIndex++);
            }

            @Override
            public void remove() {
                instance.remove(currentIndex);
            }
        };
        return it;
    }
}
