package services.checkmatedetection;

import model.enums.PieceColor;
import services.board.Board;

public interface CheckmateDetector {
    boolean isInCheck(Board board, PieceColor pieceColor);

    boolean isInCheckmate(Board board, PieceColor pieceColor);

    boolean isInStalemate(Board board, PieceColor pieceColor);
}
