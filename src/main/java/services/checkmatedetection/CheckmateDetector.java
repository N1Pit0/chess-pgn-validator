package services.checkmatedetection;

import model.board.Board;

public interface CheckmateDetector {
    void check(Board board);

    void checkmate(Board board);

    void stalemate(Board board);
}
