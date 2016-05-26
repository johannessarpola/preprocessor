/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.Internal;

import Utilities.Logging.GeneralLogging;
import Utilities.Structures.FinalizedPair;
import Utilities.Structures.Table;
import java.util.List;
import java.util.Map;

/**
 * Is basically a map of weights for different words in regards to it 'position'
 * in table e.g. with columns 1st would have 1, 2nd 0.5, 3rd 0.25, 4th, 0.125
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableHierarchy<T> {

    private Map<T, Double> weightMap;
    private TableBasedWeighingLogic tbwl;

    public TableHierarchy(Table<T> t) {
        createHierarchy(t);
    }

    private void createHierarchy(Table<T> t) {
        // Both start from 1
        int weightFactor = t.getHeader().size();
        int position = 1;
        TableBasedWeighingPair twp = new TableBasedWeighingPair(0, weightFactor);
        tbwl = TableBasedWeighingLogic.Builder.build();
        for (List<T> row : t.getRows()) {
            for (T element : row) {
                twp.newPosition(position);
                initializeElementWithWeight(twp, element);
                position++;
            }
        }
    }
    private void initializeElementWithWeight(TableBasedWeighingPair twp, T element) {
        Double weight = tbwl.calculateWeight(twp);
        weightMap.put(element, weight);
    }
    public FinalizedPair getWeight(T element) {
        if (weightMap.containsKey(element)) {
            FinalizedPair<T, Double> fp = new FinalizedPair(element, weightMap.get(element));
            return fp;
        } else {
            GeneralLogging.logMessage_Error(getClass(), "No element found in weight map for " + element.toString());
            return null;
        }
    }
}
