package services.utils.filereader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderUtilImpl implements FileReaderUtil {
    private final BufferedReader reader;
    private boolean isFileFullyRead;

    public FileReaderUtilImpl(File file) throws IOException {
        this.reader = new BufferedReader(new FileReader(file));
        this.isFileFullyRead = false;
    }

    public synchronized String[] readSingleGameFromFile() throws IOException {
        StringBuffer tags = new StringBuffer();
        StringBuffer moves = new StringBuffer();
        String line;
        boolean isMoveSection = false;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (isMoveSection && line.isEmpty()) {
                break; // New Game Section Starts
            }

            if (line.isEmpty()) {
                isMoveSection = true;
                continue;
            }

            if (isMoveSection) {
                moves.append(line).append(" ");
            } else {
                tags.append(line).append("\n");
            }
        }

        if (line == null) {
            isFileFullyRead = true;
            reader.close(); // Close the reader when the file is fully read
        }

        return new String[]{tags.toString(), moves.toString()};
    }

    public boolean isFileFullyRead() {
        return isFileFullyRead;
    }
}