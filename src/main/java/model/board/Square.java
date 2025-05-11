package model.board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import services.board.BoardService;
import services.board.SquareInterface;
import services.enums.PieceColor;
import services.strategy.common.PieceInterface;


@Getter
@Setter
@EqualsAndHashCode(exclude = {"boardService", "pieceColor", "occupyingPiece"}, callSuper = false)
public class Square implements SquareInterface {
    private final PieceColor pieceColor;
    private BoardService boardService;
    private PieceInterface occupyingPiece;

    private int xNum;
    private int yNum;

    public Square(BoardService boardService, PieceColor pieceColor, int xNum, int yNum) {

        this.boardService = boardService;
        this.pieceColor = pieceColor;
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
