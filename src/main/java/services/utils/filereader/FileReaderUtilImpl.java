package services.utils.filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderUtilImpl implements FileReaderUtil{
    private final String path;

    public FileReaderUtilImpl(String path) {
        this.path = path;
    }

    public String[] readFile() throws IOException {
        StringBuilder tags = new StringBuilder();
        StringBuilder moves = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isMoveSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
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
