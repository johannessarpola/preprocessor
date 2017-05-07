package fi.johannes.Core;

import java.util.logging.Level;
import java.util.logging.Logger;

import fi.johannes.Utilities.Logging.Logging;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Component;

/**
 * Created by Johannes on 7.5.2017.
 */
public class AppCli {

    private AppState state;

    private String[] args = null;
    private Options options = new Options();

    public AppCli(String[] args) {
        this.args = args;

        options.addOption("h", "help", false, "show help.");
        options.addOption("v", "var", true, "Here you can set parameter .");

        // todo input folder
        // todo output if vector out
    }

    public AppCli parse() {
        CommandLineParser parser = new BasicParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h"))
                help();

            if (cmd.hasOption("v")) {
                Logging.logMessage_Info(this.getClass(), "Using cli argument -v=" + cmd.getOptionValue("v"));
            } else {
                Logging.logMessage_Error(this.getClass(), "MIssing v option");
                help();
            }

        } catch (ParseException e) {
            Logging.logMessage_Error(this.getClass(), "Failed to parse comand line properties", e);
            help();
        }
        return this;
    }

    private void help() {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", options);
        System.exit(0);
    }

    /**
     * Gets the state when the app should start
     * @return
     */
    public AppState getState() {
        return state;
    }

}
