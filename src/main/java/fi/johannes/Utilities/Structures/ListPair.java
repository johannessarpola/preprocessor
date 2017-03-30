/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import fi.johannes.Utilities.Logging.CustomExceptions.UnevenSizedListsException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * List wrapper which uses the finalized pair
 *
 * @author Johannes
 */
public class ListPair<L, R> implements Iterable<FinalizedPair<L,R>> {
    // TODO Implement Iterator();

    List<L> keys;
    List<R> values;
    private FinalizedPair<L, R> pair;
    private boolean operational;

    public ListPair() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    ;
    public ListPair(List<L> keys, List<R> values) throws UnevenSizedListsException {
        operational = false;
        validate(keys, values);
        this.keys = keys;
        this.values = values;
    }

    public FinalizedPair<L, R> getPair(int index) {
        pair = new FinalizedPair(keys.get(index), values.get(index));
        return pair;
    }
    /**
     * Creates a partition of both lists wiht begin and end index
     * @param begin index to begin
     * @param end index to end
     * @return partition of both lists as a List of FinalizedPairs
     * @see FinalizedPair
     */
    public List<FinalizedPair<L, R>> createlist(int begin, int end) {
        List<L> a = keys.subList(begin, end);
        List<R> b = values.subList(begin, end);
        List<FinalizedPair<L,R>> pairs = new ArrayList();
        for (int i = 0; i < a.size(); i++) {
            pair = new FinalizedPair<>(a.get(i), b.get(i));
            pairs.add(pair);
        }
        return pairs;
    }

    private void remove(int index) {
        keys.remove(index);
        values.remove(index);
    }

    public int size() {
        return keys.size();
    }

    private void validate(List<L> keys, List<R> values) throws UnevenSizedListsException {
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
    public Iterator<FinalizedPair<L,R>> iterator() {
        ListPair instance = this;
        Iterator<FinalizedPair<L,R>> it = new Iterator<FinalizedPair<L,R>>() {
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
