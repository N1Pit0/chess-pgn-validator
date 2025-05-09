package services.utils;

import model.board.Board;
import model.board.Square;
import model.pieces.common.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovementUtil {

    private static final int[][] LINEAR_DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    private static final int[][] DIAGONAL_DIRECTIONS = {
            {-1, -1}, {1, -1}, {1, 1}, {-1, 1}
    };

    public static List<Square> getLinearMoves(Board chessBoard, Piece piece) {
        return getMovesInDirections(chessBoard, piece, LINEAR_DIRECTIONS);
    }

    public static List<Square> getDiagonalMoves(Board chessBoard, Piece piece) {
        return getMovesInDirections(chessBoard, piece, DIAGONAL_DIRECTIONS);
    }

    private static List<Square> getMovesInDirections(Board board, Piece piece, int[][] directions) {
        List<Square> legalSquares = new ArrayList<>();
        Square[][] squares = board.getBoard();
        Square position = piece.getCurrentSquare();
        int x = position.getXNum();
        int y = position.getYNum();

        Arrays.stream(directions).forEach((direction) -> {
            int dy = direction[0];
            int dx = direction[1];
            int currentY = y + dy;
            int currentX = x + dx;

            while (isInBound(currentY, currentX)) {
                Square targetSquare = squares[currentY][currentX];

                if (targetSquare.isOccupied()) {
                    if (targetSquare.getOccupyingPiece().getColor() != piece.getColor()) {
                        legalSquares.add(targetSquare);
                    }
                    break;
                }

                legalSquares.add(targetSquare);
                currentY += dy;
                currentX += dx;
            }
        });

        return legalSquares;
    }

    public static boolean isInBound(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}
