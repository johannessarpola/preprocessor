/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.TableWrappers;

import Clusters.SupervisedBiasing.Internal.StringTableHierarchy;


/**
 * Wraps the TableReader here
 * Specialized for Strings
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class StringTableContainerWrapper extends TableContainerWrapper<String>{

    public StringTableContainerWrapper(String filepath) {
        super(filepath);
    }
    public StringTableHierarchy createTableHierarchy(){
        return new StringTableHierarchy(tableContainer.getTable());
    }
}
