package fileProcessor;

import java.nio.file.Path;

public interface FileProcessor {
    String processFile(Path path);
    void moveFile(Path current, Path destination);
    void writeStatToFile(Path path, String fileContent);
    String getMostUsedWords(String fileContent);
    int getNumberOfDots(String fileContent);
    int getNumberOfWords(String fileContent);
}
