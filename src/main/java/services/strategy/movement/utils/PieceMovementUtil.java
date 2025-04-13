package services.strategy.movement.utils;

import chess.board.Square;
import model.common.Piece;

import java.util.LinkedList;
import java.util.List;

public class PieceMovementUtil {

    public static int[] getLinearOccupations(Square[][] board, int x, int y, Piece piece) {
        int lastYAbove = 0;
        int lastXRight = 7;
        int lastYBelow = 7;
        int lastXLeft = 0;

        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != piece.getColor()) {
                    lastYAbove = i;
                } else lastYAbove = i + 1;
            }

            if (board[7 - i][x].isOccupied()) {
                if (board[7 - i][x].getOccupyingPiece().getColor() != piece.getColor()) {
                    lastYBelow = 7 - i;
                } else {
                    lastYBelow = 7 - i - 1;
                }
            }
        }

//        for (int i = 7; i > y; i--) {
//            if (board[i][x].isOccupied()) {
//                if (board[i][x].getOccupyingPiece().getColor() != piece.getColor()) {
//                    lastYBelow = i;
//                } else lastYBelow = i - 1;
//            }
//        }

        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != piece.getColor()) {
                    lastXLeft = i;
                } else lastXLeft = i + 1;
            }

            if (board[y][7 - i].isOccupied()) {
                if (board[y][7 - i].getOccupyingPiece().getColor() != piece.getColor()) {
                    lastXRight = 7 - i;
                } else lastXRight = 7 - i - 1;
            }
        }

//        for (int i = 7; i > x; i--) {
//            if (board[y][i].isOccupied()) {
//                if (board[y][i].getOccupyingPiece().getColor() != piece.getColor()) {
//                    lastXRight = i;
//                } else lastXRight = i - 1;
//            }
//        }

        return new int[]{lastYAbove, lastYBelow, lastXLeft, lastXRight};
    }

    public static List<Square> getDiagonalOccupations(Square[][] board, int x, int y, Piece piece) {
        LinkedList<Square> diagOccupy = new LinkedList<>();

        int xNorthWest = x - 1;
        int xSouthWest = x - 1;
        int xNorthEast = x + 1;
        int xSouthEast = x + 1;
        int yNorthWest = y - 1;
        int ySouthWest = y + 1;
        int yNorthEast = y - 1;
        int ySouthEast = y + 1;

        while (xNorthWest >= 0 && yNorthWest >= 0) {
            if (board[yNorthWest][xNorthWest].isOccupied()) {
                if (board[yNorthWest][xNorthWest].getOccupyingPiece().getColor() != piece.getColor()) {
                    diagOccupy.add(board[yNorthWest][xNorthWest]);
                }
                break;
            } else {
                diagOccupy.add(board[yNorthWest][xNorthWest]);
                yNorthWest--;
                xNorthWest--;
            }
        }

        while (xSouthWest >= 0 && ySouthWest < 8) {
            if (board[ySouthWest][xSouthWest].isOccupied()) {
                if (board[ySouthWest][xSouthWest].getOccupyingPiece().getColor() != piece.getColor()) {
                    diagOccupy.add(board[ySouthWest][xSouthWest]);
                }
                break;
            } else {
                diagOccupy.add(board[ySouthWest][xSouthWest]);
                ySouthWest++;
                xSouthWest--;
            }
        }

        while (xSouthEast < 8 && ySouthEast < 8) {
            if (board[ySouthEast][xSouthEast].isOccupied()) {
                if (board[ySouthEast][xSouthEast].getOccupyingPiece().getColor() != piece.getColor()) {
                    diagOccupy.add(board[ySouthEast][xSouthEast]);
                }
                break;
            } else {
                diagOccupy.add(board[ySouthEast][xSouthEast]);
                ySouthEast++;
                xSouthEast++;
            }
        }

        while (xNorthEast < 8 && yNorthEast >= 0) {
            if (board[yNorthEast][xNorthEast].isOccupied()) {
                if (board[yNorthEast][xNorthEast].getOccupyingPiece().getColor() != piece.getColor()) {
                    diagOccupy.add(board[yNorthEast][xNorthEast]);
                }
                break;
            } else {
                diagOccupy.add(board[yNorthEast][xNorthEast]);
                yNorthEast--;
                xNorthEast++;
            }
        }

        return diagOccupy;
    }

    public boolean move(Square fin, Piece piece) {
        Piece occupied = fin.getOccupyingPiece();

        if (occupied != null) {
            if (occupied.getColor() == piece.getColor()) return false;
            else fin.capture(piece);
        }

        piece.getCurrentSquare().removePiece();
        piece.setCurrentSquare(fin);
        piece.getCurrentSquare().put(piece);
        return true;
    }


}
