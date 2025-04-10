package chess.board.mousehandler;

import chess.board.CustomBoardMouseListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardMouseHandler extends MouseAdapter {

    private final CustomBoardMouseListener mouseListener;

    public BoardMouseHandler(CustomBoardMouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseListener.handleMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseListener.handleMouseReleased(e);
    }
}
