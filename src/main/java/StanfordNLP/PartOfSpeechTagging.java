/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StanfordNLP;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class PartOfSpeechTagging {

    StanfordCoreNLP pipe;

    public PartOfSpeechTagging() {

        init();

    }

    private void init() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, parse");
        pipe = new StanfordCoreNLP(props);
    }
    
    public List<CoreMap> annotate(String str) {
        Annotation ann = new Annotation(str);
        pipe.annotate(ann);
        List<CoreMap> cm =ann.get(SentencesAnnotation.class); 
        return cm;
    }
    
    public Map<String, String> getPos(String str){
     return getPos(annotate(str));   
    }
    /**
     * Gets noun, verbs and adjectives and doesn't take in account others
     * @param str
     * @return 
     */
    public Map<String,String> getSimplifiedPos(String str){
        Map<String, String> tm = getPos(annotate(str));
        Map<String, String> simpleMap = new HashMap<>();
        tm.entrySet().stream().forEach((Map.Entry<String, String> e) -> {
            if(e.getValue().startsWith("J")){
                simpleMap.put(e.getKey(), "Adjective");
            }
            else if(e.getValue().startsWith("N")){
                simpleMap.put(e.getKey(), "Noun");
            }
            else if(e.getValue().startsWith("V")){
                simpleMap.put(e.getKey(), "Verb");
            }
        });
        return simpleMap;
    }
    /**
     * Gets all the POS tags, reference: http://stackoverflow.com/questions/1833252/java-stanford-nlp-part-of-speech-labels
     * @param cm
     * @return 
     */
    public Map<String,String> getPos(List<CoreMap> cm) {
        HashMap<String, String> pos = new HashMap<>();
        cm.stream().forEach((CoreMap sentence) -> {
            // a CoreLabel is a CoreMap with additional token-specific methods
            sentence.get(TokensAnnotation.class).stream().forEach((token) -> {
                String word = token.get(TextAnnotation.class);
                String posTag = token.get(PartOfSpeechAnnotation.class);
                pos.put(word, posTag);
            });
        });
        return pos;
    }

}
