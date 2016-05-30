/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import Utilities.Logging.CustomExceptions.UnevenSizedListsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * List wrapper which uses the finalized pair
 *
 * @author Johannes
 */
public class SortedListPair<K, N extends Number & Comparable<? super N>> extends ListPair {

    /**
     *
     * @param keys
     * @param values
     * @throws UnevenSizedListsException
     */
    public SortedListPair(List<K> keys, List<N> values) throws UnevenSizedListsException {
        super(keys, values);
        selfsort(keys, values);
    }

    /**
     * This wont be very effective, O^2 jezus...
     */
    // TODO Divide this up
    private void selfsort(List<K> keys, List<N> values) {
        Iterator<FinalizedPair<K, N>> iter = this.iterator();
        Map<Integer, FinalizedPair<K, N>> indexmap = new HashMap<>();
        Map<N, PriorityQueue<Integer>> indexQueue = new HashMap<>();
        List<N> vals = new ArrayList<>();
        Integer i = 0;
        // go through each element in the listpair
        while (iter.hasNext()) {
            FinalizedPair<K,N> fp = iter.next();
            // map indexes to finalized pairs
            indexmap.put(i, fp);
            N numb = fp.getValue();
            // Add to queue or create new if it doesnt exist
            if (!indexQueue.containsKey(numb)) {
                PriorityQueue q = new PriorityQueue();
                q.add(i);
                indexQueue.put(numb, q);
            } else {
                indexQueue.get(numb).add(i);
            }
            vals.add(numb);
            i++;
        }
        List<K> endKeys = new ArrayList<>();
        List<N> endVals = new ArrayList<>();
        // sort the values
        Collections.sort(vals);
        Collections.reverse(vals);
        for (N val : vals) {
            PriorityQueue<Integer> q = indexQueue.get(val);
            while (q.peek() != null) {
                Integer indx = q.poll();
                FinalizedPair<K, N> fp = indexmap.get(indx);
                endKeys.add(fp.getItem());
                endVals.add(fp.getValue());
            }
        }
        this.keys = endKeys;
        this.values = endVals;

    }

}
