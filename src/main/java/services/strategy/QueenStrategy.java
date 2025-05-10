package services.strategy;

import model.board.Board;
import model.board.Square;
import model.pieces.common.Piece;
import services.strategy.common.PieceStrategy;
import services.utils.MovementUtil;

import java.util.ArrayList;
import java.util.List;

public class QueenStrategy extends PieceStrategy {

    public QueenStrategy(Piece piece) {
        super(piece);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {

        List<Square> legalMoves = new ArrayList<>(MovementUtil.getLinearMoves(board, this.getPiece()));

        legalMoves.addAll(MovementUtil.getDiagonalMoves(board, super.getPiece()));

        return legalMoves;
    }

}
