/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Logging.CustomExceptions;

/**
 * 
 * @author Johannes
 */
public class UnhandledServiceException extends Exception {

    /**
     * Constructs an instance of <code>UnhandledServiceException</code> with the
     * specified detail message.
     *
     */
    public UnhandledServiceException() {
        super("Unhandled service exception happened");
    }
}
