package fi.johannes.Utilities.Processing;

import com.google.common.collect.ImmutableList;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import fi.johannes.Utilities.Processing.Internal.FilteredTags;
import fi.johannes.Utilities.Processing.Internal.TagFilter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class PartOfSpeechTagging {

    private ImmutableList<FilteredTags> tags;
    private StanfordCoreNLP pipe;

    public PartOfSpeechTagging() {

        init();

    }

    public List<FilteredTags> getTags() {
        return tags;
    }

    public void setTags(ImmutableList<FilteredTags> tags) {
        this.tags = tags;
    }

    private void init() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, parse");
        pipe = new StanfordCoreNLP(props);
        tags = FilteredTags.defaultFiltered();
    }

    public List<CoreMap> annotate(String str) {
        Annotation ann = new Annotation(str);
        pipe.annotate(ann);
        List<CoreMap> cm = ann.get(SentencesAnnotation.class);
        return cm;
    }

    public Map<String, String> getPos(String str) {
        return getPos(annotate(str));
    }

    private static FilteredTags resolveTag(String str){
        String first = str.substring(0, 1);
        return Arrays.stream(FilteredTags.values())
                .reduce((a,b) -> { throw new IllegalStateException("Multiple elements: " + a + ", " + b); })
                .get();
    }

    /**
     * Gets by default noun, verbs and adjectives and can be configured through setTags
     * @param str
     * @return
     */
    public Map<String, String> getSimplifiedPos(String str) {
        Map<String, String> tm = getPos(annotate(str));
        TagFilter tf = new TagFilter(tags);
        return tm.entrySet()
                .stream()
                .filter(e -> tf.test(e.getKey()))
                .map(e -> new ImmutablePair<String, String>(e.getKey(), resolveTag(e.getValue()).key))
                .collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight));
    }


    /**
     * Gets all the POS tags, reference: http://stackoverflow.com/questions/1833252/java-stanford-nlp-part-of-speech-labels
     *
     * @param coreMaps
     * @return
     */
    public Map<String, String> getPos(List<CoreMap> coreMaps) {
        return coreMaps.stream().flatMap(sentence -> sentence.get(TokensAnnotation.class).stream())
                .map((CoreLabel coreLabel) -> {
                    String word = coreLabel.get(TextAnnotation.class);
                    String posTag = coreLabel.get(PartOfSpeechAnnotation.class);
                    return new ImmutablePair<>(word, posTag);
                })
                .collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight));
    }



}
