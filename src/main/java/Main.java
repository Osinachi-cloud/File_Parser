import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        // Check directory path
        if(args.length == 0) {
            System.err.println("Supply directory path as an argument...");
            System.exit(0);
        }

        Path dir = getFilePath(args[0]);

        if(Files.exists(dir)){
            // watch out for file created in this directory
            System.out.println("\nRecords of all our files statics: \n"+getAllStatistics(dir)+"\n");
            fileWatchInProcess(dir);
        }else{
            System.err.println("Directory does not exists");
            System.exit(0);
        }
    }

    private static Path getFilePath(String dirPath){
        if(!dirPath.startsWith("/")) dirPath = String.format("/%s", dirPath);
        String fullPath = String.format("%s%s", getPresentWorkingDir(), dirPath);
        return Path.of(fullPath);
    }

    private static String getPresentWorkingDir(){
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }

    private static void fileWatchInProcess(Path path){
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new WatchCallable(path));
    }

    private static List<String> getAllStatistics(Path path){
        StatisticsDTO statisticsDTO = new StatisticsDTO();

        return statisticsDTO.getAllStats(Path.of(path+"/stats/output.txt"));
    }
}
