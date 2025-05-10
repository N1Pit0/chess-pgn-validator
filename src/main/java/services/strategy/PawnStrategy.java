package services.strategy;

import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import model.pieces.common.Piece;
import services.strategy.common.PieceStrategy;

import java.util.ArrayList;
import java.util.List;

import static model.enums.PieceColor.BLACK;
import static services.utils.MovementUtil.isInBound;

public class PawnStrategy extends PieceStrategy {
    private boolean wasMoved;

    public PawnStrategy(Piece piece) {
        super(piece);
    }

    @Override
    public boolean move(Square fin) {
        boolean b = super.move(fin);
        wasMoved = true;
        return b;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();
        Square[][] squareArrayBoard = board.getBoard();

        int x = super.getPiece().getCurrentSquare().getXNum();
        int y = super.getPiece().getCurrentSquare().getYNum();
        PieceColor pieceColor = super.getPiece().getColor();

        // Vertical direction: positive for BLACK, negative for WHITE
        int direction = (pieceColor.equals(BLACK)) ? 1 : -1;

        // Add potential moves using helper methods
        addStraightMove(legalMoves, squareArrayBoard, x, y, direction);
        addAttackMoves(legalMoves, squareArrayBoard, x, y, direction);

        return legalMoves;
    }

    // Helper method for straight moves (forward moves)
    private void addStraightMove(List<Square> legalMoves, Square[][] board, int x, int y, int direction) {
        // First move (can move two steps if not yet moved)
        if (!wasMoved && isInBound(y + 2 * direction, x) && !board[y + 2 * direction][x].isOccupied()) {
            legalMoves.add(board[y + 2 * direction][x]);
        }
        // Regular move (one step forward)
        if (isInBound(y + direction, x) && !board[y + direction][x].isOccupied()) {
            legalMoves.add(board[y + direction][x]);
        }
    }

    // Helper method for diagonal attack moves
    private void addAttackMoves(List<Square> legalMoves, Square[][] board, int x, int y, int direction) {
        // Check diagonal right
        if (isInBound(y + direction, x + 1) && board[y + direction][x + 1].isOccupied()) {
            legalMoves.add(board[y + direction][x + 1]);
        }
        // Check diagonal left
        if (isInBound(y + direction, x - 1) && board[y + direction][x - 1].isOccupied()) {
            legalMoves.add(board[y + direction][x - 1]);
        }
    }

}
