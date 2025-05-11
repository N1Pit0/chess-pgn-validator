package services.board;

import lombok.Getter;
import lombok.Setter;
import services.strategy.common.PieceInterface;
import view.gui.GameWindowImpl;

import java.util.List;
import java.util.Optional;


@Getter
@Setter
public class BoardService {
    // Logical and graphical representations of board
    private final GameWindowImpl gameWindow;
    private final BoardInterface boardInterface;

    private boolean whiteTurn;
    private PieceInterface currPiece;

    private int currX;
    private int currY;


    public BoardService(GameWindowImpl gameWindow, BoardInterface boardInterface) {
        this.gameWindow = gameWindow;
        this.boardInterface = boardInterface;

        initializeBoardSquares();

        initializePieces();

        whiteTurn = true;

    }

    private void initializePieces() {
        boardInterface.initializePieces();
    }

    public void initializeBoardSquares() {
        boardInterface.initializeBoardSquares();
    }

    public Optional<PieceInterface> getWhiteKing() {
        return boardInterface.getWhiteKing();
    }

    public void setWhiteKing(PieceInterface piece) {
        boardInterface.setWhiteKing(piece);
    }

    public Optional<PieceInterface> getBlackKing() {
        return boardInterface.getBlackKing();
    }

    public void setBlackKing(PieceInterface piece) {
        boardInterface.setBlackKing(piece);
    }

    public List<PieceInterface> getWhitePieces() {
        return boardInterface.getWhitePieces();
    }

    public void setWhitePieces(List<PieceInterface> pieces) {
        boardInterface.setWhitePieces(pieces);
    }

    public List<PieceInterface> getBlackPieces() {
        return boardInterface.getBlackPieces();
    }

    public void setBlackPieces(List<PieceInterface> pieces) {
        boardInterface.setBlackPieces(pieces);
    }

    public SquareInterface[][] getBoardSquareArray() {
        return boardInterface.getBoardSquareArray();
    }


}