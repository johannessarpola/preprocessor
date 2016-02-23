/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import java.util.List;

/**
 * Class with headers and rows
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class Table<E> {
    private List<E> header;
    private List<List<E>> rows;
    
    public Table(List<E> header, List<List<E>> rows){
        this.header = header;
        this.rows = rows;
    }

    public List<E> getHeader() {
        return header;
    }

    public List<List<E>> getRows() {
        return rows;
    }

    public void setHeader(List<E> header) {
        this.header = header;
    }

    public void setRows(List<List<E>> rows) {
        this.rows = rows;
    }
}
