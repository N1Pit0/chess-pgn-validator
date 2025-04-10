package chess.board.mousehandler;

import chess.board.CustomBoardMouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BoardMouseMotionHandler extends MouseMotionAdapter {

    private final CustomBoardMouseListener mouseListener;

    public BoardMouseMotionHandler(CustomBoardMouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseListener.handleMouseDragged(e);
    }

}
