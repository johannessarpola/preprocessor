package fi.johannes.Core;

import fi.johannes.Core.AppConf.*;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by Johannes on 7.5.2017.
 */
@Data
public class AppState {

    private String inputFolder;
    private SupportedProcessingStrategy runningStrategy;

}
