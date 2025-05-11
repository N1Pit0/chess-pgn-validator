package model.pieces;

import lombok.Getter;
import model.pieces.common.Piece;
import services.board.BoardService;
import services.board.SquareInterface;
import services.enums.PieceColor;
import services.strategy.PawnStrategy;

import java.util.List;

@Getter
public class Pawn extends Piece {

    public Pawn(PieceColor color, SquareInterface initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public boolean move(SquareInterface fin) {
        boolean b = super.move(fin);
        setWasMoved(true);
        return b;
    }

    @Override
    public List<SquareInterface> getLegalMoves(BoardService boardService) {
        return new PawnStrategy(this).getLegalMoves(boardService);
    }

    public void dummy() {
    }

}
