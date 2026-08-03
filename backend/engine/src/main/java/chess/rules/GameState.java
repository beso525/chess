package chess.rules;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import chess.board.Board;
import chess.model.Move;
import chess.model.Position;
import chess.movegen.CheckGenerator;
import chess.movegen.LegalMovesFilter;

@Component
public class GameState {

  private final LegalMovesFilter legalMovesFilter;
  private final CheckGenerator checkGenerator;

  public GameState(LegalMovesFilter legalMovesFilter, CheckGenerator checkGenerator) {
    this.legalMovesFilter = legalMovesFilter;
    this.checkGenerator = checkGenerator;
  }

  public GameStatus evaluate(char color, Board board) {
    List<Move> allMoves = new ArrayList<>();

    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        String piece = board.getPiece(r, c);
        if (piece != null && piece.charAt(0) == color) {
          allMoves.addAll(legalMovesFilter.filterLegalMoves(new Position(r, c), board));
        }
      }
    }

    if (allMoves.isEmpty() && checkGenerator.isInCheck(color, board)) {
      return GameStatus.CHECKMATE;
    } else if (allMoves.isEmpty() && !checkGenerator.isInCheck(color, board)) {
      return GameStatus.STALEMATE;
    }
    return GameStatus.ONGOING;
  }
}
