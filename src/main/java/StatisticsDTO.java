import lombok.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsDTO {
    private int numberOfDots;
    private int numberOfWords;
    private String mostUsedWords;

    public String getStatics(LocalDate localDate) {
        return "{" +
                    "numberOfDots:" + numberOfDots +
                    ", numberOfWords:" + numberOfWords +
                    ", mostUsedWords:\"" + mostUsedWords + '\"' +
                    ", dateCreated:\"" + localDate + '\"' +
                 "}";
    }

    public List<String> getAllStats(Path path){
        List<String> stats = new ArrayList<>();

        try(BufferedReader read = Files.newBufferedReader(path)) {
            String line;
            while ((line = read.readLine()) != null){
                stats.add(line+"\n");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return stats;
    }
}
