package services.strategy.common;

import services.board.Board;
import services.board.SquareInterface;
import services.enums.PieceColor;

import java.awt.*;
import java.util.List;

public interface PieceInterface {
    List<SquareInterface> getLegalMoves(Board b);

    PieceColor getPieceColor();

    SquareInterface getCurrentSquare();

    void setCurrentSquare(SquareInterface currentSquare);

    boolean move(SquareInterface targetSquare);

    boolean isWasMoved();

    Image getImage();

}
