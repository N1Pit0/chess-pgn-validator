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
    private static final String SAN_REGEX = "^(1-0|0-1|1/2-1/2|O-O(?:-O)?|[KQRBN]?[a-h]?[1-8]?x?[a-h][1-8](=[QRBN])?[+#]?)$";

    public List<MoveDto> validateMoves(String moves, List<String> syntaxErrors) {
        List<MoveDto> moveDtos = new ArrayList<>();
        Set<Integer> seenMoveNumbers = new HashSet<>();
        int expectedMoveNumber = 1;

        Matcher matcher = RegEx.getMatcher(MOVE_NUMBER_REGEX, moves);
        String[] movePairs = moves.split(MOVE_NUMBER_REGEX);

        for (int i = 1; i < movePairs.length-1; i++) {
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
                moveDtos.add(new MoveDto(expectedMoveNumber - 1, whiteMove, blackMove, ""));
            }
        }

        addLastMove(movePairs, moveDtos, syntaxErrors, expectedMoveNumber);

        return moveDtos;
    }

    private boolean isValidSan(String move) {
        return RegEx.match(SAN_REGEX, move);
    }

    private void addLastMove(String[] movePairs, List<MoveDto> moveDtos, List<String> syntaxErrors, int expectedMoveNumber){
        // Check for end move notation
        String[] endMoveParts = movePairs[movePairs.length - 1].trim().split("\\s+");
        String lastMove = endMoveParts[endMoveParts.length - 1];

        if (!lastMove.equals("1-0") && !lastMove.equals("0-1") && !lastMove.equals("1/2-1/2")) {
            syntaxErrors.add("missing who is winner");
        }

        String whiteMove = endMoveParts[0];
        String blackMove = "";
        String winner = "";

        if(endMoveParts.length > 2) {
            blackMove = endMoveParts[1];
            winner = endMoveParts[2];
            if(!isValidSan(whiteMove) || !isValidSan(blackMove) || !isValidSan(winner)){
                syntaxErrors.add("Invalid move pair: " + expectedMoveNumber + ", " + whiteMove + " " + blackMove + " " + winner);
            }else {
                moveDtos.add(new MoveDto(expectedMoveNumber, whiteMove, blackMove, winner));
            }
        }else if(endMoveParts.length > 1) {
            winner = endMoveParts[1];
            if(!isValidSan(whiteMove) || !isValidSan(winner)){
                syntaxErrors.add("Invalid move pair: " + expectedMoveNumber + ", " + whiteMove + " " + winner);
            }else {
                moveDtos.add(new MoveDto(expectedMoveNumber, whiteMove, blackMove, winner));
            }
        }

    }

}