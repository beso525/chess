package chess.movegen;

import java.util.ArrayList;
import java.util.List;

import chess.board.Board;
import chess.model.Move;
import chess.model.Position;

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
            {-1, -1}, {0, 1}, {1, 1},
            {-1, 0}, {1, 0},
            {-1, -1}, {0, -1}, {1, -1}
        };
        // check if col and row is between 0 and 7 inclusive
        // squares[0][-1], squares[+1][-1], squares[+1][0], squares[+1][+1]
        // squares[0][+1], squares[-1][+1], squares[-1][0], squares[-1][-1]

        for (int[] pMove : potentialMoves) {
            int toRow = pos.row + pMove[0];
            int toCol = pos.col + pMove[1];

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

        //
        int[][] potentialMoves = {
            {-2, 1}, {-1, 2}, {1, 2}, {2, 1},
            {2, -1}, {1, -2}, {-1, -2}, {-2, -1}
        };

        for (int[] moveKnight : potentialMoves) {
            int toRow = pos.row + moveKnight[0];
            int toCol = pos.col + moveKnight[1];

            if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
                continue;
            }

            if (!board.isEmpty(toRow, toCol) && !board.isEnemy(pos.row, pos.col, color)) {
                continue;
            }

            knightMoves.add(new Move(
                    new Position(pos.row, pos.col),
                    new Position(toRow, toCol)));
        };
        return knightMoves;
    }

    private List<Move> genBishopMoves(Position pos, Board board) {
        return new ArrayList<>();
    }

    private List<Move> genRookMoves(Position pos, Board board) {
        return new ArrayList<>();
    }

    private List<Move> genQueenMoves(Position pos, Board board) {
        return new ArrayList<>();
    }

    private List<Move> genPawnMoves(Position pos, Board board) {
        return new ArrayList<>();
    }
}
