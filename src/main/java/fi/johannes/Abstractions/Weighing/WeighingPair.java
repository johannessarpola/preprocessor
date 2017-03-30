/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Abstractions.Weighing;

/**
 *
 * @author Johannes
 * @param <D> == number (float, double, int, byte)
 * @param <T> == number (float, double, int, byte)
 */
public abstract class WeighingPair<D extends Number,T extends Number> {
    private D depth; // e.g Index or, totalDepth in hierarchy
    private T totalDepth; // e.g. totalDepth how should each level of totalDepth be weighed like 1,0.5,0.25 would be 0.5
    public WeighingPair(D position, T depth){
        this.depth = position;
        this.totalDepth = depth;
    }
    /**
     * @return the depth
     */
    public D getDepth() {
        return depth;
    }

    /**
     * @return the totalDepth
     */
    public T getTotalDepth() {
        return totalDepth;
    }
    /**
     * Swap the depth and return old as number.
     * @param number
     * @return 
     */
    public Number newPosition(D number){
        Number old = fi.johannes.Utilities.Numbers.deepCopyNumber(depth);
        this.depth = number;
        return old;
    }
}
