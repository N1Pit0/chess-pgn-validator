package model.pieces;

import lombok.Getter;
import model.board.Square;
import services.enums.PieceColor;
import model.pieces.common.Piece;
import services.board.Board;
import services.strategy.PawnStrategy;

import java.util.List;

@Getter
public class Pawn extends Piece {

    public Pawn(PieceColor color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public boolean move(Square fin) {
        boolean b = super.move(fin);
        setWasMoved(true);
        return b;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        return new PawnStrategy(this).getLegalMoves(board);
    }

    public void dummy() {
    }

}
