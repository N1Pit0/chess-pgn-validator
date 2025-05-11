package services.strategy.common;

import lombok.Getter;
import model.board.Square;
import services.board.Board;

import java.util.List;

@Getter
public abstract class PieceStrategy {
    private final PieceInterface piece;

    public PieceStrategy(PieceInterface piece) {
        this.piece = piece;
    }


    public abstract List<Square> getLegalMoves(Board board);
}
