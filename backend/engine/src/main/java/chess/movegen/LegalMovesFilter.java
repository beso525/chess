package chess.movegen;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import chess.board.Board;
import chess.model.CastlingRights;
import chess.model.EnPassantSquare;
import chess.model.Move;
import chess.model.Position;

@Component
public class LegalMovesFilter {

  private final MoveGenerator moveGenerator;
  private final CastlingMoveGenerator castlingMoveGenerator;
  private final CheckGenerator checkGenerator;

  public LegalMovesFilter(MoveGenerator moveGenerator,
      CheckGenerator checkGenerator,
      CastlingMoveGenerator castlingMoveGenerator) {
    this.moveGenerator = moveGenerator;
    this.castlingMoveGenerator = castlingMoveGenerator;
    this.checkGenerator = checkGenerator;
  }

  public List<Move> filterLegalMoves(Position pos,
      Board board,
      CastlingRights castlingRights,
      EnPassantSquare enPassantSquare) {
    // get all moves
    String piece = board.getPiece(pos.row, pos.col);
    if (piece == null) {
      return new ArrayList<>();
    }

    List<Move> moves = new ArrayList<>(moveGenerator.genMove(pos, board, enPassantSquare));
    List<Move> filteredMoves = new ArrayList<>();

    char color = board.getPiece(pos.row, pos.col).charAt(0);
    // for each move i need a copy of the board
    if (piece.charAt(1) == 'K') {
      moves.addAll(castlingMoveGenerator.genCastlingMoves(pos, board, castlingRights, enPassantSquare));
    }

    for (Move move : moves) {
      Board boardCopy = board.copy();
      boardCopy.movePiece(move.getFromPos().row, move.getFromPos().col, move.getToPos().row, move.getToPos().col);
      // after that move is made check if the king is in check
      // if not, then i will keep that move in legal moves filter
      if (!checkGenerator.isInCheck(color, boardCopy, castlingRights, enPassantSquare)) {
        filteredMoves.add(move);
      }
    }
    return filteredMoves;
  }
}
