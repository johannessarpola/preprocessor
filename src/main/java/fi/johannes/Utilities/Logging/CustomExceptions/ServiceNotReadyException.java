/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Logging.CustomExceptions;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class ServiceNotReadyException extends Exception {
    public ServiceNotReadyException(){
        super("Service not ready yet, please add vocabulary");
    }
}
