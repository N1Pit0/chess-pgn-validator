package model.board;

import model.enums.PieceColor;
import services.CheckmateDetector;
import view.gui.GameWindow;
import lombok.Getter;
import lombok.Setter;
import model.pieces.*;
import model.pieces.common.Piece;

import java.util.LinkedList;
import java.util.List;

import static model.enums.ImagePath.*;
import static model.enums.PieceColor.*;


@Getter
@Setter
public class Board {
    // Logical and graphical representations of board
    private final Square[][] board;
    private final GameWindow gameWindow;

    // List of pieces and whether they are movable
    private final LinkedList<Piece> blackPieces;
    private final LinkedList<Piece> whitePieces;

    private List<Square> movable;
    private boolean whiteTurn;
    private Piece currPiece;

    private int currX;
    private int currY;

    private CheckmateDetector ckeckmateDetector;

    public Board(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        board = new Square[8][8];
        blackPieces = new LinkedList<>();
        whitePieces = new LinkedList<>();

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

        King bk = new King(BLACK, board[0][4], RESOURCES_BKING_PNG.label);
        King wk = new King(WHITE, board[7][4], RESOURCES_WKING_PNG.label);
        board[0][4].put(bk);
        board[7][4].put(wk);

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

        ckeckmateDetector = new CheckmateDetector(this, whitePieces, blackPieces, wk, bk);
    }

    private void initializeBoardSquares() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                PieceColor color = ((x + y) % 2 == 0) ? WHITE : BLACK;
                board[y][x] = new Square( this, color, x, y);
            }
        }
    }

}