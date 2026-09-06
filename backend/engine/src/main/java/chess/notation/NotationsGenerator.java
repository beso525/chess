package chess.notation;

import chess.board.Board;
import chess.model.Position;

public class NotationsGenerator {

  public String generateNotation(Position from,
      Position to,
      String pieceMoved,
      String pieceCaptured,
      Board board,
      boolean isCheck,
      boolean isCheckmate,
      boolean isCastling) {
    char type = pieceMoved.charAt(1);
    char file = (char) ('a' + to.col);
    int rank = 8 - to.row;

    StringBuilder notation = new StringBuilder();
    // check for castling
    // check for side
    // return castling notation depending on the castling
    String pieceType = switch (type) {
      case 'P' -> "P";
      case 'K' -> "K";
      case 'Q' -> "Q";
      case 'R' -> "R";
      case 'N' -> "N";
      case 'B' -> "B";
      default -> "";
    };
    switch (pieceType) {

      case "P" -> {
        if (pieceCaptured != null) {
          char fromFile = (char) ('a' + from.col);
          notation.append(fromFile).append("x").append(file).append(rank);
        } else {
          notation.append(file).append(rank);
        }

        if (rank == 8 || rank == 1) {
          notation.append("=").append(pieceMoved);
        }
      }
      case "K" -> {
        if (isCastling && file == 'g') {
          notation.append("O-O");
        } else if (isCastling && file == 'c') {
          notation.append("O-O-O");
        } else {
          notation.append(pieceType).append(file).append(rank);
        }
      }
      default -> {
        notation.append(pieceType);
        if (pieceCaptured != null) {
          notation.append("x");
        }
        notation.append(file).append(rank);
      }
    }

    // check for stalemate
    // return 1/2 - 1/2

    // check for promotion
    // return promotion notation "="

    // return checkmate notation "#"
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