package fi.johannes.VectorOutput;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import fi.johannes.Utilities.GeneralUtilities;

import java.util.List;

/**
 * Created by Johannes on 1.6.2017.
 */
public class OutputUtils {

    public static Multiset<String> toMultiset(String line){
        List<String> strings = GeneralUtilities.guavaSplitterWhiteSpace.splitToList(line);
        Multiset<String> ms = ImmutableMultiset.<String>builder().addAll(strings).build();
        return ms;
    }
}
