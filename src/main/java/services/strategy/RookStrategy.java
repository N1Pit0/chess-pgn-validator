package services.strategy;

import services.board.BoardService;
import services.board.SquareInterface;
import services.strategy.common.PieceInterface;
import services.strategy.common.PieceStrategy;
import services.utils.MovementUtil;

import java.util.List;

public class RookStrategy extends PieceStrategy {
    public RookStrategy(PieceInterface piece) {
        super(piece);
    }

    @Override
    public List<SquareInterface> getLegalMoves(BoardService boardService) {

        return MovementUtil.getLinearMoves(boardService, this.getPiece());
    }
}
