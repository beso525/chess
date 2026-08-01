package chess.movegen;

import java.util.List;

import org.springframework.stereotype.Component;

import chess.board.Board;
import chess.model.Move;
import chess.model.Position;

@Component
public class CheckGenerator {

  private final MoveGenerator moveGenerator;

  public CheckGenerator(MoveGenerator moveGenerator) {
    this.moveGenerator = moveGenerator;
  }

  public boolean isInCheck(char kingColor, Board board) {
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
          List<Move> legalMoves = moveGenerator.genMove(new Position(r, c), board);
          // comparing if there's overlap
          for (Move move : legalMoves) {
            if (move.getToPos().row == kingPos.row &&
                move.getToPos().col == kingPos.col) {
              System.out.println("King is in check at " + kingPos.row + " " + kingPos.col +
                  " by piece at " + piece);
              return true;
            }
          }
        }
      }
    }
    return false;
  }
}
