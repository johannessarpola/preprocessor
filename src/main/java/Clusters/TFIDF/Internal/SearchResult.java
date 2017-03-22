package Clusters.TFIDF.Internal;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Johannes on 22.3.2017.
 */
@Data
@Builder
class SearchResult implements Result {

    int hits;
    int documentCount;

    @Override
    public int getFreuency() {
        return hits;
    }
}
