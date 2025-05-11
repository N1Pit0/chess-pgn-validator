package services.strategy;

import services.board.Board;
import services.board.SquareInterface;
import services.strategy.common.PieceInterface;
import services.strategy.common.PieceStrategy;
import services.utils.MovementUtil;

import java.util.List;

public class BishopStrategy extends PieceStrategy {


    public BishopStrategy(PieceInterface piece) {
        super(piece);
    }

    @Override
    public List<SquareInterface> getLegalMoves(Board board) {

        return MovementUtil.getDiagonalMoves(board, super.getPiece());
    }
}
