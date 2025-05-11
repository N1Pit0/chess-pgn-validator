package services.strategy;

import model.board.Square;
import model.pieces.common.Piece;
import services.board.Board;
import services.strategy.common.PieceStrategy;
import services.utils.MovementUtil;

import java.util.List;

public class BishopStrategy extends PieceStrategy {


    public BishopStrategy(Piece piece) {
        super(piece);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {

        return MovementUtil.getDiagonalMoves(board, super.getPiece());
    }
}
