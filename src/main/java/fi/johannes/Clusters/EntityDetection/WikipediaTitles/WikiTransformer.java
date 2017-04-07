
package fi.johannes.Clusters.EntityDetection.WikipediaTitles;

import org.apache.commons.lang.StringUtils;

import java.util.function.Function;

/**
 * Translates strings to wiki form and vice versa
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikiTransformer {


    /**
     * _ to space (to more detail)
     * Converts _ to ' ' and makes the string of Abc_abc_(disamb)_abc to  -> abc abc abc
     * @param title
     * @return
     */
    public static String transformWikiTitle(String title) {
        if (title.length() == 1) {
            return null;
        }
        String mutation = title;
        if (mutation.contains("(") && mutation.contains(")")) {
            String m1 = StringUtils.substringBefore(mutation, "_(");
            String m2;
            if (mutation.indexOf(")") != mutation.length() - 1) {
                m2 = StringUtils.substringAfter(mutation, ")");
            } else {
                m2 = "";
            }
            mutation = m1 + m2;
        }
        mutation = StringUtils.replace(mutation, "_", " ");
        mutation = StringUtils.trim(mutation);
        return mutation.toLowerCase();
    }

    private static String transformItemToWikiFormat(String item) {
        String mutation = item;
        mutation = StringUtils.trim(mutation);
        mutation = StringUtils.replace(mutation, " ", "_");
        return mutation;
    }

    /**
     * Barebones function to translate wiki titles to be something more like it would appear in text
     */
    public static class WikiToStringFunc implements Function<String,String>{
        @Override
        public String apply(String s) {
            return transformWikiTitle(s);
        }
    }

}
