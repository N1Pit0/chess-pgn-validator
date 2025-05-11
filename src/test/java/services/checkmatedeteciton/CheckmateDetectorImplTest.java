package services.checkmatedeteciton;
import services.board.BoardService;
import model.board.Square;
import services.enums.PieceColor;
import model.pieces.King;
import model.pieces.common.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.checkmatedetection.CheckmateDetectorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckmateDetectorImplTest {


    @Mock
    private BoardService boardService;

    @Mock
    private King whiteKing;

    @Mock
    private King blackKing;

    @Mock
    private Piece whitePiece;

    @Mock
    private Piece blackPiece;

    @InjectMocks
    private CheckmateDetectorImpl checkmateDetector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checkmateDetector = new CheckmateDetectorImpl();
        when(blackKing.getPieceColor()).thenReturn(PieceColor.BLACK);
        when(whiteKing.getPieceColor()).thenReturn(PieceColor.WHITE);
    }

    @Test
    void isInCheck_WhiteKingInCheck_ReturnsTrue() {
        when(boardService.getWhiteKing()).thenReturn(Optional.of(whiteKing));
        when(boardService.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(boardService)).thenReturn(List.of(mock(Square.class)));
        when(whiteKing.getCurrentSquare()).thenReturn(mock(Square.class));

        boolean result = checkmateDetector.isInCheck(boardService, PieceColor.WHITE);

        assertTrue(result);
    }

    @Test
    void isInCheck_BlackKingNotInCheck_ReturnsFalse() {
        when(boardService.getBlackKing()).thenReturn(Optional.of(blackKing));
        when(boardService.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(boardService)).thenReturn(new ArrayList<>());

        boolean result = checkmateDetector.isInCheck(boardService, PieceColor.BLACK);

        assertFalse(result);
    }

    @Test
    void isInCheckmate_WhiteKingInCheckmatePosition_ReturnsTrue() {
        when(boardService.getWhiteKing()).thenReturn(Optional.of(whiteKing));
        when(boardService.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(boardService)).thenReturn(List.of(mock(Square.class)));
        when(whiteKing.getCurrentSquare()).thenReturn(mock(Square.class));
        when(boardService.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(boardService)).thenReturn(new ArrayList<>());

        boolean result = checkmateDetector.isInCheckmate(boardService, PieceColor.WHITE);

        assertTrue(result);
    }

    @Test
    void isInCheckmate_BlackKingNotInCheckmate_ReturnsFalse() {
        when(boardService.getBlackKing()).thenReturn(Optional.of(blackKing));
        when(boardService.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(boardService)).thenReturn(new ArrayList<>());
        when(boardService.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(boardService)).thenReturn(List.of(mock(Square.class)));

        boolean result = checkmateDetector.isInCheckmate(boardService, PieceColor.BLACK);

        assertFalse(result);
    }

    @Test
    void isInStalemate_WhiteKingInStalematePosition_ReturnsTrue() {
        when(boardService.getWhiteKing()).thenReturn(Optional.of(whiteKing));
        when(boardService.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(boardService)).thenReturn(new ArrayList<>());
        when(boardService.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(boardService)).thenReturn(new ArrayList<>());

        boolean result = checkmateDetector.isInStalemate(boardService, PieceColor.WHITE);

        assertTrue(result);
    }

    @Test
    void isInStalemate_BlackKingNotInStalemate_ReturnsFalse() {
        when(boardService.getBlackKing()).thenReturn(Optional.of(blackKing));
        when(boardService.getWhitePieces()).thenReturn(List.of(whitePiece));
        when(whitePiece.getLegalMoves(boardService)).thenReturn(new ArrayList<>());
        when(boardService.getBlackPieces()).thenReturn(List.of(blackPiece));
        when(blackPiece.getLegalMoves(boardService)).thenReturn(List.of(mock(Square.class)));

        boolean result = checkmateDetector.isInStalemate(boardService, PieceColor.BLACK);

        assertFalse(result);
    }
}
