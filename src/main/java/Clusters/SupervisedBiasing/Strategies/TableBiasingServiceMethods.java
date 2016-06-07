/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.Strategies;

import static Abstractions.Weighing.WeighingLogic.CombineStrategies.*;
import Clusters.SupervisedBiasing.Internal.StringTableHierarchy;
import Clusters.SupervisedBiasing.Internal.DoubleBasedWeighingLogic;
import static Clusters.SupervisedBiasing.Strategies.TableBiasingServiceOptions.DELIM;
import Utilities.Logging.CustomExceptions.UnevenSizedListsException;
import Utilities.Structures.FinalizedPair;
import Utilities.Structures.SortedListPair;
import java.util.ArrayList;
import java.util.List;

/**
 * Basically checks each token and it's position in tabular data Hierarchy uses XLSX
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 * @version %I%
 */
public class TableBiasingServiceMethods  {

    /**
     * Gets the weights for splitted line using tablehierarchies
     * @param splitLine
     * @param ths
     * @return the weights in the same order as the split line
     */
    // TODO Test tablehierarchies
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
    /**
     * Creates list pair which is sorted with the Double value
     * @param ws words 
     * @param dbles doubles, in this case the weights
     * @return SortedListPair, which can be acceses through FinalizedPair
     * @see SortedListPair
     * @see FinalizedPair
     * @throws UnevenSizedListsException 
     */
    protected static SortedListPair<String,Double> makeListPair(List<String> ws, List<Double> dbles) throws UnevenSizedListsException{
        return new SortedListPair<>(ws,dbles);
    } 
    /**
     * Gets the highestWords from list of words according to set size
     * @param sortedListPair SortedListPair to al
     * @param sampleSize
     * @return 
     */
    private static List<FinalizedPair<String,Double>> getSublist(SortedListPair<String,Double> sortedListPair, int sampleSize) {
        return sortedListPair.createlist(0, sampleSize);
    }
    protected static List<FinalizedPair<String,Double>> getHighestWords(List<String> words, List<Double> weights, int sampleSize) throws UnevenSizedListsException{
        SortedListPair<String,Double> slp = makeListPair(words, weights);
        List<FinalizedPair<String,Double>> l = getSublist(slp, sampleSize);
        return l;
    }
    /**
     * Flattens the pair list to a string
     * @param highest
     * @return 
     */
    protected static String flatten(List<FinalizedPair<String, Double>> highest) {
        StringBuilder ret = new StringBuilder("");
        highest.stream().forEach(element -> ret.append(multiplyToString(element)));
        return ret.toString();
    }
    private static String multiplyToString(FinalizedPair<String, Double> fp) {
        Double val = fp.getValue();
        StringBuilder ret = new StringBuilder("");
        while(val-- > 0.){
            String elem = fp.getItem()+DELIM;
            ret.append(elem);
        }
        return ret.toString().trim();
    }
    
}
