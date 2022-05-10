import fileProcessor.FileProcessorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DirectoryObserverTest {
    private DirectoryObserver directoryObserver = DirectoryObserver.getDirectoryObserverInstance();
    FileProcessorFactory fileProcessorFactory = new FileProcessorFactory();
    DirectoryObserver dirObserverMock;
    Path currentRelativePath = Paths.get("");
    String pathStr = currentRelativePath.toAbsolutePath().toString();
    Path path = Path.of(pathStr+"/src/test/java/fileProcessor/impl");

    @Test
    @BeforeEach
    void getDirectoryObserverInstance() {
        // ensure singleton pattern was achieved
        var anotherInstance = DirectoryObserver.getDirectoryObserverInstance();

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new WatchCallable(path));

        // same object
        assertEquals(anotherInstance, directoryObserver);
    }

    @Test
    void startFileWatch(){
        dirObserverMock = Mockito.mock(DirectoryObserver.class);
        doNothing().when(dirObserverMock).startFileWatch(path);
        dirObserverMock.startFileWatch(path);
        verify(dirObserverMock, times(1)).startFileWatch(path);
    }
}