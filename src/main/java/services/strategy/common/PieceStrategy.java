package services.strategy.common;

import lombok.Getter;
import model.board.Square;
import model.pieces.common.Piece;
import services.board.Board;

import java.util.List;

@Getter
public abstract class PieceStrategy {
    private final Piece piece;

    public PieceStrategy(Piece piece) {
        this.piece = piece;
    }


    public abstract List<Square> getLegalMoves(Board board);
}
