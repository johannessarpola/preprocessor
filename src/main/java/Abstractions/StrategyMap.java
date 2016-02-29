/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abstractions;

import Global.Options;
import Utilities.Logging.CustomExceptions.StrategyNotSupported;

/**
 * Class which each cluster should have
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public abstract class StrategyMap <T> {
    Options.SupportedClusters id;
    protected StrategyMap(Options.SupportedClusters id){
        this.id = id;
    }
    public abstract T buildStrategy(Options.SupportedProcessingStrategy strategy) throws StrategyNotSupported;
}
