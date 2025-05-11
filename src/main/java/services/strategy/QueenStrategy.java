package services.strategy;

import model.board.Square;
import services.board.Board;
import services.strategy.common.PieceInterface;
import services.strategy.common.PieceStrategy;
import services.utils.MovementUtil;

import java.util.ArrayList;
import java.util.List;

public class QueenStrategy extends PieceStrategy {

    public QueenStrategy(PieceInterface piece) {
        super(piece);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {

        List<Square> legalMoves = new ArrayList<>(MovementUtil.getLinearMoves(board, this.getPiece()));

        legalMoves.addAll(MovementUtil.getDiagonalMoves(board, super.getPiece()));

        return legalMoves;
    }

}
