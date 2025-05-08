package view;

import model.board.*;
import lombok.Getter;
import model.enums.PieceColor;
import model.pieces.common.Piece;

import javax.swing.*;
import java.awt.*;

import static model.enums.PieceColor.*;

@Getter
public class BoardView extends JPanel {
    private final Board board;

    public BoardView(Board board) {
        this.board = board;

        setLayout(new GridLayout(8, 8, 0, 0));

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.add(new SquareView(this.board.getBoard()[x][y])); // ??
            }
        }

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Square[][] board = this.board.getBoard();
        Piece currPiece = this.board.getCurrPiece();
        boolean whiteTurn = this.board.isWhiteTurn();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[y][x];
                SquareView sqView = new SquareView(sq);
                sqView.setDisplayPiece(true);
                sqView.paintComponent(g);
            }
        }

        if (currPiece != null) {
            PieceColor pieceColor = currPiece.getColor();
            if ((pieceColor.equals(WHITE) && whiteTurn)
                    || (pieceColor.equals(BLACK) && !whiteTurn)) {
                final Image i = currPiece.getImage();
                g.drawImage(i, this.board.getCurrX(), this.board.getCurrY(), null);
            }
        }
    }
}
