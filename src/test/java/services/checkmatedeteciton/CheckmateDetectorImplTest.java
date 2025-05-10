package services.checkmatedeteciton;
import model.board.Board;
import model.board.Square;
import model.enums.PieceColor;
import model.pieces.King;
import model.pieces.common.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.checkmatedetection.CheckmateDetectorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckmateDetectorImplTest {

    private CheckmateDetectorImpl checkmateDetector;

    @Mock
    private Board board;

    @Mock
    private King whiteKing;

    @Mock
    private King blackKing;

    @Mock
    private Piece whitePiece;

    @Mock
    private Piece blackPiece;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checkmateDetector = new CheckmateDetectorImpl();
        when(blackKing.getColor()).thenReturn(PieceColor.BLACK);
        when(whiteKing.getColor()).thenReturn(PieceColor.WHITE);
    }

    @Test
    void isInCheck_WhiteKingInCheck_ReturnsTrue() {
        when(board.getWhiteKing()).thenReturn(Optional.of(whiteKing));
        when(board.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(board)).thenReturn(List.of(mock(Square.class)));
        when(whiteKing.getCurrentSquare()).thenReturn(mock(Square.class));

        boolean result = checkmateDetector.isInCheck(board, PieceColor.WHITE);

        assertTrue(result);
    }

    @Test
    void isInCheck_BlackKingNotInCheck_ReturnsFalse() {
        when(board.getBlackKing()).thenReturn(Optional.of(blackKing));
        when(board.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(board)).thenReturn(new ArrayList<>());

        boolean result = checkmateDetector.isInCheck(board, PieceColor.BLACK);

        assertFalse(result);
    }

    @Test
    void isInCheckmate_WhiteKingInCheckmatePosition_ReturnsTrue() {
        when(board.getWhiteKing()).thenReturn(Optional.of(whiteKing));
        when(board.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(board)).thenReturn(List.of(mock(Square.class)));
        when(whiteKing.getCurrentSquare()).thenReturn(mock(Square.class));
        when(board.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(board)).thenReturn(new ArrayList<>());

        boolean result = checkmateDetector.isInCheckmate(board, PieceColor.WHITE);

        assertTrue(result);
    }

    @Test
    void isInCheckmate_BlackKingNotInCheckmate_ReturnsFalse() {
        when(board.getBlackKing()).thenReturn(Optional.of(blackKing));
        when(board.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(board)).thenReturn(new ArrayList<>());
        when(board.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(board)).thenReturn(List.of(mock(Square.class)));

        boolean result = checkmateDetector.isInCheckmate(board, PieceColor.BLACK);

        assertFalse(result);
    }

    @Test
    void isInStalemate_WhiteKingInStalematePosition_ReturnsTrue() {
        when(board.getWhiteKing()).thenReturn(Optional.of(whiteKing));
        when(board.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(board)).thenReturn(new ArrayList<>());
        when(board.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(board)).thenReturn(new ArrayList<>());

        boolean result = checkmateDetector.isInStalemate(board, PieceColor.WHITE);

        assertTrue(result);
    }

    @Test
    void isInStalemate_BlackKingNotInStalemate_ReturnsFalse() {
        when(board.getBlackKing()).thenReturn(Optional.of(blackKing));
        when(board.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(board)).thenReturn(new ArrayList<>());
        when(board.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(board)).thenReturn(List.of(mock(Square.class)));

        boolean result = checkmateDetector.isInStalemate(board, PieceColor.BLACK);

        assertFalse(result);
    }
}
