package services;

import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import model.pieces.King;
import model.pieces.common.Piece;

import static model.enums.PieceColor.*;

public class Move {
    private Piece piece;

    public Move(Piece piece) {
        this.piece = piece;
    }

    public boolean makeMove(Square fin, Board board) {
        Piece occupyingPiece = fin.getOccupyingPiece();

        if (occupyingPiece != null) {
            if (occupyingPiece.getColor() == piece.getColor()) return false;
            else capture(piece, fin, board);
        }

        removePiece(piece.getCurrentSquare());
        piece.setCurrentSquare(fin);
        piece.getCurrentSquare().put(piece);
        return true;
    }

    private void removePiece(Square targetSquare) {
        targetSquare.setOccupyingPiece(null);
    }

    private void capture(Piece piece, Square targetSquare, Board board) {

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
