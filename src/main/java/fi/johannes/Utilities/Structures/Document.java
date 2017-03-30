package fi.johannes.Utilities.Structures;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Johannes on 22.3.2017.
 */
@Data
@Builder
public class Document {

    Meta meta;
    String content;
    FrequencyModel model;

    @Override
    public boolean equals(Object o) {
        // We can ignore meta for now
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        return content.equals(document.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
