package model;

import chess.board.Board;
import chess.board.Square;
import model.common.Piece;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getSquareArray();
        int x = this.getCurrentSquare().getXNum();
        int y = this.getCurrentSquare().getYNum();

        return getDiagonalOccupations(board, x, y);
    }
}
