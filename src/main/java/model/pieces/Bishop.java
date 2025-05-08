package model.pieces;

import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import model.pieces.common.Piece;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getBoard();
        int x = this.getCurrentSquare().getXNum();
        int y = this.getCurrentSquare().getYNum();

        return getDiagonalOccupations(board, x, y);
    }
}
