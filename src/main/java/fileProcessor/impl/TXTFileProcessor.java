package fileProcessor.impl;

import fileProcessor.FileProcessor;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class TXTFileProcessor implements FileProcessor {

    @Override
    public String processFile(Path path) {
        return readFile(path);
    }

    private String readFile(Path path){
        StringBuilder fileContent = new StringBuilder();

        try(BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null){
                fileContent.append(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return fileContent.toString();
    }

    @Override
    public void moveFile(Path current, Path destination){
        try {
            Files.move(current, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void writeStatToFile(Path path, String fileContent){
        Path write = Path.of(path+"/stats/output.txt");

        try {
            Files.write(write, Collections.singletonList(fileContent), StandardCharsets.UTF_8,
                    Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public String getMostUsedWords(String fileContent){
        String[] words = fileContent.split(" ");
        Map<String, Integer> mapOfWords = new HashMap<>();

        for (String word: words){
            if (mapOfWords.containsKey(word)) mapOfWords.merge(word, 1, Integer::sum);
            else mapOfWords.put(word, 1);
        }

       return Collections.max(mapOfWords.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    @Override
    public int getNumberOfDots(String fileContent){
        return fileContent.replaceAll("[^\\.]", "").length();
    }

    @Override
    public int getNumberOfWords(String fileContent){
        return fileContent.split(" ").length;
    }
}
