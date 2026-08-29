package chess.movegen;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import chess.board.Board;
import chess.model.CastlingRights;
import chess.model.EnPassantSquare;
import chess.model.Move;
import chess.model.Position;

@Component
public class MoveGenerator {

  private final CheckGenerator checkGenerator;

  public MoveGenerator(@Lazy CheckGenerator checkGenerator) {
    this.checkGenerator = checkGenerator;
  }

  public List<Move> genMove(Position pos, Board board, CastlingRights castlingRights, EnPassantSquare es) {
    String piece = board.getPiece(pos.row, pos.col);
    char type = piece.charAt(1);

    return switch (type) {
      case 'K' ->
        genKingMoves(pos, board, castlingRights, es);
      case 'Q' ->
        genQueenMoves(pos, board);
      case 'R' ->
        genRookMoves(pos, board);
      case 'B' ->
        genBishopMoves(pos, board);
      case 'N' ->
        genKnightMoves(pos, board);
      case 'P' ->
        genPawnMoves(pos, board, es);
      default ->
        new ArrayList<>();
    };
  }

  private List<Move> genKingMoves(Position pos, Board board, CastlingRights castlingRights, EnPassantSquare es) {
    ArrayList<Move> kingMoves = new ArrayList<>();
    char color = board.getPiece(pos.row, pos.col).charAt(0);

    int[][] potentialMoves = {
        { -1, 1 }, { 0, 1 }, { 1, 1 },
        { -1, 0 }, { 1, 0 },
        { -1, -1 }, { 0, -1 }, { 1, -1 },
    };

    for (int[] kingMove : potentialMoves) {
      int toRow = pos.row + kingMove[0];
      int toCol = pos.col + kingMove[1];

      if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7)
        continue;

      if (!board.isEmpty(toRow, toCol) && !board.isEnemy(toRow, toCol, color))
        continue;

      kingMoves.add(new Move(
          new Position(pos.row, pos.col),
          new Position(toRow, toCol)));
    }
    kingMoves.addAll(genCastlingMoves(pos, board, castlingRights, es));
    return kingMoves;
  }

  private List<Move> genKnightMoves(Position pos, Board board) {
    ArrayList<Move> knightMoves = new ArrayList<>();
    char color = board.getPiece(pos.row, pos.col).charAt(0);

    int[][] potentialMoves = {
        { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 },
        { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 }
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

  private List<Move> genPawnMoves(Position pos, Board board, EnPassantSquare es) {
    ArrayList<Move> pawnMoves = new ArrayList<>();
    char color = board.getPiece(pos.row, pos.col).charAt(0);
    // check color
    if (color == 'w') {

      int toRow = pos.row - 1;

      if (toRow >= 0) {

        if (board.isEmpty(toRow, pos.col)) {
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
      }
    }

    if (color == 'b') {
      int toRow = pos.row + 1;
      if (toRow <= 7) {

        if (board.isEmpty(toRow, pos.col)) {
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
      }
    }

    pawnMoves.addAll(genEnPassantMoves(pos, board, es));
    return pawnMoves;
  }

  private List<Move> genBishopMoves(Position pos, Board board) {
    ArrayList<Move> bishopMoves = new ArrayList<>();
    char color = board.getPiece(pos.row, pos.col).charAt(0);

    int[][] potentialMoves = {
        { -1, 1 }, { 1, 1 },
        { -1, -1 }, { 1, -1 }
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
        { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 }
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

  // special moves
  private List<Move> genCastlingMoves(Position pos, Board board, CastlingRights cr, EnPassantSquare es) {
    if (cr == null)
      return new ArrayList<>();
    ArrayList<Move> castlingMoves = new ArrayList<>();
    char color = board.getPiece(pos.row, pos.col).charAt(0);
    int row = pos.row;

    boolean kingMoved = (color == 'w') ? cr.whiteKingMoved : cr.blackKingMoved;
    if (kingMoved)
      return castlingMoves;

    // kingside castling
    boolean kingSideRookMoved = (color == 'w') ? cr.whiteKingSideRookMoved : cr.blackKingSideRookMoved;
    if (!kingSideRookMoved
        && board.getPiece(row, 7) != null
        && board.getPiece(row, 7).charAt(1) == 'R'
        && board.isEmpty(row, 5)
        && board.isEmpty(row, 6)
        && !checkGenerator.isSquareAttacked(row, 6, color, board, es)
        && !checkGenerator.isSquareAttacked(row, 5, color, board, es)
        && !checkGenerator.isInCheck(color, board, cr, es)) {
      System.out.println(!checkGenerator.isSquareAttacked(row, 5, color, board, es));
      System.out.println(!checkGenerator.isSquareAttacked(row, 6, color, board, es));
      castlingMoves.add(new Move(pos, new Position(row, 6)));
      System.out.println(castlingMoves.get(0).toString());
    }

    // queenside castling
    boolean queenSideRookMoved = (color == 'w') ? cr.whiteQueenSideRookMoved : cr.blackQueenSideRookMoved;
    if (!queenSideRookMoved
        && board.getPiece(row, 0) != null
        && board.getPiece(row, 0).charAt(1) == 'R'
        && board.isEmpty(row, 1)
        && board.isEmpty(row, 2)
        && board.isEmpty(row, 3)
        && !checkGenerator.isSquareAttacked(row, 2, color, board, es)
        // && !checkGenerator.isSquareAttacked(row, 3, color, board, es)
        && !checkGenerator.isInCheck(color, board, cr, es)) {
      castlingMoves.add(new Move(pos, new Position(row, 2)));
    }
    return castlingMoves;
  }

  private List<Move> genEnPassantMoves(Position pos, Board board, EnPassantSquare es) {
    List<Move> enPassantMoves = new ArrayList<>();
    char color = board.getPiece(pos.row, pos.col).charAt(0);
    int findRow = (color == 'w') ? 3 : 4;

    if (!es.isAvailable())
      return enPassantMoves;

    if (pos.row != findRow)
      return enPassantMoves;

    if (es.row == pos.row - (color == 'w' ? 1 : -1)
        && Math.abs(es.col - pos.col) == 1) {
      enPassantMoves.add(new Move(pos, new Position(es.row, es.col)));
    }
    return enPassantMoves;
  }

}