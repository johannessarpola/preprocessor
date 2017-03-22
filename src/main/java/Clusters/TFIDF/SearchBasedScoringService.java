package Clusters.TFIDF;

import Clusters.TFIDF.Internal.Converter;
import Clusters.TFIDF.Internal.Result;
import Clusters.TFIDF.Internal.SearchEngine;
import Utilities.Structures.LinkedWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Performs the search with a keyword and returns on how many "hits" or "pages" it returns
 * Created by Johannes on 22.3.2017.
 */
@Component
public class SearchBasedScoringService {

    private final SearchEngine searcEngine;

    @Autowired
    public SearchBasedScoringService(SearchEngine searchService) {
        this.searcEngine = searchService;
    }

    /**
     * Performs the query with LinkedWord
     * @param lw
     * @return
     */
    public Result query(LinkedWord lw){
        String queryString = Converter.linkedWordToString(lw);
        Result result = searcEngine.query(queryString);
        return result;
    }

}
