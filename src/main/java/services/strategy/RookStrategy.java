package services.strategy;

import model.board.Board;
import model.board.Square;
import model.pieces.common.Piece;
import services.strategy.common.PieceStrategy;
import services.utils.MovementUtil;

import java.util.LinkedList;
import java.util.List;

public class RookStrategy extends PieceStrategy {
    public RookStrategy(Piece piece) {
        super(piece);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        Square[][] squareArrayBoard = board.getBoard();

        return MovementUtil.getLinearMoves(board, this.getPiece());
    }
}
