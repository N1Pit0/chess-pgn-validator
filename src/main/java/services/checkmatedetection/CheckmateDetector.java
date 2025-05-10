package services.checkmatedetection;

import model.board.Board;

public interface CheckmateDetector {
    boolean isInCheck(Board board);

    boolean isInCheckmate(Board board);

    boolean isInStalemate(Board board);
}
