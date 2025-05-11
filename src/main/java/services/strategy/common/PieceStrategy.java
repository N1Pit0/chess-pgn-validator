package services.strategy.common;

import lombok.Getter;
import services.board.Board;
import services.board.SquareInterface;

import java.util.List;

@Getter
public abstract class PieceStrategy {
    private final PieceInterface piece;

    public PieceStrategy(PieceInterface piece) {
        this.piece = piece;
    }


    public abstract List<SquareInterface> getLegalMoves(Board board);
}
