package chess.movegen;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import chess.board.Board;
import chess.model.Move;
import chess.model.Position;

@Component
public class MoveGenerator {

    public List<Move> genMove(Position pos, Board board) {
        String piece = board.getPiece(pos.row, pos.col);
        char type = piece.charAt(1);

        return switch (type) {
            case 'K' ->
                genKingMoves(pos, board);
            case 'Q' ->
                genQueenMoves(pos, board);
            case 'R' ->
                genRookMoves(pos, board);
            case 'B' ->
                genBishopMoves(pos, board);
            case 'N' ->
                genKnightMoves(pos, board);
            case 'P' ->
                genPawnMoves(pos, board);
            default ->
                new ArrayList<>();
        };
    }

    private List<Move> genKingMoves(Position pos, Board board) {
        ArrayList<Move> kingMoves = new ArrayList<>();
        char color = board.getPiece(pos.row, pos.col).charAt(0);

        int[][] potentialMoves = {
            {-1, 1}, {0, 1}, {1, 1},
            {-1, 0}, {1, 0},
            {-1, -1}, {0, -1}, {1, -1}
        };
        // check if col and row is between 0 and 7 inclusive
        // squares[0][-1], squares[+1][-1], squares[+1][0], squares[+1][+1]
        // squares[0][+1], squares[-1][+1], squares[-1][0], squares[-1][-1]

        for (int[] kingMove : potentialMoves) {
            int toRow = pos.row + kingMove[0];
            int toCol = pos.col + kingMove[1];

            if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
                continue;
            }

            if (!board.isEmpty(toRow, toCol) && !board.isEnemy(toRow, toCol, color)) {
                continue;
            }

            kingMoves.add(new Move(
                    new Position(pos.row, pos.col),
                    new Position(toRow, toCol)
            ));
        }

