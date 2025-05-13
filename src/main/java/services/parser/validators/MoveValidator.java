package services.parser.validators;

import services.dtos.MoveDto;
import services.parser.RegEx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class MoveValidator {
    private static final String MOVE_NUMBER_REGEX = "(\\d+)\\.";
    private static final String SAN_REGEX = "^(O-O(?:-O)?|[KQRBN]?[a-h]?[1-8]?x?[a-h][1-8](=[QRBN])?[+#]?)$";

    public List<MoveDto> validateMoves(String moves, List<String> syntaxErrors) {
        List<MoveDto> moveDtos = new ArrayList<>();
        Set<Integer> seenMoveNumbers = new HashSet<>();
        int expectedMoveNumber = 1;

        Matcher matcher = RegEx.getMatcher(MOVE_NUMBER_REGEX, moves);
        String[] movePairs = moves.split(MOVE_NUMBER_REGEX);

        for (int i = 1; i < movePairs.length; i++) {
            String[] movePair = movePairs[i].trim().split("\\s+");
            String whiteMove = movePair.length > 0 ? movePair[0] : "";
            String blackMove = movePair.length > 1 ? movePair[1] : "";

            // Validate move numbers
            if (matcher.find()) {
                int moveNumber = Integer.parseInt(matcher.group(1));

                if (seenMoveNumbers.contains(moveNumber)) {
                    syntaxErrors.add("Repeated move number: " + moveNumber + ", expected: " + expectedMoveNumber);
                } else if (moveNumber != expectedMoveNumber) {
                    syntaxErrors.add("Invalid move number: " + moveNumber + ", expected: " + expectedMoveNumber);
                }

                seenMoveNumbers.add(moveNumber);
                expectedMoveNumber++;
            }

            // Validate SAN moves
            if (!isValidSan(whiteMove) || !isValidSan(blackMove)) {
                syntaxErrors.add("Invalid move pair: " + (expectedMoveNumber - 1) + ", " + whiteMove + " " + blackMove);
            } else {
                moveDtos.add(new MoveDto(expectedMoveNumber - 1, whiteMove, blackMove));
            }
        }

        return moveDtos;
    }

    private boolean isValidSan(String move) {
        return RegEx.match(SAN_REGEX, move);
    }
}