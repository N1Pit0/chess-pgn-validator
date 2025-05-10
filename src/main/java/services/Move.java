package services;

import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import model.pieces.common.Piece;

import static model.enums.PieceColor.BLACK;
import static model.enums.PieceColor.WHITE;

public class Move {
    private Piece piece;

    public Move(Piece piece) {
        this.piece = piece;
    }

    public boolean makeMove(Square fin, Board board) {
        Piece occupyingPiece = fin.getOccupyingPiece();
        Square currentSquare = piece.getCurrentSquare();

        if (occupyingPiece != null) {
            if (occupyingPiece.getColor() == piece.getColor()) return false;
            else capture(piece, currentSquare, board);
        }

        removePiece(currentSquare);
        currentSquare = fin;
        currentSquare.put(piece);
        return true;
    }

    private void removePiece(Square targetSquare) {
        targetSquare.setOccupyingPiece(null);
    }

    private void capture(Piece piece, Square targetSquare, Board board) {

        Piece targetPiece = targetSquare.getOccupyingPiece();
        PieceColor currentPieceColor = targetPiece.getColor();

        if (currentPieceColor.equals(BLACK)) board.getBlackPieces().remove(targetPiece);
        if (currentPieceColor.equals(WHITE)) board.getWhitePieces().remove(targetPiece);

        targetSquare.setOccupyingPiece(piece);
    }
}