        return kingMoves;
    }

    private List<Move> genKnightMoves(Position pos, Board board) {
        ArrayList<Move> knightMoves = new ArrayList<>();
        char color = board.getPiece(pos.row, pos.col).charAt(0);

        int[][] potentialMoves = {
            {-2, 1}, {-1, 2}, {1, 2}, {2, 1},
            {2, -1}, {1, -2}, {-1, -2}, {-2, -1}
        };

        for (int[] knightMove : potentialMoves) {
            int toRow = pos.row + knightMove[0];
            int toCol = pos.col + knightMove[1];

            if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
                continue;
            }

            if (!board.isEmpty(toRow, toCol) && !board.isEnemy(toRow, toCol, color)) {
                continue;
            }

            knightMoves.add(new Move(
                    new Position(pos.row, pos.col),
                    new Position(toRow, toCol)));
        }
        return knightMoves;
    }

    private List<Move> genPawnMoves(Position pos, Board board) {
        ArrayList<Move> pawnMoves = new ArrayList<>();
        char color = board.getPiece(pos.row, pos.col).charAt(0);
        // check color
        if (color == 'w') {

            int toRow = pos.row - 1;

            if (toRow >= 0 && board.isEmpty(toRow, pos.col)) {
                pawnMoves.add(new Move(pos, new Position(toRow, pos.col)));

                if (pos.row == 6 && board.isEmpty(pos.row - 2, pos.col)) {
                    pawnMoves.add(new Move(
                            new Position(pos.row, pos.col),
                            new Position(pos.row - 2, pos.col)));
                }
            }

            if (pos.col + 1 <= 7 && !board.isEmpty(toRow, pos.col + 1) && board.isEnemy(toRow, pos.col + 1, color)) {
                pawnMoves.add(new Move(
                        new Position(pos.row, pos.col),
                        new Position(toRow, pos.col + 1)));
            }

            if (pos.col - 1 >= 0 && !board.isEmpty(toRow, pos.col - 1) && board.isEnemy(toRow, pos.col - 1, color)) {
                pawnMoves.add(new Move(
                        new Position(pos.row, pos.col),
                        new Position(toRow, pos.col - 1)));
            }

            if (toRow == 0 && board.isEmpty(toRow, pos.col)) {
                pawnUpgrade(color);
            }
        }

        if (color == 'b') {

            int toRow = pos.row + 1;

            if (toRow <= 7 && board.isEmpty(toRow, pos.col)) {
                pawnMoves.add(new Move(pos, new Position(toRow, pos.col)));

                if (pos.row == 1 && board.isEmpty(pos.row + 2, pos.col)) {
                    pawnMoves.add(new Move(
                            new Position(pos.row, pos.col),
                            new Position(pos.row + 2, pos.col)));
                }
            }

            if (pos.col - 1 >= 0 && !board.isEmpty(toRow, pos.col - 1) && board.isEnemy(toRow, pos.col - 1, color)) {
                pawnMoves.add(new Move(
                        new Position(pos.row, pos.col),
                        new Position(toRow, pos.col - 1)));
            }

            if (pos.col + 1 <= 7 && !board.isEmpty(toRow, pos.col + 1) && board.isEnemy(toRow, pos.col + 1, color)) {
                pawnMoves.add(new Move(
                        new Position(pos.row, pos.col),
                        new Position(toRow, pos.col + 1)));
            }

            if (toRow == 7 && board.isEmpty(toRow, pos.col)) {
                pawnUpgrade(color);
            }
        }
        return pawnMoves;
    }

    private String[] pawnUpgrade(char myColor) {
        String[] pieces = {"Q", "R", "B", "K"};
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] += myColor + pieces[i];
        }
        return pieces;
    }

    private List<Move> genBishopMoves(Position pos, Board board) {
        ArrayList<Move> bishopMoves = new ArrayList<>();
        char color = board.getPiece(pos.row, pos.col).charAt(0);

        int[][] potentialMoves = {
            {-1, 1}, {1, 1},
            {-1, -1}, {1, -1}
        };

        for (int[] bishopMove : potentialMoves) {
            int toRow = pos.row + bishopMove[0];
            int toCol = pos.col + bishopMove[1];

            while (toRow >= 0 && toRow <= 7 && toCol >= 0 && toCol <= 7) {

                if (board.isEmpty(toRow, toCol)) {
                    bishopMoves.add(new Move(
                            new Position(pos.row, pos.col),
                            new Position(toRow, toCol)));

                } else if (board.isEnemy(toRow, toCol, color)) {
                    bishopMoves.add(new Move(
                            new Position(pos.row, pos.col),
                            new Position(toRow, toCol)));
                    break;
                } else {
                    break;
                }
                toRow += bishopMove[0];
                toCol += bishopMove[1];
            }
        }
        return bishopMoves;
    }

    private List<Move> genRookMoves(Position pos, Board board) {

        ArrayList<Move> rookMoves = new ArrayList<>();
        char color = board.getPiece(pos.row, pos.col).charAt(0);

        int[][] potentialMoves = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
        };

        for (int[] rookMove : potentialMoves) {
            int toRow = pos.row + rookMove[0];
            int toCol = pos.col + rookMove[1];

            while (toRow >= 0 && toRow <= 7 && toCol >= 0 && toCol <= 7) {
                if (board.isEmpty(toRow, toCol)) {
                    rookMoves.add(new Move(
                            new Position(pos.row, pos.col),
                            new Position(toRow, toCol)));
                } else if (board.isEnemy(toRow, toCol, color)) {
                    rookMoves.add(new Move(
                            new Position(pos.row, pos.col),
                            new Position(toRow, toCol)));
                    break;
                } else {
                    break;
                }
                toRow += rookMove[0];
                toCol += rookMove[1];
            }
        }
        return rookMoves;
    }

    private List<Move> genQueenMoves(Position pos, Board board) {
        ArrayList<Move> queenMoves = new ArrayList<>();
        queenMoves.addAll(genBishopMoves(pos, board));
        queenMoves.addAll(genRookMoves(pos, board));
        return queenMoves;
    }

}
