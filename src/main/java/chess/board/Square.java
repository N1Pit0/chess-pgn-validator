package chess.board;

import model.common.Piece;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"board", "color", "occupyingPiece", "displayPiece"}, callSuper = false)
public class Square extends JComponent {
    private Board board;
    
    private final int color;
    private Piece occupyingPiece;
    private boolean displayPiece;
    
    private int xNum;
    private int yNum;
    
    public Square(Board board, int c, int xNum, int yNum) {
        
        this.board = board;
        this.color = c;
        this.displayPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;

        this.setBorder(BorderFactory.createEmptyBorder());
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
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (this.color == 1) {
            g.setColor(new Color(221,192,127));
        } else {
            g.setColor(new Color(101,67,33));
        }
        
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        if(occupyingPiece != null && displayPiece) {
            occupyingPiece.draw(g);
        }
    }

}
