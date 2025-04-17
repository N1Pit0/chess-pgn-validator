package app.service;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Setter
@Getter
public class SyntaxMatcher {
    private String path;
    private BufferedReader reader;

    public SyntaxMatcher(String path) throws FileNotFoundException {
        this.path = path;
        reader = new BufferedReader(new FileReader(path));
    }

    public boolean validatePgn(){
        String line;
        boolean matches = false;
        try {
            while((line = reader.readLine()) != null){
                String regex = "^(\\[[A-Z]\\w+ \"[^\"]+\"\\])+$";
                matches = RegEx.check(regex,line.strip());
                System.out.print(" " + matches);
                System.out.println();
            }

            closeReader();
        }catch (IOException e){
            e.printStackTrace();
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
