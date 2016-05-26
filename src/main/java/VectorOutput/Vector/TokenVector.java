package VectorOutput.Vector;

import Utilities.String.CStringOperations;
import Utilities.Structures.FinalizedPair;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Vector to create from Multiset<Integer>
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class TokenVector implements Iterable<FinalizedPair<Integer, Integer>> {

    private final int[] indexes;
    private final int[] counts;

    // is the string representation of given vector, used in io
    private String vectorString;
    private int highestIndex;
    private final long serial;

    // TODO Separate these to a delimeted data body structure
    // is the separateor for fields, e.g serial,vector
    final char fieldSeparator = ',';
    // Is the way to separate vectors e.g 1:3 2:4
    final char vectorItemSepartor = ' ';

    /**
     * A POJO for int vector from Multiset, AtomicLong is a singular long for id
     *
     * @param ms
     */
    public TokenVector(Multiset<Integer> ms, AtomicLong al) {
        indexes = new int[ms.elementSet().size()];
        counts = new int[ms.elementSet().size()];
        serial = al.getAndIncrement();
        vectorString = "" + serial + fieldSeparator;
        create(ms);
    }

    /**
     * Creates int vector
     *
     * @param ms
     */
    private void create(Multiset<Integer> ms) {
        int iter = 0;
        highestIndex = 0;
        for (Entry<Integer> e : ms.entrySet()) {
            int val = e.getElement();
            int count = e.getCount();
            indexes[iter] = val;
            counts[iter] = count;
            vectorString += val + ":" + count + vectorItemSepartor;
            if (val > highestIndex) {
                highestIndex = val;
            }
            iter++;
        }
        // Removes the last ','
        vectorString = CStringOperations.rmCharAt(vectorString.length() - 1, vectorString);
    }

    public int[] getIndexes() {
        return indexes;
    }

    public int[] getCounts() {
        return counts;
    }

    /**
     * Returns the string representation of index(int):count(int)|next..
     *
     * @return
     */
    @Override
    public String toString() {
        return vectorString;
    }

    public long getSerial() {
        return serial;
    }

    /**
     * Gets the iterator
     * @return
     */
    @Override
    public Iterator<FinalizedPair<Integer, Integer>> iterator() {
        return new TokenVectorIterator(0, this);
    }

    /**
     * Iterator for Token vector, goes through indexes and counts
     */
    private static final class TokenVectorIterator implements Iterator<FinalizedPair<Integer, Integer>> {

        int cursor;
        final int end;
        final TokenVector vector;

        private TokenVectorIterator(int start, TokenVector vector) {
            this.vector = vector;
            cursor = start;
            end = vector.getIndexes().length;
        }

        @Override
        public boolean hasNext() {
            return cursor < end;
        }

        @Override
        public FinalizedPair<Integer, Integer> next() {
            if (hasNext()) {
                Integer index = vector.indexes[cursor];
                Integer count = vector.counts[cursor];
                cursor++;
                return new FinalizedPair<>(index, count);
            } else {
                throw new NoSuchElementException();
            }
        }

    }
}
