package services.parser.tochessmoveparser;

import services.enums.ChessPiece;

public class DummyChessMoveImpl implements ChessMove {
    @Override
    public ChessPiece getPiece() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFrom() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPosition() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCapture() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPromotion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChessPiece getPromotedPiece() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCheck() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCheckmate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChessMove dummyFromText(String moveText) {
        //TODO extract moves form text. It should return new class based on what type of move it is

        return this;
    }
}
