package model.pieces.common;

import lombok.Getter;
import lombok.Setter;
import services.board.Board;
import services.board.Move;
import services.board.SquareInterface;
import services.enums.PieceColor;
import services.strategy.common.PieceInterface;
import services.utils.ImageReaderUtil;
import services.utils.ImageReaderUtilImpl;
import services.utils.exceptions.ImageNotFoundException;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public abstract class Piece implements PieceInterface {
    private final PieceColor pieceColor;
    private SquareInterface currentSquare;
    private Image image;
    private boolean wasMoved;

    public Piece(PieceColor pieceColor, SquareInterface initSq, String img_file) {
        this.pieceColor = pieceColor;
        this.currentSquare = initSq;

        try {
            ImageReaderUtil imageReader = new ImageReaderUtilImpl();

            image = imageReader.readImage(img_file).orElseThrow(() -> new ImageNotFoundException("image not found"));
        } catch (ClassCastException e) {
            System.out.println("Class should extend Image");
            e.printStackTrace();
        }
    }

    public boolean move(SquareInterface targetSquare) {

        return Move.makeMove(this, targetSquare, currentSquare.getBoard());
    }

    // No implementation, to be implemented by each subclass
    public abstract List<SquareInterface> getLegalMoves(Board b);
}