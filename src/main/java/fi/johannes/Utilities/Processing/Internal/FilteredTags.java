package fi.johannes.Utilities.Processing.Internal;

import com.google.common.collect.ImmutableList;

/**
 * Created by Johannes on 30.3.2017.
 */
public enum FilteredTags {

    Adjective("Adjective", "J"),
    Noun("Noun", "N"),
    Verb("Verb", "V");

    public final String key;
    public final String id;

    FilteredTags(String key, String id) {
        this.key = key;
        this.id = id;
    }

    public static ImmutableList<FilteredTags> defaultFiltered() {
        return ImmutableList.<FilteredTags>builder().add(Adjective).add(Noun).add(Verb).build();
    }
}