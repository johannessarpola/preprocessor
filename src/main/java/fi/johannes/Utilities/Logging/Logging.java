package fi.johannes.Utilities.Logging;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logging {
    static Logger logger;

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
    public static void logStackTrace_Error(Object from, Exception e) {
        logger = Logger.getLogger(from.getClass());
        logger.error(stringifyException(e));
    }

    public static void logStackTrace_Error(Class<? extends Object> from, Exception e) {
        logger = Logger.getLogger(from);
        logger.error(stringifyException(e));
    }

    /**
     * Logs the from and stacktrace (fatal)
     *
     * @param from
     * @param e
     */
    public static void logStackTrace_Fatal(Object from, Exception e) {
        logger = Logger.getLogger(from.getClass());
        logger.fatal(stringifyException(e));
    }

    public static void logStackTrace_Fatal(Class<? extends Object> from, Exception e) {
        logger = Logger.getLogger(from);
        logger.fatal(stringifyException(e));
    }

    public static void logMessage_Fatal(Class<? extends Object> from, String msg) {
        Logger logger = Logger.getLogger(from);
        logger.fatal(msg);
    }

    public static void logMessage_Fatal(Class<? extends Object> from, String msg, Exception e) {
        Logger logger = Logger.getLogger(from);
        logger.fatal(msg);
    }

    public static void logMessage_Error(Class<? extends Object> from, String msg) {
        logger = Logger.getLogger(from);
        logger.error(msg);
    }

    public static void logMessage_Error(Class<? extends Object> from, String msg, Exception e) {
        logger = Logger.getLogger(from);
        logger.error(msg);
    }

    public static void logMessage_Info(Class<? extends Object> from, String msg) {
        logger = Logger.getLogger(from);
        logger.info(msg);
    }
}
