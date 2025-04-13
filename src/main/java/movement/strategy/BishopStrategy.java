package movement.strategy;

import chess.board.Board;
import chess.board.Square;
import model.common.Piece;
import movement.strategy.common.PieceStrategy;
import movement.utils.PieceMovementUtil;

import java.util.List;

public class BishopStrategy extends PieceStrategy {


    public BishopStrategy(Piece piece) {
        super(piece);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {

        Square[][] squareArrayBoard = board.getSquareArray();
        int x = super.getPiece().getCurrentSquare().getXNum();
        int y = super.getPiece().getCurrentSquare().getYNum();

        return PieceMovementUtil.getDiagonalOccupations(squareArrayBoard, x, y, super.getPiece());
    }
}
