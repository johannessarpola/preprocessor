/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.Strategies;

import static Abstractions.Weighing.WeighingLogic.CombineStrategies.*;
import Clusters.SupervisedBiasing.Internal.StringTableHierarchy;
import Clusters.SupervisedBiasing.Internal.DoubleBasedWeighingLogic;
import Utilities.Logging.CustomExceptions.UnevenSizedListsException;
import Utilities.Structures.FinalizedListPair;
import Utilities.Structures.FinalizedPair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Basically checks each token and it's position in tabular data Hierarchy uses XLSX
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TableBiasingServiceMethods  {

    /**
     * Gets the weights for splitted line using tablehierarchies
     * @param splitLine
     * @param ths
     * @return the weights in the same order as the split line
     */
    protected static List<Double> getWeightsForLine(List<String> splitLine, List<StringTableHierarchy> ths) {
        List<Double> weights = new ArrayList<>(splitLine.size());
        DoubleBasedWeighingLogic logic = DoubleBasedWeighingLogic.Builder.build();
        int i =0;
        for(String word : splitLine){
            List<Double> weightsForWord = new ArrayList<>();
            for(StringTableHierarchy h: ths){
                weightsForWord.add(h.getWeight(word));
            }
            Double combined = logic.combineWeights(mean, weightsForWord);
            weights.add(combined);
        }
        return weights;
    }
    
    protected static FinalizedListPair<String,Double> makeListPair(List<String> ws, List<Double> dbles) throws UnevenSizedListsException{
        return new FinalizedListPair<>(ws,dbles);
    } 
    protected static List<String> getHighestWords(FinalizedListPair<String,Double> lp, int biasingsize) {
        Iterator<FinalizedPair> iterator = lp.iterator();
        while(iterator.hasNext()){
            
        }
        
        return null;
       // Now we need the iterator interface
    }
}
