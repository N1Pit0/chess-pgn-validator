package services.strategy.common;

import services.board.BoardService;
import services.board.SquareInterface;
import services.enums.PieceColor;

import java.awt.*;
import java.util.List;

public interface PieceInterface {
    List<SquareInterface> getLegalMoves(BoardService b);

    PieceColor getPieceColor();

    SquareInterface getCurrentSquare();

    void setCurrentSquare(SquareInterface currentSquare);

    boolean move(SquareInterface targetSquare);

    boolean isWasMoved();

    Image getImage();

}
