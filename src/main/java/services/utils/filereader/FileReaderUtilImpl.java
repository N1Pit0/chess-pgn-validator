package services.utils.filereader;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Getter
public class FileReaderUtilImpl implements FileReaderUtil{
    private final File file;
    private boolean isFileFullyRead;

    public FileReaderUtilImpl(File file) {
        this.file = file;
        this.isFileFullyRead = false;
    }

    public String[] readSingleGameFromFile() throws IOException {
        StringBuilder tags = new StringBuilder();
        StringBuilder moves = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isMoveSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if(isMoveSection && line.isEmpty()) {
                    isFileFullyRead = true;
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
        }

        return new String[]{tags.toString(), moves.toString()};
    }
}
