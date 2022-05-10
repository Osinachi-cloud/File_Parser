package fileProcessor;

import enums.FILETYPES;
import fileProcessor.impl.TXTFileProcessor;

public class FileProcessorFactory {

    public FileProcessor getProcessFile(FILETYPES filetypes){

        if(filetypes.equals(FILETYPES.TEXT)) return new TXTFileProcessor();
        // else other types

        return null;
    }
}
