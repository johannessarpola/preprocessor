/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clusters.SupervisedBiasing.Internal;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WeighingLogic {
    
    /**
     * Manages the weighting logic for tablebiasing
     * @param position column position of a word
     * @param weighingFactor usually width of table
     * @return 
     */
    public static Double calculateWeight(int position, int weighingFactor){
        Double d = position/weighingFactor*1.;
        return d;
    }
}
