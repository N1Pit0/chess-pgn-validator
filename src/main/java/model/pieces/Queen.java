package model.pieces;

import model.board.Square;
import model.enums.PieceColor;
import model.pieces.common.Piece;
import services.board.Board;
import services.strategy.QueenStrategy;

import java.util.List;

public class Queen extends Piece {

    public Queen(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {

        return new QueenStrategy(this).getLegalMoves(board);
    }

}
