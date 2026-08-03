package chess.movegen;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import chess.board.Board;
import chess.model.Move;
import chess.model.Position;

@Component
public class LegalMovesFilter {

  private final MoveGenerator moveGenerator;
  private final CheckGenerator checkGenerator;

  public LegalMovesFilter(MoveGenerator moveGenerator, CheckGenerator checkGenerator) {
    this.moveGenerator = moveGenerator;
    this.checkGenerator = checkGenerator;
  }

  public List<Move> filterLegalMoves(Position pos, Board board) {
    // get all moves
    if (board.getPiece(pos.row, pos.col) == null) {
      return new ArrayList<>();
    }
    List<Move> moves = moveGenerator.genMove(pos, board);
    List<Move> filteredMoves = new ArrayList<>();
    char color = board.getPiece(pos.row, pos.col).charAt(0);
    // for each move i need a copy of the board

    for (Move move : moves) {
      Board boardCopy = board.copy();
      boardCopy.movePiece(move.getFromPos().row, move.getFromPos().col, move.getToPos().row, move.getToPos().col);

      // after that move is made check if the king is in check
      // if not, then i will keep that move in legal moves filter
      if (!checkGenerator.isInCheck(color, boardCopy)) {
        filteredMoves.add(move);
      }
    }

    return filteredMoves;
  }
}
