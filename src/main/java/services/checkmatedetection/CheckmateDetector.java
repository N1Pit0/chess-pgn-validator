package services.checkmatedetection;

import services.board.Board;
import services.enums.PieceColor;

public interface CheckmateDetector {
    boolean isInCheck(Board board, PieceColor pieceColor);

    boolean isInCheckmate(Board board, PieceColor pieceColor);

    boolean isInStalemate(Board board, PieceColor pieceColor);
}
