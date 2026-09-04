package chess.notation;

import chess.board.Board;
import chess.model.Position;

public class NotationsGenerator {

  public String generateNotation(Position from, Position to, String pieceMoved, String pieceCaptured, Board board,
      boolean isCheck, boolean isCheckmate) {
    char type = pieceMoved.charAt(1);
    char file = (char) ('a' + to.col);
    int rank = 8 - to.row;

    StringBuilder notation = new StringBuilder();
    // check for castling
    // check for side
    // return castling notation depending on the castling
    switch (type) {
      case 'K' -> {
        if (file == 'g') {
          notation.append("O-O");
        } else {
          notation.append("O-O-O");
        }
      }
      case 'P' -> {
        if (pieceCaptured != null) {
          char fromFile = (char) ('a' + from.col);
          notation.append(fromFile).append("x").append(file).append(rank);
        } else {
          notation.append(file).append(rank);
        }
      }
      default -> {
        String pieceType = switch (type) {
          case 'K' -> "K";
          case 'Q' -> "Q";
          case 'R' -> "R";
          case 'N' -> "N";
          case 'B' -> "B";
          default -> "";
        };
        notation.append(pieceType);
        if (pieceCaptured != null) {
          notation.append("x");
        }
        notation.append(file).append(rank);
      }
    }

    // check for checkmate
    // return checkmate notation "#"

    // check for stalemate
    // return 1/2 - 1/2

    // check for promotion
    // return promotion notation "="

    // check for check
    // return check notation "+"
    if (isCheckmate) {
      notation.append("#");
    } else if (isCheck) {
      notation.append("+");
    }

    System.out.println(notation);
    return notation.toString();
  }
}