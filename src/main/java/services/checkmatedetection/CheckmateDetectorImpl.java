package services.checkmatedetection;

import model.board.Square;
import services.enums.PieceColor;
import services.board.Board;
import services.strategy.common.PieceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static services.enums.PieceColor.WHITE;

public class CheckmateDetectorImpl implements CheckmateDetector {

    @Override
    public boolean isInCheck(Board board, PieceColor pieceColor) {

        return pieceColor.equals(WHITE)
                ? checkHelper(board, PieceColor.WHITE)
                : checkHelper(board, PieceColor.BLACK);
    }

    @Override
    public boolean isInCheckmate(Board board, PieceColor pieceColor) {
        if (!isInCheck(board, pieceColor)) {
            return false;
        }

        return !hasLegalMoveWithoutCheck(board, pieceColor);
    }

    @Override
    public boolean isInStalemate(Board board, PieceColor pieceColor) {
        if (isInCheck(board, pieceColor)) {
            return false;
        }

        return !hasLegalMoveWithoutCheck(board, pieceColor);
    }

    private boolean hasLegalMoveWithoutCheck(Board board, PieceColor pieceColor) {
        List<PieceInterface> currentPlayerPieces = pieceColor.equals(WHITE) ? board.getWhitePieces() : board.getBlackPieces();

        return currentPlayerPieces.stream()
                .flatMap(piece -> piece.getLegalMoves(board).stream().map(targetSquare -> {
                    Square originalSquare = piece.getCurrentSquare();
                    PieceInterface capturedPiece = targetSquare.getOccupyingPiece();

                    // Make the move
                    piece.move(targetSquare);

                    // Check if the king is in check after the move
                    boolean isInCheckAfterMove = isInCheck(board, pieceColor);

                    // Undo the move
                    piece.move(originalSquare);
                    if (capturedPiece != null) {
                        targetSquare.setOccupyingPiece(capturedPiece);
                        if (capturedPiece.getPieceColor() == WHITE) {
                            List<PieceInterface> updatedWhitePieces = new ArrayList<>(board.getWhitePieces());
                            updatedWhitePieces.add(capturedPiece);
                            board.setWhitePieces(updatedWhitePieces);
                        } else {
                            List<PieceInterface> updatedBlackPieces = new ArrayList<>(board.getBlackPieces());
                            updatedBlackPieces.add(capturedPiece);
                            board.setBlackPieces(updatedBlackPieces);
                        }
                    }

                    return !isInCheckAfterMove;
                }))
                .anyMatch(canGetOutOfCheck -> canGetOutOfCheck);
    }

    private boolean checkHelper(Board board, PieceColor pieceColor) {
        Optional<PieceInterface> optionalKing = pieceColor.equals(PieceColor.WHITE) ? board.getWhiteKing() : board.getBlackKing();

        if (optionalKing.isEmpty()) {
            return false;
        }

        PieceInterface king = optionalKing.get();

        List<PieceInterface> opponentPieces = king
                .getPieceColor()
                .equals(PieceColor.WHITE) ? board.getBlackPieces() : board.getWhitePieces();

        return opponentPieces.stream().anyMatch(piece ->
                piece.getLegalMoves(board).contains(king.getCurrentSquare()));
    }
}
