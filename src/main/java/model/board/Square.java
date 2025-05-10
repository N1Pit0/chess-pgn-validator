package model.board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import model.enums.PieceColor;
import model.pieces.common.Piece;

import static model.enums.PieceColor.BLACK;
import static model.enums.PieceColor.WHITE;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"board", "color", "occupyingPiece"}, callSuper = false)
public class Square {
    private final PieceColor color;
    private Board board;
    private Piece occupyingPiece;

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

    public void put(Piece p) {
        this.occupyingPiece = p;
        p.setCurrentSquare(this);
    }

}
