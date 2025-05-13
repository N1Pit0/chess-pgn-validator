package services.parser.tochessmoveparser;

import services.enums.ChessPiece;

public interface ChessMove {
    ChessPiece getPiece();

    String getFrom();

    String getPosition();

    boolean isCapture();

    boolean isPromotion();

    ChessPiece getPromotedPiece();

    boolean isCheck();

    boolean isCheckmate();

    ChessMove dummyFromText(String moveText);
}