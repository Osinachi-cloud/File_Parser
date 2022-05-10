import java.nio.file.Path;
import java.util.concurrent.Callable;

public record WatchCallable(Path path) implements Callable<Void> {

    @Override
    public Void call() {
        DirectoryObserver dirObserver = DirectoryObserver.getDirectoryObserverInstance();
        dirObserver.initializeWatchService(path);
        dirObserver.createProcessedDir(path);
        dirObserver.startFileWatch(path);
        return null;
    }
}
