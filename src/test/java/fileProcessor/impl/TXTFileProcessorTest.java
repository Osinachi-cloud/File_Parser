package fileProcessor.impl;

import enums.FILETYPES;
import fileProcessor.FileProcessor;
import fileProcessor.FileProcessorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;

class TXTFileProcessorTest {

    FileProcessorFactory fileProcessorFactory = new FileProcessorFactory();
    FileProcessor fileProcessor = fileProcessorFactory.getProcessFile(FILETYPES.TEXT);
    private String fileContent;

    @Test
    @BeforeEach
    void processFile() {

        Path currentRelativePath = Paths.get("");
        String pathStr = currentRelativePath.toAbsolutePath().toString();
        Path path = Path.of(pathStr+"/src/test/java/fileProcessor/impl/testing.txt");

        fileContent = fileProcessor.processFile(path);

        Assertions.assertEquals("Hello how are you you doing?", fileContent);
    }

    @Test
    void getMostUsedWords() {
        Assertions.assertEquals("you", fileProcessor.getMostUsedWords(fileContent));
    }

    @Test
    void getNumberOfDots() {
        Assertions.assertEquals(0, fileProcessor.getNumberOfDots(fileContent));
    }

    @Test
    void getNumberOfWords() {
        Assertions.assertEquals(6, fileProcessor.getNumberOfWords(fileContent));
    }
}