package services.strategy.common;

import model.board.Square;
import services.enums.PieceColor;
import services.board.Board;

import java.awt.*;
import java.util.List;

public interface PieceInterface {
    List<Square> getLegalMoves(Board b);

    PieceColor getPieceColor();

    Square getCurrentSquare();
    void setCurrentSquare(Square currentSquare);

    boolean move(Square targetSquare);
    boolean isWasMoved();

    Image getImage();

}
