import enums.FILETYPES;
import fileProcessor.FileProcessor;
import fileProcessor.FileProcessorFactory;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Locale;

public class DirectoryObserver{

    private WatchService watchService;
    private WatchKey watchKey;
    private Path path;
    private static final DirectoryObserver directoryObserver = new DirectoryObserver();

    public static DirectoryObserver getDirectoryObserverInstance(){

        return directoryObserver;
    }

    public void initializeWatchService(Path path){
        System.out.println("Initialized watchService");

        try {
            watchService = FileSystems.getDefault().newWatchService();
            watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createProcessedDir(Path path){
        System.out.println("creating processedDir... Please wait");

        this.path = path.resolve("processedDir");
        Path processedStatsDir = path.resolve("stats");

        try {
            if (!Files.exists(this.path)) {
                Files.createDirectories(this.path);
            }

            if (!Files.exists(processedStatsDir)) {
                Files.createDirectories(processedStatsDir);
                Files.createFile(Path.of(processedStatsDir +"/output.txt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startFileWatch(Path path){
        System.out.println("Started watching...");

        FileProcessorFactory fileProcessorFactory = new FileProcessorFactory();

        try {
            var files = Files.newDirectoryStream(path);

            files.forEach(file -> {
                var filePath = file.toString().toLowerCase(Locale.ROOT);

                if(filePath.endsWith(".txt")){
                    processFile(FILETYPES.TEXT, fileProcessorFactory, file.getFileName(), path);
                }

                // check other file types
            });

            while (true) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {

                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                    Path fileName = pathEvent.context();
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("A new file is created : " + fileName);
                        String file = fileName.toString();

                        if(file.endsWith(".txt")){
                            processFile(FILETYPES.TEXT, fileProcessorFactory, fileName, path);
                        }
                        // check other file types
                    }
                }
                boolean valid = watchKey.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processFile(FILETYPES filetype, FileProcessorFactory fileProcessorFactory, Path fileName, Path path){
        // call factory the class
        FileProcessor fileProcessor =  fileProcessorFactory.getProcessFile(filetype);

        // start file processing
        String fileContent = fileProcessor.processFile(path.resolve(fileName));
        String mostUsedWords = fileProcessor.getMostUsedWords(fileContent);
        int numberOfDots = fileProcessor.getNumberOfDots(fileContent);
        int numberOfWords = fileProcessor.getNumberOfWords(fileContent);

        // move file
        fileProcessor.moveFile(path.resolve(fileName), Path.of(this.path +"/"+fileName));

        // transfer data to dto
        StatisticsDTO dto = StatisticsDTO.builder()
                .numberOfDots(numberOfDots)
                .mostUsedWords(mostUsedWords)
                .numberOfWords(numberOfWords)
                .build();

        // display stats
        var stats = dto.getStatics(LocalDate.now());
        System.out.println("Stats of the newly loaded file: "+stats);

        // write this stats to file
        fileProcessor.writeStatToFile(path, stats);

    }

    public void stopFileWatch(){
        try {
            watchKey.cancel();
            watchService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
