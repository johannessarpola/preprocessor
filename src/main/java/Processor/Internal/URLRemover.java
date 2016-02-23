/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor.Internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.UrlValidator;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class URLRemover {

    static Pattern urlRegex = Pattern.compile("https?:\\/\\/(?:www\\.|(?!www))[^\\s\\.]+\\.[^\\s]{2,}|www\\.[^\\s]+\\.[^\\s]{2,}", Pattern.CASE_INSENSITIVE);
    static UrlValidator validator = new UrlValidator(new String[]{"http", "https"}, UrlValidator.ALLOW_ALL_SCHEMES);
    public static String removeUrl(String word) {
        if(testForUrl(word)) {
            return "";
        } else {
            return word;
        }
    }
    public static boolean testForUrl(String word) {
        if (word.startsWith("(") && word.endsWith(")")) {
            word = word.substring(1, word.length() - 2);
        }
        boolean isUrl = false;
        Matcher m = urlRegex.matcher(word); // Amazingly slow
        isUrl = m.matches();
        if (!word.startsWith("www.")) {
            try {
                isUrl = validator.isValid(word);
            } catch (Exception e) {
                return false;
            }
        }
        return isUrl;

    }

    public static boolean javaUrl(String s) {
        // separate input by spaces ( URLs don't have spaces )
        try {
            URL url = new URL(s);

            // If possible then replace with anchor...
            return true;
        } catch (MalformedURLException e) {
            // If there was an URL that was not it!...
            return false;
        }
    }

}
