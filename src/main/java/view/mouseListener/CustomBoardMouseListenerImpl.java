package view.mouseListener;

import services.board.BoardService;
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
        BoardService boardService = boardView.getBoardService();

        boardService.setCurrX(e.getX());
        boardService.setCurrY(e.getY());

        SquareView squareView = (SquareView) boardView.getComponentAt(new Point(e.getX(), e.getY()));
        SquareInterface square = squareView.getSquare();

        if (square.isOccupied()) {
            boardService.setCurrPiece(square.getOccupyingPiece());
            PieceColor currentPieceColor = boardService.getCurrPiece().getPieceColor();
            if (currentPieceColor.equals(BLACK) && boardService.isWhiteTurn())
                return;
            if (currentPieceColor.equals(WHITE) && !boardService.isWhiteTurn())
                return;
            squareView.setDisplayPiece(true);
        }
        boardView.repaint();
    }

    @Override
    public void handleMouseReleased(MouseEvent e) {
        SquareView squareView = (SquareView) boardView.getComponentAt(new Point(e.getX(), e.getY()));
        SquareInterface targetSquare = squareView.getSquare();
        BoardService boardService = boardView.getBoardService();
        PieceInterface currentPiece = boardService.getCurrPiece();

        if (boardService.getCurrPiece() == null) return;

        PieceColor currentPieceColor = boardService.getCurrPiece().getPieceColor();
        if (currentPieceColor.equals(BLACK) && boardService.isWhiteTurn())
            return;

        if (currentPieceColor.equals(WHITE) && !boardService.isWhiteTurn())
            return;

        List<SquareInterface> legalMoves = currentPiece.getLegalMoves(boardService.getBoardSquareArray());

        if (legalMoves.contains(targetSquare)) {
            // Store the original square of the current piece
            SquareInterface originalSquare = currentPiece.getCurrentSquare();

            // Store the captured piece (if any)
            PieceInterface capturedPiece = targetSquare.getOccupyingPiece();

            // Make the move
            currentPiece.move(targetSquare, boardService);

            // Check if the current player's king is in check after the move
            if (checkmateDetector.isInCheck(boardService, currentPieceColor)) {
                // Undo the move
                currentPiece.move(originalSquare, boardService);

                // Restore the captured piece (if any)
                if (capturedPiece != null) {
                    targetSquare.setOccupyingPiece(capturedPiece);
                    if (capturedPiece.getPieceColor() == WHITE) {
                        boardService.getWhitePieces().add(capturedPiece);
                    } else {
                        boardService.getBlackPieces().add(capturedPiece);
                    }
                }

                System.out.println("Invalid move. Your king is in check!");
            } else {

                PieceColor opponentColor = currentPieceColor.equals(WHITE) ? BLACK : WHITE;
                // Check if the opponent is in checkmate
                if (checkmateDetector.isInCheckmate(boardService, opponentColor)) {
                    System.out.println("Checkmate! You win!");
                    setupBoardForCheckmate(boardService, opponentColor);
                }
                // Check if the opponent is in stalemate
                else if (checkmateDetector.isInStalemate(boardService, opponentColor)) {
                    System.out.println("Stalemate! The game is a draw.");
                    // TODO: Handle the end of the game logic
                }
                // Change the turn to the other player
                boardService.setWhiteTurn(!boardService.isWhiteTurn());
            }
        }

        boardService.setCurrPiece(null);
        boardView.repaint();
    }

    @Override
    public void handleMouseDragged(MouseEvent e) {
        boardView.getBoardService().setCurrX(e.getX());
        boardView.getBoardService().setCurrY(e.getY());
        boardView.repaint();
    }

    private void setupBoardForCheckmate(BoardService boardService, PieceColor pieceColor) {
        boardService.setCurrPiece(null);
        boardView.repaint();
        boardView.removeMouseListener(boardMouseListener);
        boardView.removeMouseMotionListener(boardMouseMotionListener);
        boardService.getGameWindow().checkmateOccurred(pieceColor);
    }

}
