package ua.com.foxminded.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class FileParser {

    public String[] readFileToLines(String fileName) {
        String file = receivePath(fileName);
        String[] sqlQueryList = new String[1];
        try (Stream<String> fileInStream = Files.lines(Paths.get(file))) {
            sqlQueryList = fileInStream.toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlQueryList;
    }

    private void checkFile(File file) {
        if (isNotExist(file)) {
            throw new IllegalArgumentException("File " + file + " doesn't exist");
        }
        if (isNotFile(file)) {
            throw new IllegalArgumentException("Directory is  not allowed  " 
        + file + " Wait for a file ....");
        }
    }

    String receivePath(String fileName) {
        checkFileName(fileName);

        ClassLoader classLoader = getClass().getClassLoader();

        File file = Optional.ofNullable(classLoader.getResource(fileName))
                .map(URL::getFile)
                .map(File::new)
                .orElseThrow(() -> new IllegalArgumentException("File doesn't exis"));

        checkFile(file);

        return file.getAbsolutePath();
    }

    public void checkFileName(String fileName) {
        Optional.ofNullable(fileName)
                .orElseThrow(() -> new IllegalArgumentException("Null as file name is not allowed "));

        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("Empty parameters are not allowed " + fileName);
        }
    }

    private boolean isNotFile(File file) {
        return !file.isFile();
    }

    private boolean isNotExist(File file) {
        return !file.exists();
    }
}

