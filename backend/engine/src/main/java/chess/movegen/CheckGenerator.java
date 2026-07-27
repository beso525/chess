package chess.movegen;

import java.util.List;
import java.util.ArrayList;

import chess.board.Board;
import chess.model.Move;
import chess.model.Position;

public class CheckGenerator {

  private MoveGenerator moveGenerator;

  public boolean isInCheck(char kingColor, Board board) {
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

    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        String piece = board.getPiece(r, c);
        if (piece != null && piece.charAt(0) == kingColor && piece.charAt(1) == 'K') {
          kingPos = new Position(r, c);
          break;
          if (piece != null && piece.charAt(0) != kingColor && piece.charAt(1) != 'K') {
            // generate a list of all legal moves
            List<List<Move>> legalMoves = new ArrayList<List<Move>>();
            legalMoves.add(moveGenerator.genMoves(pos, board));
          }
        }
      }

    }
    return false;
  }
}
