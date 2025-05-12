package services.parser;

import lombok.Getter;
import lombok.Setter;
import services.dtos.MoveDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;

public class SyntaxMatcher {
    @Getter
    private final String path;

    private final List<MoveDto> moveDtos;

    @Getter
    private final List<MoveDto> moveSyntaxErrors;

    @Getter
    private final Map<Integer, String> tagSyntaxErrorMap ;

    private Integer syntaxErrorCounter = 1;

    public SyntaxMatcher(String path) {
        this.path = path;
        moveDtos = new ArrayList<>();
        tagSyntaxErrorMap = new HashMap<>();
        moveSyntaxErrors = new ArrayList<>();
    }

    public List<MoveDto> validatePgn() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            validateTags(reader);
            validateMoves(reader);
        }
        return moveDtos;
    }

    private void validateTags(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) break; // End of tags section

            String regex = "^\\[[A-Z]\\w+ \"[^\"]+\"\\]$";
            if (!RegEx.match(regex, line)) {
                tagSyntaxErrorMap.put(syntaxErrorCounter++, "Invalid tag: " + line);
            }
        }
    }

    private void validateMoves(BufferedReader reader) throws IOException {
        StringBuilder moveText = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            moveText.append(line).append(" ");
        }

        String regexForMoveNumbers = "(\\d+)\\.";

        checkMoveNumbers(regexForMoveNumbers, moveText.toString());

        String[] movesMade = moveText.toString().split(regexForMoveNumbers);

        for (int i = 1; i < movesMade.length; i++) {
            String[] movePair = movesMade[i].trim().split("\\s+");
            String whiteMove = movePair.length > 0 ? movePair[0] : "";
            String blackMove = movePair.length > 1 ? movePair[1] : "";

            if (!isValidSan(whiteMove) || !isValidSan(blackMove)) {
                moveSyntaxErrors.add(new MoveDto(i, whiteMove, blackMove));
                tagSyntaxErrorMap.put(syntaxErrorCounter++, "Invalid move pair: " + whiteMove + " " + blackMove);
            } else {
                moveDtos.add(new MoveDto(i, whiteMove, blackMove));
            }
        }
    }

    private void checkMoveNumbers(String regexForMoveNumbers, String moveText) {
        int expected = 1; // Start with the first move number
        Matcher matcher = RegEx.getMatcher(regexForMoveNumbers, moveText);
        Set<Integer> seenMoveNumbers = new HashSet<>(); // Track seen move numbers to detect repeats

        while (matcher.find()) {
            int moveNum = Integer.parseInt(matcher.group(1));

            // Check if the move number is repeated
            if (seenMoveNumbers.contains(moveNum)) {
                tagSyntaxErrorMap.put(syntaxErrorCounter++, "Repeated move number: " + moveNum + ", expected: " + expected);
                System.out.println("Repeated move number: " + moveNum + ", expected: " + expected);
                expected++;
                continue; // Skip further checks for this move number
            }

            // Add the move number to the set of seen numbers
            seenMoveNumbers.add(moveNum);

            // Check if the move number is not sequential
            if (moveNum != expected) {
                seenMoveNumbers.remove(moveNum);
                tagSyntaxErrorMap.put(syntaxErrorCounter++, "Invalid move number: " + moveNum + ", expected " + expected);
                System.out.println("Invalid move number detected: " + moveNum + ", expected " + expected);
            }

            expected++;

        }
    }

    private boolean isValidSan(String move) {
        String SAN_REGEX = "^(O-O(?:-O)?|[KQRBN]?[a-h]?[1-8]?x?[a-h][1-8](=[QRBN])?[+#]?)$";
        return RegEx.match(SAN_REGEX, move);
    }

}
