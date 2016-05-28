/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import Utilities.Logging.CustomExceptions.UnevenSizedListsException;
import java.util.List;

/**
 * List wrapper which uses the finalized pair
 *
 * @author Johannes
 */
public class FinalizedListPair<K, V> {
    // TODO Implement Iterator();

    final List<K> keys;
    final List<V> values;
    private FinalizedPair<K, V> pair;
    private boolean operational;

    public FinalizedListPair(List<K> keys, List<V> values) throws UnevenSizedListsException {
        operational = false;
        validate(keys, values);
        this.keys = keys;
        this.values = values;
    }

    public FinalizedPair<K, V> getPair(int index) {
        pair = new FinalizedPair(keys.get(index), values.get(index));
        return pair;
    }

    public int size() {
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
}
