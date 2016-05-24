/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abstractions.Weighing;

/**
 *
 * @author Johannes
 * @param <I> == number
 * @param <F> == number
 */
public abstract class WeighingPair<I extends Number,F extends Number> {
    private I position; // e.g Index or, depth in hierarchy
    private F depth; // e.g. depth how should each level of depth be weighed like 1,0.5,0.25 would be 0.5
    public WeighingPair(I position, F depth){
        this.position = position;
        this.depth = depth;
    }
    /**
     * @return the position
     */
    public I getPosition() {
        return position;
    }

    /**
     * @return the depth
     */
    public F getDepth() {
        return depth;
    }
    
}
