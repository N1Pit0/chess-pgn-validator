package view;

import lombok.Getter;
import lombok.Setter;
import model.board.Square;
import model.pieces.common.Piece;

import javax.swing.*;
import java.awt.*;

import static services.enums.PieceColor.WHITE;

@Getter
@Setter
public class SquareView extends JComponent {
    private Square square;
    private boolean displayPiece;

    public SquareView(Square square) {
        this.square = square;
        this.displayPiece = true;

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.square.getColor().equals(WHITE)) {
            g.setColor(new Color(221, 192, 127));
        } else {
            g.setColor(new Color(101, 67, 33));
        }

        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        PieceView pieceView = new PieceView();

        if (this.square.getOccupyingPiece() != null && displayPiece) {
            pieceView.draw(g, (Piece) square.getOccupyingPiece());
        }
    }
}
