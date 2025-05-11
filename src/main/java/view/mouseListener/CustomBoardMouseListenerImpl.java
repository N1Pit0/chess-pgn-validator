package view.mouseListener;

import services.board.Board;
import services.board.SquareInterface;
import services.checkmatedetection.CheckmateDetector;
import services.enums.PieceColor;
import services.strategy.common.PieceInterface;
import view.BoardView;
import view.SquareView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

import static services.enums.PieceColor.BLACK;
import static services.enums.PieceColor.WHITE;

public class CustomBoardMouseListenerImpl implements CustomBoardMouseListener {

    private final BoardView boardView;
    private final BoardMouseListener boardMouseListener;
    private final BoardMouseMotionListener boardMouseMotionListener;
    private final CheckmateDetector checkmateDetector;

    public CustomBoardMouseListenerImpl(BoardView boardView, CheckmateDetector checkmateDetector) {
        this.checkmateDetector = checkmateDetector;
        this.boardView = boardView;
        this.boardMouseListener = new BoardMouseListener(this);
        this.boardMouseMotionListener = new BoardMouseMotionListener(this);
        this.boardView.addMouseListener(boardMouseListener);
        this.boardView.addMouseMotionListener(boardMouseMotionListener);
    }

    @Override
    public void handleMousePressed(MouseEvent e) {
        Board board = boardView.getBoard();

        board.setCurrX(e.getX());
        board.setCurrY(e.getY());

        SquareView squareView = (SquareView) boardView.getComponentAt(new Point(e.getX(), e.getY()));
        SquareInterface square = squareView.getSquare();

        if (square.isOccupied()) {
            board.setCurrPiece(square.getOccupyingPiece());
            PieceColor currentPieceColor = board.getCurrPiece().getPieceColor();
            if (currentPieceColor.equals(BLACK) && board.isWhiteTurn())
                return;
            if (currentPieceColor.equals(WHITE) && !board.isWhiteTurn())
                return;
            squareView.setDisplayPiece(true);
        }
        boardView.repaint();
    }

    @Override
    public void handleMouseReleased(MouseEvent e) {
        SquareView squareView = (SquareView) boardView.getComponentAt(new Point(e.getX(), e.getY()));
        SquareInterface targetSquare = squareView.getSquare();
        Board board = boardView.getBoard();
        PieceInterface currentPiece = board.getCurrPiece();

        if (board.getCurrPiece() == null) return;

        PieceColor currentPieceColor = board.getCurrPiece().getPieceColor();
        if (currentPieceColor.equals(BLACK) && board.isWhiteTurn())
            return;

        if (currentPieceColor.equals(WHITE) && !board.isWhiteTurn())
            return;

        List<SquareInterface> legalMoves = currentPiece.getLegalMoves(board);

        if (legalMoves.contains(targetSquare)) {
            // Store the original square of the current piece
            SquareInterface originalSquare = currentPiece.getCurrentSquare();

            // Store the captured piece (if any)
            PieceInterface capturedPiece = targetSquare.getOccupyingPiece();

            // Make the move
            currentPiece.move(targetSquare);

            // Check if the current player's king is in check after the move
            if (checkmateDetector.isInCheck(board, currentPieceColor)) {
                // Undo the move
                currentPiece.move(originalSquare);

                // Restore the captured piece (if any)
                if (capturedPiece != null) {
                    targetSquare.setOccupyingPiece(capturedPiece);
                    if (capturedPiece.getPieceColor() == WHITE) {
                        board.getWhitePieces().add(capturedPiece);
                    } else {
                        board.getBlackPieces().add(capturedPiece);
                    }
                }

                System.out.println("Invalid move. Your king is in check!");
            } else {

                PieceColor opponentColor = currentPieceColor.equals(WHITE) ? BLACK : WHITE;
                // Check if the opponent is in checkmate
                if (checkmateDetector.isInCheckmate(board, opponentColor)) {
                    System.out.println("Checkmate! You win!");
                    setupBoardForCheckmate(board, opponentColor);
                }
                // Check if the opponent is in stalemate
                else if (checkmateDetector.isInStalemate(board, opponentColor)) {
                    System.out.println("Stalemate! The game is a draw.");
                    // TODO: Handle the end of the game logic
                }
                // Change the turn to the other player
                board.setWhiteTurn(!board.isWhiteTurn());
            }
        }

        board.setCurrPiece(null);
        boardView.repaint();
    }

    @Override
    public void handleMouseDragged(MouseEvent e) {
        boardView.getBoard().setCurrX(e.getX());
        boardView.getBoard().setCurrY(e.getY());
        boardView.repaint();
    }

    private void setupBoardForCheckmate(Board board, PieceColor pieceColor) {
        board.setCurrPiece(null);
        boardView.repaint();
        boardView.removeMouseListener(boardMouseListener);
        boardView.removeMouseMotionListener(boardMouseMotionListener);
        board.getGameWindow().checkmateOccurred(pieceColor);
    }

}
