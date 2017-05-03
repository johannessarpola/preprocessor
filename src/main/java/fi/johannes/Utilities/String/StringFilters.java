package fi.johannes.Utilities.String;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Johannes on 3.5.2017.
 */
public class StringFilters {

    private static Pattern allowedCharPattern = Pattern.compile("[^A-Za-z0-9'-,. ]");

    /**
     * Returns if string has special characters
     * @param s
     * @return
     */
    public static boolean hasOnlyAllowedCharacters(String s) {
        return !allowedCharPattern.matcher(s).matches();
    }
}
