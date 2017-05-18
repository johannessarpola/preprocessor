package fi.johannes.Utilities.Shorthands;

import fi.johannes.Utilities.Logging.Logging;

/**
 * Shorthand utils for Logging
 * Created by Johannes on 18.5.2017.
 */
public class Log {

    public static void info(Class<? extends Object> from, String msg) {
        Logging.logMessageInfo(from, msg);
    }
    public static void warn(Class<? extends Object> from, String msg) {
        Logging.logMessageWarn(from, msg);
    }
    public static void err(Class<? extends Object> from, String msg) {
        Logging.logMessageError(from, msg);
    }
}
