package services.checkmatedetection;

import model.board.Board;
import model.enums.PieceColor;

public interface CheckmateDetector {
    boolean isInCheck(Board board, PieceColor pieceColor);

    boolean isInCheckmate(Board board, PieceColor pieceColor);

    boolean isInStalemate(Board board, PieceColor pieceColor);
}
