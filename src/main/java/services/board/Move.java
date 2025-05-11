package services.board;

import model.board.Square;
import model.enums.PieceColor;
import model.pieces.King;
import model.pieces.common.Piece;

import static model.enums.PieceColor.BLACK;
import static model.enums.PieceColor.WHITE;

public interface Move {

    static boolean makeMove(Piece currentPiece, Square targetSquare, Board board) {
        Piece occupyingPiece = targetSquare.getOccupyingPiece();

        if (occupyingPiece != null) {
            if (occupyingPiece.getColor() == currentPiece.getColor()) return false;
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

    private static void capture(Piece piece, Square targetSquare, Board board) {

        Piece targetPiece = targetSquare.getOccupyingPiece();
        PieceColor targetPieceColor = targetPiece.getColor();

        if (targetPieceColor.equals(BLACK)) {
            if (targetPiece instanceof King) board.setBlackKing(null);
            board.getBlackPieces().remove(targetPiece);
        }
        if (targetPieceColor.equals(WHITE)) {
            if (targetPiece instanceof King) board.setWhiteKing(null);
            board.getWhitePieces().remove(targetPiece);
        }

        targetSquare.setOccupyingPiece(piece);
    }
}
