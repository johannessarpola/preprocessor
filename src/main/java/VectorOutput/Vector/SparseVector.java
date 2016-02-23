package VectorOutput.Vector;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.math.LongMath;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Michael Cochez <miselico@jyu.fi>
 */
public class SparseVector implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int[] indexes;
    private final int[] values;
    private final double norm;

    private SparseVector(int[] indexes, int[] values, double norm) {
        this.indexes = indexes;
        this.values = values;
        this.norm = norm;
    }

    /**
     * Note: universe MUST be sorted!
     * @param tokens
     * @param universe
     * @return
     */
    public static <E extends Comparable<E>> SparseVector create(Multiset<E> tokens, ArrayList<E> universe) {
        int size = tokens.elementSet().size();
        int[] indexes = new int[size];
        int[] values = new int[size];
        int current = 0;
        ArrayList<Entry<E>> sortedEntries = new ArrayList<>(size);
        sortedEntries.addAll(tokens.entrySet());
        Collections.sort(sortedEntries, new Comparator<Entry<E>>() {

            @Override
            public int compare(Entry<E> o1, Entry<E> o2) {
                return o1.getElement().compareTo(o2.getElement());
            }
        });
        long sumACube = 0;
        for (Entry<E> term : sortedEntries) {
            int index = Collections.binarySearch(universe, term.getElement());
            int count = term.getCount();
            indexes[current] = index;
            values[current] = count;
            current++;
            sumACube += count * count;
        }
        double norm = Math.sqrt(sumACube);
        return new SparseVector(indexes, values, norm);
    }

    public long dotProduct(int[] v) {
        long dotproduct = 0;
        for (int i = 0; i < this.indexes.length; i++) {
            int index = this.indexes[i];
            long a_index = this.values[i];
            long v_index = v[index];
            dotproduct += a_index * v_index;
            assert dotproduct == LongMath.checkedAdd(dotproduct, LongMath.checkedMultiply(a_index, v_index));
        }
        return dotproduct;
    }

    public static double angle(SparseVector a, SparseVector b) {
        long sumAB = 0;
        int sparseIndexA = 0;
        int sparseIndexB = 0;
        while ((sparseIndexA < a.indexes.length) && (sparseIndexB < b.indexes.length)) {
            if (a.indexes[sparseIndexA] == b.indexes[sparseIndexB]) {
                sumAB += a.values[sparseIndexA] * b.values[sparseIndexB];
                sparseIndexA++;
                sparseIndexB++;
            } else if (a.indexes[sparseIndexA] < b.indexes[sparseIndexB]) {
                sparseIndexA++;
            } else {
                sparseIndexB++;
            }
        }

        double sqSumACube = a.norm;
        double sqSumBCube = b.norm;
        double cos = sumAB / (sqSumACube * sqSumBCube);
        if (cos == Math.nextUp(1.0)) {
            cos = 1.0;
        }
        double distance = Math.acos(cos);
        if (Double.isNaN(distance)) {
            throw new Error("Got NaN from " + (sumAB / (sqSumACube * sqSumBCube)));
        }
        return distance;
    }
}
