package services.strategy.common;

import lombok.Getter;
import model.board.Board;
import model.board.Square;
import model.pieces.common.Piece;

import java.util.List;

@Getter
public abstract class PieceStrategy {
    private final Piece piece;

    public PieceStrategy(Piece piece) {
        this.piece = piece;
    }

    public boolean move(Square square) {
        Piece occup = square.getOccupyingPiece();

        if (occup != null) {
            if (occup.getColor() == piece.getColor()) return false;
            else square.capture(piece);
        }

        piece.getCurrentSquare().removePiece();
        piece.setCurrentSquare(square);
        piece.getCurrentSquare().put(piece);
        return true;
    }

    public abstract List<Square> getLegalMoves(Board board);
}
