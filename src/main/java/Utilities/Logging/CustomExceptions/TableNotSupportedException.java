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
public class TableNotSupportedException  extends Exception {
    public TableNotSupportedException(){
        super("Table filetype is not supported");
    }
}
