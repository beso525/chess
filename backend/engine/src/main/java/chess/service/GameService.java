package chess.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.stereotype.Service;

import chess.board.Board;
import chess.model.CastlingRights;
import chess.model.EnPassantSquare;
import chess.model.MoveRecord;
import chess.model.Position;
import chess.movegen.CheckGenerator;
import chess.notation.NotationsGenerator;
import chess.rules.GameState;
import chess.rules.GameStatus;

@Service
public class GameService {

  private final Board board = new Board();
  private final CheckGenerator checkGenerator;
  private final GameState gameState;

  private boolean pendingPromotion = false;

  private List<String> whiteCaptures = new ArrayList<>();
  private List<String> blackCaptures = new ArrayList<>();

  private Deque<MoveRecord> moveHistory = new ArrayDeque<>();

  public GameService(CheckGenerator checkGenerator, GameState gameState) {
    this.checkGenerator = checkGenerator;
    this.gameState = gameState;
  }

  public boolean isPendingPromotion() {
    return pendingPromotion;
  }

  public void resetBoard() {
    board.resetStartingPosition();
    pendingPromotion = false;
    board.setPromotionRow(-1);
    board.setPromotionCol(-1);
    board.setWhiteKingMoved(false);
    board.setBlackKingMoved(false);
    board.setBlackKingSideRookMoved(false);
    board.setBlackQueenSideRookMoved(false);
    board.setWhiteKingSideRookMoved(false);
    board.setWhiteQueenSideRookMoved(false);
    whiteCaptures.clear();
    blackCaptures.clear();
    board.setIsCastling(false);
    moveHistory.clear();
  }

  public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
    CastlingRights prevCR = board.getCastlingRights();
    EnPassantSquare prevES = board.getEnPassantSquare();
    String pieceMoved = board.getPiece(fromRow, fromCol);
    String pieceCaptured = board.getPiece(toRow, toCol);
    NotationsGenerator notationsGenerator = new NotationsGenerator();

    board.setIsCastling(false);
    String piece = board.getPiece(fromRow, fromCol);
    char color = piece.charAt(0);
    char type = piece.charAt(1);

    // check if king has moved
    if (type == 'K') {
      if (color == 'w')
        board.setWhiteKingMoved(true);
      else
        board.setBlackKingMoved(true);
    }

    // check if and which rooks have moved
    if (type == 'R') {
      if (color == 'w') {
        if (fromCol == 7)
          board.setWhiteKingSideRookMoved(true);
        if (fromCol == 0)
          board.setWhiteQueenSideRookMoved(true);
      } else {
        if (fromCol == 7)
          board.setBlackKingSideRookMoved(true);
        if (fromCol == 0)
          board.setBlackQueenSideRookMoved(true);
      }

    }

    // check for promotion
    if ((color == 'w' && type == 'P' && toRow == 0)
        || (color == 'b' && type == 'P' && toRow == 7)) {
      board.setPromotionRow(toRow);
      board.setPromotionCol(toCol);
      pendingPromotion = true;
    }

    boolean isEnPassant = type == 'P' && fromCol != toCol && board.getPiece(toRow, toCol) == null;

    // save captured pieces to a list
    String capturedPiece = board.getPiece(toRow, toCol);
    if (capturedPiece != null && capturedPiece.charAt(0) != color) {
      capture(color, capturedPiece);
    }

    board.movePiece(fromRow, fromCol, toRow, toCol);

    // *** AFTER A PIECE HAS MOVED *** //
    // check if it's the king moving 2 spaces to cue castling
    board.setIsCastling(type == 'K' && Math.abs(toCol - fromCol) == 2);
    if (board.getIsCastling()) {
      // where the rook moves if the king castled king side
      if (toCol == 6) {
        board.movePiece(fromRow, 7, fromRow, 5);
      }
      // and where it moves if the king castled queen side
      if (toCol == 2) {
        board.movePiece(fromRow, 0, fromRow, 3);
      }
    }

    // check if the pawn that moved
    if (type == 'P' && Math.abs(toRow - fromRow) == 2) {
      board.setEnPassantCol(toCol);
      board.setEnPassantRow((fromRow + toRow) / 2);
    } else {
      board.setEnPassantCol(-1);
      board.setEnPassantRow(-1);
    }
    if (isEnPassant) {
      int capturedPawnRow = color == 'w' ? toRow + 1 : toRow - 1;
      String enPassantCapture = board.getPiece(capturedPawnRow, toCol);
      board.getSquares()[capturedPawnRow][toCol] = null;
      capture(color, enPassantCapture);
    }

    char opponentColor = (color == 'w') ? 'b' : 'w';
    boolean isCheckMate = gameState.evaluate(opponentColor, board, prevCR, prevES) == GameStatus.CHECKMATE;
    boolean isCheck = checkGenerator.isInCheck(opponentColor, board, prevCR, prevES);

    String notation = notationsGenerator.generateNotation(
        new Position(fromRow, fromCol),
        new Position(toRow, toCol),
        pieceMoved,
        pieceCaptured,
        board,
        isCheck,
        isCheckMate,
        board.getIsCastling());

    moveHistory.push(new MoveRecord(
        new Position(fromRow, fromCol),
        new Position(toRow, toCol),
        pieceMoved,
        pieceCaptured,
        notation,
        prevCR,
        prevES));
  }

  private void undoMove() {

  }

  public void capture(char capturingColor, String capturedPiece) {
    if (capturingColor == 'w') {
      whiteCaptures.add(capturedPiece);
    } else {
      blackCaptures.add(capturedPiece);
    }
  }

  public void promotePawn(int row, int col, String chosenPiece) {
    char color = board.getPiece(row, col).charAt(0);
    board.getSquares()[row][col] = color + chosenPiece;
    pendingPromotion = false;
    board.setPromotionRow(-1);
    board.setPromotionCol(-1);
    board.flipTurn();
  }

  public boolean isPlayerInCheck() {
    char kingColor = board.isWhiteTurn() ? 'w' : 'b';
    return checkGenerator.isInCheck(kingColor, getBoard(), board.getCastlingRights(), board.getEnPassantSquare());
  }

  public GameStatus getGameStatus() {
    char color = board.isWhiteTurn() ? 'w' : 'b';
    return gameState.evaluate(color, getBoard(), board.getCastlingRights(),
        board.getEnPassantSquare());
  }

  // GETTERS
  public Board getBoard() {
    return board;
  }

  public List<String> getWhiteCaptures() {
    return whiteCaptures;
  }

  public List<String> getBlackCaptures() {
    return blackCaptures;
  }

  public List<String> getMoveHistory() {
    List<String> movesString = new ArrayList<>(moveHistory.size());

    for (MoveRecord move : moveHistory) {
      movesString.add(move.toString());
    }
    return movesString;
  }
}