package fi.johannes.Core;

import fi.johannes.Core.AppConf.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Created by Johannes on 7.5.2017.
 */
@Data
public class AppState {

    private Integer limitInputRows;
    private String inputFolder;
    private String inputFile;
    private String outputFolder;
    private String outputFile;
    private SupportedProcessingStrategy runningStrategy;

    public void setInputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public void setRunningStrategy(SupportedProcessingStrategy runningStrategy) {
        this.runningStrategy = runningStrategy;
    }

    public Optional<Integer> getLimitInputRows() {
        return Optional.ofNullable(limitInputRows);
    }

    /**
     * Sets the input rows and is added 1 since then it's more suitable for cli
     * @param limitInputRows
     */
    public void setLimitInputRows(Integer limitInputRows) {
        this.limitInputRows = limitInputRows +1;
    }

    public Optional<String> getInputFolder() {
        return Optional.ofNullable(inputFolder);
    }

    public Optional<String> getInputFile() {
        return Optional.ofNullable(inputFile);
    }

    public Optional<String> getOutputFolder() {
        return Optional.ofNullable(outputFolder);
    }

    public Optional<String> getOutputFile() {
        return Optional.ofNullable(outputFile);
    }

    public Optional<SupportedProcessingStrategy> getRunningStrategy() {
        return Optional.ofNullable(runningStrategy);
    }

    public boolean useInputFolder() {
        return inputFolder != null;
    }
    public boolean useOutputFolder() {
        return outputFolder != null;
    }
    public boolean useInputLimitting() { return limitInputRows != null; }

}
