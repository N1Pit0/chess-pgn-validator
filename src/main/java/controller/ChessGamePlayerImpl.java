package controller;

import services.board.BoardService;
import services.board.SquareInterface;
import services.checkmatedetection.CheckmateDetector;
import services.dtos.MoveDto;
import services.enums.PieceColor;
import services.parser.tochessmoveparser.ChessMove;
import services.parser.tochessmoveparser.DummyChessMoveImpl;
import services.strategy.common.PieceInterface;
import services.utils.exceptions.*;

import java.util.List;

public class ChessGamePlayerImpl implements ChessGamePlayer {
    private final BoardService boardService;
    private final CheckmateDetector checkmateDetector;

    public ChessGamePlayerImpl(BoardService boardService, CheckmateDetector checkmateDetector) {
        this.boardService = boardService;
        this.checkmateDetector = checkmateDetector;
    }

    public void replayGame(List<MoveDto> moveDtos) {
        for (MoveDto moveDto : moveDtos) {
            String whiteMove = moveDto.getWhiteMove();
            String blackMove = moveDto.getBlackMove();

            // Parse and execute white move
            executeMove(whiteMove, PieceColor.WHITE);

            // Parse and execute black move
            executeMove(blackMove, PieceColor.BLACK);
        }
    }

    private void executeMove(String moveText, PieceColor pieceColor) {
        PieceColor opposite = pieceColor.equals(PieceColor.WHITE) ? PieceColor.WHITE : PieceColor.BLACK;

        if (moveText.isEmpty()) {
            return;
        }

        if (moveText.equals("1-0") || moveText.equals("0-1") || moveText.equals("1/2-1/2")) {
            // End move notation, skip execution
            return;
        }

        try {
            ChessMove moveClass = new DummyChessMoveImpl();

            ChessMove move = moveClass.dummyFromText(moveText);

            SquareInterface sourceSquare = parseSourceSquare(move, boardService.getBoardSquareArray());
            SquareInterface destinationSquare = parseDestinationSquare(move, boardService.getBoardSquareArray());

            if (sourceSquare == null || destinationSquare == null) {
                throw new InvalidMoveException("Invalid move: " + moveText);
            }

            PieceInterface piece = sourceSquare.getOccupyingPiece();

            if (piece == null || piece.getPieceColor() != pieceColor) {
                throw new InvalidMoveException("Invalid move: " + moveText);
            }

            List<SquareInterface> legalMoves = piece.getLegalMoves(boardService.getBoardSquareArray());

            if (!legalMoves.contains(destinationSquare)) {
                throw new IllegalMoveException("Illegal move: " + moveText);
            }

            if (move.getPosition().equals("O-O") || move.getPosition().equals("O-O-O")) {
                throw new SpecialMoveNotImplementedException("Castling is not implemented yet");
            }

            if (move.isPromotion()) {
                throw new SpecialMoveNotImplementedException("Pawn promotion is not implemented yet");
            }

            // TODO: Check for en passant and throw exception if not implemented

            piece.move(destinationSquare, boardService);

            if (move.isCheck()) {
                if (!checkmateDetector.isInCheck(boardService, opposite)) {
                    throw new InvalidCheckNotationException("Invalid check notation: " + moveText);
                }
            }

            if (move.isCheckmate()) {
                if (!checkmateDetector.isInCheckmate(boardService, opposite)) {
                    throw new InvalidCheckmateNotationException("Invalid checkmate notation: " + moveText);
                }
            }

            // TODO: needs fix: stalemate occurs when its draw i.e 1/2-1/2
            if (checkmateDetector.isInStalemate(boardService, opposite)) {
                throw new StalemateException("Stalemate detected after move: " + moveText);
            }
        } catch (Exception e) {
            throw new InvalidMoveException("Invalid move: " + moveText);
        }
    }

    private SquareInterface parseSourceSquare(ChessMove move, SquareInterface[][] boardSquares) {
        String from = move.getFrom();
        if (from == null) {
            // TODO: Implement logic to determine the source square based on the piece and destination square
            return null;
        }
        int fileIndex = from.charAt(0) - 'a';
        int rankIndex = 8 - Integer.parseInt(from.substring(1));
        return boardSquares[rankIndex][fileIndex];
    }

    private SquareInterface parseDestinationSquare(ChessMove move, SquareInterface[][] boardSquares) {
        String position = move.getPosition();
        if (position == null) {
            return null;
        }
        int fileIndex = position.charAt(0) - 'a';
        int rankIndex = 8 - Integer.parseInt(position.substring(1));
        return boardSquares[rankIndex][fileIndex];
    }
}