package services.utils.filereader;

import java.io.IOException;

public interface FileReaderUtil extends Runnable{
    String[] readFile() throws IOException;
}
