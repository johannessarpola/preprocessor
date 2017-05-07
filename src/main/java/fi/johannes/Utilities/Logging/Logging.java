package fi.johannes.Utilities.Logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logging {

    public static String stringifyException(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    /**
     * Logs the from and stacktrace (error)
     *
     * @param from
     * @param e
     */
    public static void logStackTraceError(Object from, Exception e) {
        Logger logger = LoggerFactory.getLogger(from.getClass());
        logger.error(stringifyException(e));
    }

    public static void logStackTraceError(Class<? extends Object> from, Exception e) {
        Logger logger = LoggerFactory.getLogger(from);
        logger.error(stringifyException(e));
    }

    public static void logMessageError(Class<? extends Object> from, String msg) {
        Logger logger = LoggerFactory.getLogger(from);
        logger.error(msg);
    }

    public static void logMessageError(Class<? extends Object> from, String msg, Exception e) {
        Logger logger = LoggerFactory.getLogger(from);
        logger.error(msg);
    }

    public static void logMessageInfo(Class<? extends Object> from, String msg) {
        Logger logger = LoggerFactory.getLogger(from);
        logger.info(msg);
    }
    public static void logMessageDebug(Class<? extends Object> from, String msg) {
        Logger logger = LoggerFactory.getLogger(from);
        logger.debug(msg);
    }
    public static void logMessageWarn(Class<? extends Object> from, String msg) {
        Logger logger = LoggerFactory.getLogger(from);
        logger.warn(msg);
    }
    public static void logMessageWarn(Class<? extends Object> from, String msg, Exception e) {
        Logger logger = LoggerFactory.getLogger(from);
        logger.warn(msg, e);
    }
}
