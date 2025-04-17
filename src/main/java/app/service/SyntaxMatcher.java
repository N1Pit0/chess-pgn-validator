package app.service;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Setter
@Getter
public class SyntaxMatcher {
    private String path;
    private BufferedReader reader;

    public SyntaxMatcher(String path) throws FileNotFoundException {
        this.path = path;
        reader = new BufferedReader(new FileReader(path));
    }

    public void validatePgn(){

        validateMoves();
    }

    private boolean validateTags(){
        String line;
        boolean matches = true;
        try {
            while((line = reader.readLine()) != null && matches){

                line = line.strip();

                if (line.isEmpty()) continue;

                String regex = "^(\\[[A-Z]\\w+ \"[^\"]+\"\\])+$";
                matches = RegEx.check(regex,line);
                System.out.print(" " + matches + "\n");
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return matches;
    }

    private boolean validateMoves(){
        StringBuilder text = new StringBuilder();
        String line;
        String move;
        boolean matches = true;

        try {
            while((line = reader.readLine()) != null){
                text.append(line);
            }

            System.out.println(text.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return matches;
    }

    private void closeReader() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
