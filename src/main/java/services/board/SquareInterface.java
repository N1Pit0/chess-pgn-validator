package services.board;

import services.enums.PieceColor;
import services.strategy.common.PieceInterface;

public interface SquareInterface {
    PieceColor getPieceColor();

    Board getBoard();

    PieceInterface getOccupyingPiece();

    void setOccupyingPiece(PieceInterface occupyingPiece);

    boolean isOccupied();

    int getXNum();

    void setXNum(int xNum);

    int getYNum();

    void setYNum(int yNum);

    void put(PieceInterface piece);

}
