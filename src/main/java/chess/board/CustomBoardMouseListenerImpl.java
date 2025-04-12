package chess.board;

import chess.board.mousehandler.BoardMouseListener;
import chess.board.mousehandler.BoardMouseMotionListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class CustomBoardMouseListenerImpl implements CustomBoardMouseListener {

    private final Board board;
    private final BoardMouseListener boardMouseListener;
    private final BoardMouseMotionListener boardMouseMotionListener;

    public CustomBoardMouseListenerImpl(Board board) {

        this.board = board;
        this.boardMouseListener = new BoardMouseListener(this);
        this.boardMouseMotionListener = new BoardMouseMotionListener(this);
        this.board.addMouseListener(boardMouseListener);
        this.board.addMouseMotionListener(boardMouseMotionListener);
    }

    @Override
    public void handleMousePressed(MouseEvent e) {
        board.setCurrX(e.getX());
        board.setCurrY(e.getY());

        Square square = (Square) board.getComponentAt(new Point(e.getX(), e.getY()));

        if (square.isOccupied()) {
            board.setCurrPiece(square.getOccupyingPiece());
            if (board.getCurrPiece().getColor() == 0 && board.isWhiteTurn())
                return;
            if (board.getCurrPiece().getColor() == 1 && !board.isWhiteTurn())
                return;
            square.setDisplay(false);
        }
        board.repaint();
    }

    @Override
    public void handleMouseReleased(MouseEvent e) {
        Square sq = (Square) board.getComponentAt(new Point(e.getX(), e.getY()));

        if (board.getCurrPiece() == null) return;

        if (board.getCurrPiece().getColor() == 0 && board.isWhiteTurn())
            return;

        if (board.getCurrPiece().getColor() == 1 && !board.isWhiteTurn())
            return;

        List<Square> legalMoves = board.getCurrPiece().getLegalMoves(board);

        List<Square> movableSquares = board.getCmd().getAllowableSquares(board.isWhiteTurn());
        board.setMovable(movableSquares);

        if (legalMoves.contains(sq) && board.getMovable().contains(sq)
                && board.getCmd().testMove(board.getCurrPiece(), sq)) {
            sq.setDisplay(true);
            board.getCurrPiece().move(sq);
            board.getCmd().update();

            if (board.getCmd().blackCheckMated()) {

                setupBoardForCheckmate(board, 0);

            } else if (board.getCmd().blackCheckMated()) {

                setupBoardForCheckmate(board, 1);

            } else {
                board.setCurrPiece(null);

                board.setWhiteTurn(!board.isWhiteTurn());

                board.setMovable(board.getCmd().getAllowableSquares(board.isWhiteTurn()));
            }

        } else {
            board.getCurrPiece().getPosition().setDisplay(true);
            board.setCurrPiece(null);
        }

        board.repaint();
    }

    @Override
    public void handleMouseDragged(MouseEvent e) {
        board.setCurrX(e.getX() - 24);
        board.setCurrY(e.getY() - 24);

        board.repaint();
    }

    private void setupBoardForCheckmate(Board board, int colorCheckMated) {
        board.setCurrPiece(null);
        board.repaint();
        board.removeMouseListener(boardMouseListener);
        board.removeMouseMotionListener(boardMouseMotionListener);
        board.getGameWindow().checkmateOccurred(colorCheckMated);
    }
}
