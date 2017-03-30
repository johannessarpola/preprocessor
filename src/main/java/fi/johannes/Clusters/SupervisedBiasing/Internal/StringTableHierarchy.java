/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.SupervisedBiasing.Internal;

import fi.johannes.Utilities.Structures.Table;

/**
 * Is basically a map of weights for different words in regards to it 'position'
 * in table e.g. with columns 1st would have 1, 2nd 0.5, 3rd 0.25, 4th, 0.125
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StringTableHierarchy extends TableHierarchy<String> {
    public StringTableHierarchy(Table<String> t) {
        super(t);
    }

}
