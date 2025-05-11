package services.strategy.common;

import model.board.Square;
import services.enums.PieceColor;
import services.board.Board;

import java.util.List;

public interface PieceInterface {
    List<Square> getLegalMoves(Board b);

    PieceColor getPieceColor();
    void setPieceColor(PieceColor pieceColor);

    Square getCurrentSquare();
    void setCurrentSquare(Square currentSquare);


}
