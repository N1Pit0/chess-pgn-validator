package services.strategy;

import model.board.Square;
import model.pieces.common.Piece;
import services.board.Board;
import services.strategy.common.PieceStrategy;
import services.utils.MovementUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightStrategy extends PieceStrategy {

    public KnightStrategy(Piece piece) {
        super(piece);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();
        Square[][] squareBoard = board.getBoard();
        Square currentSquare = getPiece().getCurrentSquare();

        int x = currentSquare.getXNum();
        int y = currentSquare.getYNum();

        int[][] directions = {
                {2, 1}, {1, 2}, {-1, 2},
                {-2, 1}, {-2, -1}, {-1, -2},
                {1, -2}, {2, -1}
        };

        Arrays.stream(directions).forEach(direction -> {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (MovementUtil.isInBound(newX, newY)) {
                Square targetSquare = squareBoard[newY][newX];

                if (!targetSquare.isOccupied()
                        || targetSquare.getOccupyingPiece().getColor() != getPiece().getColor()) {
                    legalMoves.add(targetSquare);
                }
            }

        });

        return legalMoves;
    }
}
