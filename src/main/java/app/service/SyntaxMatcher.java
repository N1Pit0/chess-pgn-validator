package app.service;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;

@Setter
@Getter
public class SyntaxMatcher {
    private String path;
    private BufferedReader reader;
    private StringBuilder moveText;

    public SyntaxMatcher(String path) throws FileNotFoundException {
        this.path = path;
        reader = new BufferedReader(new FileReader(path));
        moveText = new StringBuilder();
    }

    public void validatePgn() {
        validateTags();

        validateMoves();

        closeReader();
    }

    private boolean validateTags() {
        String line;
        boolean matches = true;
        try {
            while ((line = reader.readLine()) != null && matches) {

                line = line.strip();

                if (line.isEmpty()) break;

                String regex = "^(\\[[A-Z]\\w+ \"[^\"]+\"\\])+$";
                matches = RegEx.match(regex, line);
                System.out.print(" " + matches + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return matches;
    }

    private boolean validateMoves() {

        String line;
        String move;
        boolean matches = true;

        try {
            while ((line = reader.readLine()) != null) {
                moveText.append(line);
            }

            System.out.println(moveText.toString());

            String regexForMoveNumbers = "(?<=^|\\s)(\\d+)\\.(?=\\s)";

            checkMoveNumbers(regexForMoveNumbers);

            String[] movesMade = moveText.toString().split(regexForMoveNumbers);

            checkMovesMadeByEach(movesMade);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return matches;
    }

    private void checkMoveNumbers(String regexForMoveNumbers) {
        boolean matches = true;
        int expected = 1;

        Matcher matcher = RegEx.getMatcher(regexForMoveNumbers, moveText.toString());

        while (matcher.find()) {
            int moveNum = Integer.parseInt(matcher.group(1));

            if (moveNum != expected) {
                matches = false;
                System.out.println("Invalid move number: " + moveNum);
                break;
            }
            expected++;
            System.out.println("Found move number: " + moveNum);
        }
    }

    private void checkMovesMadeByEach(String[] movesMade) {
        String SAN_REGEX = "^(?:" +
                "O-O(?:-O)?[+#]?" +
                "|" +
                "[KQRBN](?:[a-h]|[1-8]|[a-h][1-8])?x?[a-h][1-8][+#]?" +
                "|" +
                "[a-h]x[a-h][1-8](?:=[QRBN])?[+#]?" +
                "|" +
                "[a-h][1-8](?:=[QRBN])?[+#]?" +
                "|" +
                "(\\d-\\d)?" +
                ")$";
        Arrays.stream(movesMade).forEach((movePair) -> {
            String[] arr = movePair.trim().split(" ");

            Arrays.stream(arr).forEach((move) -> {
                boolean s = RegEx.match(SAN_REGEX, move);
                System.out.print(" - " + s);
                System.out.println();
            });

        });
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
