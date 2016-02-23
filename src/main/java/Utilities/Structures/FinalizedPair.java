/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Structures;

import java.util.Objects;

/**
 * Is a structure to hold finalized item and it's frequency
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class FinalizedPair<K,V> {

    private final K item;
    private final V value;

    public K getItem() {
        return item;
    }

    public V getValue() {
        return value;
    }

    public FinalizedPair(K item, V value) {
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
        final FinalizedPair<K,V> other = (FinalizedPair<K,V>) obj;
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.item, other.item)) {
            return false;
        }
        return true;
    }
}
