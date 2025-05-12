package services.utils.filereader;

import java.io.IOException;

public interface FileReaderUtil {
    String[] readSingleGameFromFile() throws IOException;
    boolean isFileFullyRead();
}
