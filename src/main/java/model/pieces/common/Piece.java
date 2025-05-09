package model.pieces.common;

import lombok.Getter;
import lombok.Setter;
import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import services.utils.ImageReaderUtil;
import services.utils.ImageReaderUtilImpl;
import services.utils.exceptions.ImageNotFoundException;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public abstract class Piece {
    private final PieceColor color;
    private Square currentSquare;
    private Image image;

    public Piece(PieceColor color, Square initSq, String img_file) {
        this.color = color;
        this.currentSquare = initSq;

        try {
            ImageReaderUtil imageReader = new ImageReaderUtilImpl();

            image = imageReader.readImage(img_file).orElseThrow(() -> new ImageNotFoundException("image not found"));
        } catch (ClassCastException e) {
            System.out.println("Class should extend Image");
            e.printStackTrace();
        }
    }

    public boolean move(Square fin) {
        Piece occup = fin.getOccupyingPiece();

        if (occup != null) {
            if (occup.getColor() == this.color) return false;
            else fin.capture(this);
        }

        currentSquare.removePiece();
        this.currentSquare = fin;
        currentSquare.put(this);
        return true;
    }

    // No implementation, to be implemented by each subclass
    public abstract List<Square> getLegalMoves(Board b);
}