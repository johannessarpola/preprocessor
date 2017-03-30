package fi.johannes.Utilities.Processing.Internal;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Johannes on 30.3.2017.
 */
public class TagFilter implements Predicate<String> {
    List<FilteredTags> tags;

    public TagFilter(List<FilteredTags> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(String s) {
        return tags.stream().anyMatch(tag -> s.startsWith(tag.id));
    }
}
