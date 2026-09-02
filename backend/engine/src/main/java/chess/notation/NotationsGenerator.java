package chess.notation;

import chess.board.Board;
import chess.model.Position;

public class NotationsGenerator {
  public String generateNotation(Position from, Position to, String pieceMoved, String pieceCaptured, Board board) {
    char type = pieceMoved.charAt(1);
    char file = (char) ('a' + to.col);
    int rank = 8 - to.row;

    if (type == 'P') {
      if (pieceCaptured != null) {
        char fromFile = (char) ('a' + to.col);
        return fromFile + "x" + file + rank;
      }
      return "" + file + rank;
    }

    // check for castling
    // check for side
    // return castling notation depending on the castling

    // check for check
    // return check notation "+"

    // check for checkmate
    // return checkmate notation "#"

    // check for stalemate
    // return 1/2 - 1/2

    // check for promotion
    // return promotion notation "="

    String pieceType = switch (type) {
      case 'K' -> "K";
      case 'Q' -> "Q";
      case 'R' -> "R";
      case 'N' -> "N";
      case 'B' -> "B";
      default -> "";
    };

    if (pieceCaptured != null) {
      return pieceType + "x" + file + rank;
    }
    return pieceType + "" + file + rank;
  }
}
