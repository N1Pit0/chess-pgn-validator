package view;

import lombok.Getter;
import services.board.Board;
import services.board.SquareInterface;
import services.enums.PieceColor;
import services.strategy.common.PieceInterface;

import javax.swing.*;
import java.awt.*;

import static services.enums.PieceColor.BLACK;
import static services.enums.PieceColor.WHITE;

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
        SquareInterface[][] board = this.board.getBoard();
        PieceInterface currPiece = this.board.getCurrPiece();
        boolean whiteTurn = this.board.isWhiteTurn();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                SquareInterface sq = board[y][x];
                SquareView sqView = new SquareView(sq);
                sqView.setDisplayPiece(true);
                sqView.paintComponent(g);
            }
        }

        if (currPiece != null) {
            PieceColor pieceColor = currPiece.getPieceColor();
            if ((pieceColor.equals(WHITE) && whiteTurn)
                    || (pieceColor.equals(BLACK) && !whiteTurn)) {
                final Image i = currPiece.getImage();
                g.drawImage(i, this.board.getCurrX(), this.board.getCurrY(), null);
            }
        }
    }
}
