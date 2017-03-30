/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Utilities.Structures;

import java.util.Objects;

/**
 * Is a structure to hold finalized item and it's frequency
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class FinalizedPair<L,R> {

    private final L item;
    private final R value;

    public L getItem() {
        return item;
    }

    public R getValue() {
        return value;
    }

    public FinalizedPair(L item, R value) {
        this.item = item;
        this.value = value;
    }

    @Override
    public String toString() {
        return item.toString() + " " + value;
    }

    @Override
    public int hashCode() {
        int hash = item.hashCode() + value.hashCode();
        return hash;
    }

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
        final FinalizedPair<L,R> other = (FinalizedPair<L,R>) obj;
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.item, other.item)) {
            return false;
        }
        return true;
    }

}
