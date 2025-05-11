package model.pieces;

import model.board.Square;
import services.enums.PieceColor;
import model.pieces.common.Piece;
import services.board.Board;

import services.strategy.KnightStrategy;

import java.util.List;

public class Knight extends Piece {

    public Knight(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {

        return new KnightStrategy(this).getLegalMoves(board);
    }

}
