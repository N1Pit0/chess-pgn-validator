package services.board;

import lombok.Getter;
import lombok.Setter;
import model.board.Square;
import services.enums.PieceColor;
import model.pieces.*;
import services.strategy.common.PieceInterface;
import view.gui.GameWindowImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static services.enums.ImagePath.*;
import static services.enums.PieceColor.BLACK;
import static services.enums.PieceColor.WHITE;


@Getter
@Setter
public class Board {
    // Logical and graphical representations of board
    private final Square[][] board;
    private final GameWindowImpl gameWindow;

    // List of pieces and whether they are movable
    private List<PieceInterface> blackPieces;
    private List<PieceInterface> whitePieces;

    private King whiteKing;
    private King blackKing;

    private List<Square> movable;
    private boolean whiteTurn;
    private PieceInterface currPiece;

    private int currX;
    private int currY;


    public Board(GameWindowImpl gameWindow) {
        this.gameWindow = gameWindow;
        board = new Square[8][8];
        blackPieces = new ArrayList<>();
        whitePieces = new ArrayList<>();

        initializeBoardSquares();

        initializePieces();

        whiteTurn = true;

    }

    private void initializePieces() {

        for (int x = 0; x < 8; x++) {
            board[1][x].put(new Pawn(BLACK, board[1][x], RESOURCES_BPAWN_PNG.label));
            board[6][x].put(new Pawn(WHITE, board[6][x], RESOURCES_WPAWN_PNG.label));
        }

        board[7][3].put(new Queen(WHITE, board[7][3], RESOURCES_WQUEEN_PNG.label));
        board[0][3].put(new Queen(BLACK, board[0][3], RESOURCES_BQUEEN_PNG.label));

        this.blackKing = new King(BLACK, board[0][4], RESOURCES_BKING_PNG.label);
        this.whiteKing = new King(WHITE, board[7][4], RESOURCES_WKING_PNG.label);
        board[0][4].put(blackKing);
        board[7][4].put(whiteKing);

        board[0][0].put(new Rook(BLACK, board[0][0], RESOURCES_BROOK_PNG.label));
        board[0][7].put(new Rook(BLACK, board[0][7], RESOURCES_BROOK_PNG.label));
        board[7][0].put(new Rook(WHITE, board[7][0], RESOURCES_WROOK_PNG.label));
        board[7][7].put(new Rook(WHITE, board[7][7], RESOURCES_WROOK_PNG.label));

        board[0][1].put(new Knight(BLACK, board[0][1], RESOURCES_BKNIGHT_PNG.label));
        board[0][6].put(new Knight(BLACK, board[0][6], RESOURCES_BKNIGHT_PNG.label));
        board[7][1].put(new Knight(WHITE, board[7][1], RESOURCES_WKNIGHT_PNG.label));
        board[7][6].put(new Knight(WHITE, board[7][6], RESOURCES_WKNIGHT_PNG.label));

        board[0][2].put(new Bishop(BLACK, board[0][2], RESOURCES_BBISHOP_PNG.label));
        board[0][5].put(new Bishop(BLACK, board[0][5], RESOURCES_BBISHOP_PNG.label));
        board[7][2].put(new Bishop(WHITE, board[7][2], RESOURCES_WBISHOP_PNG.label));
        board[7][5].put(new Bishop(WHITE, board[7][5], RESOURCES_WBISHOP_PNG.label));


        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                blackPieces.add(board[y][x].getOccupyingPiece());
                whitePieces.add(board[7 - y][x].getOccupyingPiece());
            }
        }

    }

    private void initializeBoardSquares() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                PieceColor color = ((x + y) % 2 == 0) ? WHITE : BLACK;
                board[y][x] = new Square(this, color, x, y);
            }
        }
    }

    public Optional<PieceInterface> getWhiteKing() {
        return whitePieces.stream().filter(piece -> piece instanceof King).findFirst();
    }

    public Optional<PieceInterface> getBlackKing() {
        return blackPieces.stream().filter(piece -> piece instanceof King).findFirst();
    }

}