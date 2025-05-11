package services.strategy;

import services.board.BoardService;
import services.board.SquareInterface;
import services.enums.PieceColor;
import services.strategy.common.PieceInterface;
import services.strategy.common.PieceStrategy;

import java.util.ArrayList;
import java.util.List;

import static services.enums.PieceColor.BLACK;
import static services.utils.MovementUtil.isInBound;

public class PawnStrategy extends PieceStrategy {

    public PawnStrategy(PieceInterface piece) {
        super(piece);
    }

    @Override
    public List<SquareInterface> getLegalMoves(BoardService boardService) {
        List<SquareInterface> legalMoves = new ArrayList<>();
        SquareInterface[][] squareArrayBoard = boardService.getBoard();

        int x = super.getPiece().getCurrentSquare().getXNum();
        int y = super.getPiece().getCurrentSquare().getYNum();
        PieceColor pieceColor = super.getPiece().getPieceColor();

        // Vertical direction: positive for BLACK, negative for WHITE
        int direction = (pieceColor.equals(BLACK)) ? 1 : -1;

        // Add potential moves using helper methods
        addStraightMove(legalMoves, squareArrayBoard, x, y, direction);
        addAttackMoves(legalMoves, squareArrayBoard, x, y, direction);

        return legalMoves;
    }

    // Helper method for straight moves (forward moves)
    private void addStraightMove(List<SquareInterface> legalMoves, SquareInterface[][] board, int x, int y, int direction) {
        // First move (can move two steps if not yet moved)
        PieceInterface pawn = getPiece();
        if (!(pawn.isWasMoved()) && isInBound(y + 2 * direction, x) && !board[y + 2 * direction][x].isOccupied()) {
            legalMoves.add(board[y + 2 * direction][x]);
        }
        // Regular move (one step forward)
        if (isInBound(y + direction, x) && !board[y + direction][x].isOccupied()) {
            legalMoves.add(board[y + direction][x]);
        }
    }

    // Helper method for diagonal attack moves
    private void addAttackMoves(List<SquareInterface> legalMoves, SquareInterface[][] board, int x, int y, int direction) {
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
