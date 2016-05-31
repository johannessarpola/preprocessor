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

/**
 * List wrapper which uses the finalized pair
 *
 * @author Johannes
 * @param <L> 
 * @param <R>
 */
public class SortedListPair<L, R extends Number & Comparable<? super R>> extends ListPair {

    /**
     *
     * @param keys
     * @param values
     * @throws UnevenSizedListsException
     */
    public SortedListPair(List<L> keys, List<R> values) throws UnevenSizedListsException {
        super(keys, values);
        selfsort(keys, values);
    }

    /**
     * This wont be very effective, O^2 jezus...
     */
    private void selfsort(List<L> keys, List<R> values) {
        Iterator<FinalizedPair<L, R>> iter = this.iterator();
        Map<Integer, FinalizedPair<L, R>> indexmap = new HashMap<>();
        Map<R, PriorityQueue<Integer>> indexQueue = new HashMap<>();
        List<R> vals = new ArrayList<>();
        Integer i = 0;
        // go through each element in the listpair
        while (iter.hasNext()) {
            FinalizedPair<L,R> fp = iter.next();
            // map indexes to finalized pairs
            indexmap.put(i, fp);
            R numb = fp.getValue();
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
        List<L> endKeys = new ArrayList<>();
        List<R> endVals = new ArrayList<>();
        // sort the values
        Collections.sort(vals);
        Collections.reverse(vals);
        // go through the queues for epach double 
        for (R val : vals) {
            PriorityQueue<Integer> q = indexQueue.get(val);
            while (q.peek() != null) {
                Integer indx = q.poll();
                FinalizedPair<L, R> fp = indexmap.get(indx);
                endKeys.add(fp.getItem());
                endVals.add(fp.getValue());
            }
        }
        // set the parent classes fields
        this.keys = endKeys;
        this.values = endVals;

    }

}
