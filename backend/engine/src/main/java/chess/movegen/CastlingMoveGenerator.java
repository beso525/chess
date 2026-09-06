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
public class CastlingMoveGenerator {

  private final CheckGenerator checkGenerator;

  public CastlingMoveGenerator(CheckGenerator checkGenerator) {
    this.checkGenerator = checkGenerator;
  }

  public List<Move> genCastlingMoves(Position pos, Board board, CastlingRights cr, EnPassantSquare es) {
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
      castlingMoves.add(new Move(pos, new Position(row, 6)));
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
}
