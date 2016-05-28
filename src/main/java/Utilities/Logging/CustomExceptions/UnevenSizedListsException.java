/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Logging.CustomExceptions;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class UnevenSizedListsException extends Exception {
    public UnevenSizedListsException(){
        super("Sizes are unevenly sized, either one is bigger or smaller than the other one");
    }
}
