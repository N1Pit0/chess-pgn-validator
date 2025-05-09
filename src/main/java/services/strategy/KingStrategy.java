package services.strategy;

import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import model.pieces.common.Piece;
import services.strategy.common.PieceStrategy;
import services.utils.MovementUtil;

import java.util.LinkedList;
import java.util.List;

public class KingStrategy extends PieceStrategy {

    public KingStrategy(Piece piece) {
        super(piece);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new LinkedList<>();
        Square[][] squareBoard = board.getBoard();

        int x = super.getPiece().getCurrentSquare().getXNum();
        int y = super.getPiece().getCurrentSquare().getYNum();
        PieceColor currentColor = super.getPiece().getColor();

        // Check all adjacent squares in the 3x3 grid centered on (x, y)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Skip the current square (dx = 0, dy = 0)
                if (dx == 0 && dy == 0) {
                    continue;
                }

                // Compute the target square's coordinates
                int targetX = x + dx;
                int targetY = y + dy;

                // Check if the target square is valid and add to legal moves
                if (MovementUtil.isInBound(targetX, targetY)){
                    Square targetSquare = squareBoard[targetY][targetX];

                    if(!targetSquare.isOccupied()
                            || targetSquare.getOccupyingPiece().getColor() != currentColor){
                        legalMoves.add(targetSquare);
                    }
                }
            }
        }

        return legalMoves;
    }

}

