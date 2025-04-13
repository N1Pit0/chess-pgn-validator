package view;

import chess.board.Square;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

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

        if (this.square.getColor() == 1) {
            g.setColor(new Color(221, 192, 127));
        } else {
            g.setColor(new Color(101, 67, 33));
        }

        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (this.square.getOccupyingPiece() != null && displayPiece) {
            this.square.getOccupyingPiece().draw(g);
        }
    }
}
