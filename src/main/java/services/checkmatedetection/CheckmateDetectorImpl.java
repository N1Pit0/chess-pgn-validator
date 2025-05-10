package services.checkmatedetection;

import model.board.Board;
import model.enums.PieceColor;
import model.pieces.King;
import model.pieces.common.Piece;

import java.util.List;
import java.util.Optional;

public class CheckmateDetectorImpl implements CheckmateDetector{

    @Override
    public boolean isInCheck(Board board) {

        return board.isWhiteTurn()
                ? checkHelper(board, PieceColor.WHITE)
                : checkHelper(board, PieceColor.BLACK);
    }

    @Override
    public boolean isInCheckmate(Board board) {
        return false; //not implemented
    }

    @Override
    public boolean isInStalemate(Board board) {
        return false; //not implemented
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
