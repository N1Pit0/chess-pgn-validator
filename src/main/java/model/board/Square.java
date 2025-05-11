package model.board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import services.enums.PieceColor;
import services.board.Board;
import services.strategy.common.PieceInterface;


@Getter
@Setter
@EqualsAndHashCode(exclude = {"board", "color", "occupyingPiece"}, callSuper = false)
public class Square {
    private final PieceColor color;
    private Board board;
    private PieceInterface occupyingPiece;

    private int xNum;
    private int yNum;

    public Square(Board board, PieceColor color, int xNum, int yNum) {

        this.board = board;
        this.color = color;
        this.xNum = xNum;
        this.yNum = yNum;

    }

    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }

    public void put(PieceInterface piece) {
        this.occupyingPiece = piece;
        piece.setCurrentSquare(this);
    }

}
