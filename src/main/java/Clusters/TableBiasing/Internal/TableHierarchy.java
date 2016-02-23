/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.TableBiasing.Internal;

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
public class TableHierarchy<E> {

    private Map<E, Double> weightMap;

    public TableHierarchy(Table<E> t) {
        createHierarchy(t);
    }
    private void createHierarchy(Table<E> t) {
        // Both start from 1
        int weightFactor = t.getHeader().size();
        int position = 1;
        for (List<E> row : t.getRows()) {
            for (E element : row) {
                Double weight = WeighingLogic.calculateWeight(position, weightFactor);
                weightMap.put(element, weight);
                position++;
            }
        }
    }
    public FinalizedPair getWeight(E element) {
        if (weightMap.containsKey(element)) {
            FinalizedPair<E, Double> fp = new FinalizedPair(element, weightMap.get(element));
            return fp;
        } else {
            GeneralLogging.logMessage_Error(getClass(), "No element found in weight map for "+element.toString());
            return null;
        }
    }
}
