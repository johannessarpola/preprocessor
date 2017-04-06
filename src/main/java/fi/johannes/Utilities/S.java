package fi.johannes.Utilities;

/**
 * Shorthand utils for strings
 * Created by Johannes on 6.4.2017.
 */
public class S {

    public static String fmt(String string, Object ... objs){
        return String.format(string, objs);
    }
}
