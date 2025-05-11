package model.pieces;

import model.pieces.common.Piece;
import services.board.Board;
import services.board.SquareInterface;
import services.enums.PieceColor;
import services.strategy.KingStrategy;

import java.util.List;

public class King extends Piece {

    public King(PieceColor color, SquareInterface initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<SquareInterface> getLegalMoves(Board board) {
        return new KingStrategy(this).getLegalMoves(board);
    }

}
