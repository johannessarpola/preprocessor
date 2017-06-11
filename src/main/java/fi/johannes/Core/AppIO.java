package fi.johannes.Core;

import fi.johannes.Utilities.Logging.Logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Johannes on 7.5.2017.
 */
public class AppIO {

    /**
     * Stream all files in folder
     * @param inputFolder
     * @return
     * @throws IOException
     */
    public static Stream<String> readStreamsForFiles(String inputFolder) throws IOException {
        return Files.walk(Paths.get(inputFolder))
                .filter(Files::isRegularFile)
                .flatMap(f -> {
                    try {
                        return Files.lines(f);
                    } catch (IOException e) {
                        Logging.logMessageError(AppIO.class, "Could not read file "+f.getFileName());
                        return null;
                    }
                });
    }

    /**
     * Stream file
     * @param file
     * @return
     * @throws IOException
     */
    public static Stream<String> readStreamForFile(String file) throws IOException {
        return Files.lines(Paths.get(file));
    }
}
