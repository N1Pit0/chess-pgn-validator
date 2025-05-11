package model.pieces.common;

import lombok.Getter;
import lombok.Setter;
import model.board.Square;
import services.enums.PieceColor;
import services.board.Board;
import services.board.Move;
import services.strategy.common.PieceInterface;
import services.utils.ImageReaderUtil;
import services.utils.ImageReaderUtilImpl;
import services.utils.exceptions.ImageNotFoundException;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public abstract class Piece implements PieceInterface {
    private PieceColor pieceColor;
    private Square currentSquare;
    private Image image;

    public Piece(PieceColor pieceColor, Square initSq, String img_file) {
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

    public boolean move(Square targetSquare) {

        return Move.makeMove(this, targetSquare, currentSquare.getBoard());
    }

    // No implementation, to be implemented by each subclass
    public abstract List<Square> getLegalMoves(Board b);
}