
package fi.johannes.Clusters.EntityDetection.WikipediaTitles;

import org.apache.commons.lang.StringUtils;

/**
 * Translates strings to wiki form and vice versa (TODO Not used at the moment)
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WikiTransformer {

    /**
     * _ to space (to more detail)
     *
     * @param title
     * @return
     */
    public static String translateWikiTitle(String title) {
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
        return mutation;
    }

    public static String transformToWTitle(String item) {
        String mutation = item;
        mutation = StringUtils.trim(mutation);
        mutation = StringUtils.replace(mutation, " ", "_");
        return mutation;
    }
    // TODO Create model for probable wiki title, the parenthesis for example
    
}
