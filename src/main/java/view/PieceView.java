package view;

import model.pieces.common.Piece;

import java.awt.*;

public class PieceView {

    public void draw(Graphics g, Piece piece) {


        g.drawImage(piece.getImage(), 0, 0, null);
    }
}
