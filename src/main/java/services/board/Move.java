package services.board;

import model.board.Square;
import services.enums.PieceColor;
import model.pieces.King;
import model.pieces.common.Piece;
import services.strategy.common.PieceInterface;

import static services.enums.PieceColor.BLACK;
import static services.enums.PieceColor.WHITE;

public interface Move {

    static boolean makeMove(PieceInterface currentPiece, Square targetSquare, Board board) {
        PieceInterface occupyingPiece = targetSquare.getOccupyingPiece();

        if (occupyingPiece != null) {
            if (occupyingPiece.getPieceColor() == currentPiece.getPieceColor()) return false;
            else capture(currentPiece, targetSquare, board);
        }

        removePiece(currentPiece.getCurrentSquare());
        currentPiece.setCurrentSquare(targetSquare);
        currentPiece.getCurrentSquare().put(currentPiece);
        return true;
    }

    private static void removePiece(Square targetSquare) {
        targetSquare.setOccupyingPiece(null);
    }

    private static void capture(PieceInterface piece, Square targetSquare, Board board) {

        PieceInterface targetPiece = targetSquare.getOccupyingPiece();
        PieceColor targetPieceColor = targetPiece.getPieceColor();

        if (targetPieceColor.equals(BLACK)) {
            if (targetPiece instanceof King) board.setBlackKing(null);
            board.getBlackPieces().remove(targetPiece);
        }
        if (targetPieceColor.equals(WHITE)) {
            if (targetPiece instanceof King) board.setWhiteKing(null);
            board.getWhitePieces().remove(targetPiece);
        }

        targetSquare.setOccupyingPiece((Piece) piece);
    }
}
