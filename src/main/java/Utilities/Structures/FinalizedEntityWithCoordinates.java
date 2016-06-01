/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import java.util.Objects;

/**
 * A basic entity with x and y values (e.g row, column and value)
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class FinalizedEntityWithCoordinates<E>{
    final int x, y;
    final E entity;

    public FinalizedEntityWithCoordinates(int x, int y, E entity) {
        this.x = x;
        this.y = y;
        this.entity = entity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public E getEntity() {
        return entity;
    }
    /**
     * Uses x,y and entity for hash
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.x;
        hash = 71 * hash + this.y;
        hash = 71 * hash + Objects.hashCode(this.entity);
        return hash;
    }
    
    /**
     * Compares entity to another obj
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FinalizedEntityWithCoordinates<?> other = (FinalizedEntityWithCoordinates<?>) obj;
        return Objects.equals(this.entity, other.entity);
    }
    
    
}
