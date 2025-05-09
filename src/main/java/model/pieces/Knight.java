package model.pieces;

import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import model.pieces.common.Piece;
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
