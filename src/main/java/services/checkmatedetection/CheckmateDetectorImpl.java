package services.checkmatedetection;

import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import model.pieces.King;
import model.pieces.common.Piece;

import java.util.List;
import java.util.Optional;

import static model.enums.PieceColor.WHITE;

public class CheckmateDetectorImpl implements CheckmateDetector{

    @Override
    public boolean isInCheck(Board board) {

        return board.isWhiteTurn()
                ? checkHelper(board, PieceColor.WHITE)
                : checkHelper(board, PieceColor.BLACK);
    }

    @Override
    public boolean isInCheckmate(Board board) {
        if (!isInCheck(board)) {
            return false;
        }

        return !hasLegalMoveWithoutCheck(board);
    }

    @Override
    public boolean isInStalemate(Board board) {
        if (isInCheck(board)) {
            return false;
        }

        return !hasLegalMoveWithoutCheck(board);
    }

    private boolean hasLegalMoveWithoutCheck(Board board) {
        List<Piece> currentPlayerPieces = board.isWhiteTurn() ? board.getWhitePieces() : board.getBlackPieces();

        return currentPlayerPieces.stream()
                .flatMap(piece -> piece.getLegalMoves(board).stream().map(move -> {
                    Square originalSquare = piece.getCurrentSquare();
                    Piece capturedPiece = move.getOccupyingPiece();

                    // Make the move
                    piece.move(move);

                    // Check if the king is in check after the move
                    boolean isInCheckAfterMove = isInCheck(board);

                    // Undo the move
                    piece.move(originalSquare);
                    if (capturedPiece != null) {
                        move.setOccupyingPiece(capturedPiece);
                        if (capturedPiece.getColor() == WHITE) {
                            board.getWhitePieces().add(capturedPiece);
                        } else {
                            board.getBlackPieces().add(capturedPiece);
                        }
                    }

                    return !isInCheckAfterMove;
                }))
                .anyMatch(canGetOutOfCheck -> canGetOutOfCheck);
    }

    private boolean checkHelper(Board board, PieceColor pieceColor){
        Optional<Piece> optionalKing = pieceColor.equals(PieceColor.WHITE) ? board.getWhiteKing() : board.getBlackKing();

        if (optionalKing.isEmpty()) { return false; }

        King king = (King) optionalKing.get();

        List<Piece> opponentPieces = king
                .getColor()
                .equals(PieceColor.WHITE) ? board.getBlackPieces() : board.getWhitePieces();

        return opponentPieces.stream().anyMatch(piece ->
                piece.getLegalMoves(board).contains(king.getCurrentSquare()));
    }
}
