package chess.movegen;

import java.util.List;

import org.springframework.stereotype.Component;

import chess.board.Board;
import chess.model.CastlingRights;
import chess.model.EnPassantSquare;
import chess.model.Move;
import chess.model.Position;

@Component
public class CheckGenerator {

  private final MoveGenerator moveGenerator;

  public CheckGenerator(MoveGenerator moveGenerator) {
    this.moveGenerator = moveGenerator;
  }

  public boolean isInCheck(char kingColor, Board board, CastlingRights castlingRights,
      EnPassantSquare enPassantSquare) {
    // getting king position
    Position kingPos = null;
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        String piece = board.getPiece(r, c);
        if (piece != null && piece.charAt(0) == kingColor && piece.charAt(1) == 'K') {
          kingPos = new Position(r, c);
          break;
        }
      }
    }
    // getting legal moves for opposite color pieces
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        String piece = board.getPiece(r, c);
        if (piece != null && piece.charAt(0) != kingColor) {
          // generate a list of all legal moves
          List<Move> legalMoves = moveGenerator.genMove(new Position(r, c), board, null, enPassantSquare);
          // comparing if there's overlap
          for (Move move : legalMoves) {
            if (move.getToPos().row == kingPos.row &&
                move.getToPos().col == kingPos.col) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  public boolean isSquareAttacked(int row, int col, char color, Board board, EnPassantSquare enPassantSquare) {

    Board tempBoard = board.copy();

    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        String piece = tempBoard.getPiece(r, c);
        if (piece != null && piece.charAt(0) == color
            && piece.charAt(1) == 'K'
            && !(r == row && c == col)) {
          tempBoard.getSquares()[r][c] = null;
          break;
        }
      }
    }
    tempBoard.getSquares()[row][col] = color + "K";

    return isInCheck(color, tempBoard, null, enPassantSquare);
  }
}
