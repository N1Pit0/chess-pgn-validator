package chess.board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import model.common.Piece;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"board", "color", "occupyingPiece"}, callSuper = false)
public class Square {
    private final int color;
    private Board board;
    private Piece occupyingPiece;

    private int xNum;
    private int yNum;

    public Square(Board board, int c, int xNum, int yNum) {

        this.board = board;
        this.color = c;
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

    public Piece removePiece() {
        Piece p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }

    public void capture(Piece p) {
        Piece k = getOccupyingPiece();
        if (k.getColor() == 0) board.getBlackPieces().remove(k);
        if (k.getColor() == 1) board.getWhitePieces().remove(k);
        this.occupyingPiece = p;
    }


}
