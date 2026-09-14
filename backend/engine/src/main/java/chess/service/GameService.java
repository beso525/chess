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
import chess.model.MoveResult;
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
  private NotationsGenerator notationsGenerator = new NotationsGenerator();

  private int promotionCol = -1;
  private int promotionRow = -1;

  private boolean isCastling = false;

  private boolean isWhiteTurn = true;

  private List<String> whiteCaptures = new ArrayList<>();
  private List<String> blackCaptures = new ArrayList<>();
  private boolean pendingPromotion = false;

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
    promotionRow = -1;
    promotionCol = -1;
    whiteCaptures.clear();
    blackCaptures.clear();
    isWhiteTurn = true;
    moveHistory.clear();
  }

  public void makeMove(Position from, Position to) {
    CastlingRights prevCR = board.getCastlingRights();
    EnPassantSquare prevES = board.getEnPassantSquare();
    String pieceMoved = board.getPiece(from.row, from.col);
    String pieceCaptured = board.getPiece(to.row, to.col);
    MoveResult moveResult;

    moveResult = board.applyMove(from, to);

    recordCapture(moveResult);
    updatePendingPromotion(moveResult);
    recordMoveHistory(from, to, pieceMoved, pieceCaptured, moveResult, prevCR, prevES);
  }

  private void updatePendingPromotion(MoveResult moveResult) {
    pendingPromotion = moveResult.isPendingPromotion();

    if (pendingPromotion) {
      promotionRow = moveResult.getToPos().row;
      promotionCol = moveResult.getToPos().col;
    } else {
      promotionCol = -1;
      promotionRow = -1;
    }
  }

  private void recordCapture(MoveResult moveResult) {
    char color = moveResult.getPieceMoved().charAt(0);
    String capturedPiece = moveResult.getPieceCaptured();
    if (capturedPiece != null && capturedPiece.charAt(0) != color) {
      capture(moveResult.getPieceMoved().charAt(0), capturedPiece);
    }

    String enPassantCapture = moveResult.getEnPassantCapture();
    if (enPassantCapture != null) {
      capture(color, enPassantCapture);
    }
  }

  private void recordMoveHistory(Position from,
      Position to,
      String pieceMoved,
      String pieceCaptured,
      MoveResult moveResult,
      CastlingRights prevCR,
      EnPassantSquare prevES) {

    char color = moveResult.getPieceMoved().charAt(0);
    char opponentColor = color == 'w' ? 'b' : 'w';

    boolean isCheckMate = gameState.evaluate(opponentColor, board, prevCR, prevES) == GameStatus.CHECKMATE;
    boolean isCheck = checkGenerator.isInCheck(opponentColor, board, prevCR, prevES);
    boolean wasCastling = moveResult.isCastling();

    String notation = notationsGenerator.generateNotation(
        from, to, pieceMoved, pieceCaptured, board,
        isCheck, isCheckMate, wasCastling);

    moveHistory.push(new MoveRecord(from, to, pieceMoved, pieceCaptured, notation, prevCR, prevES));
  }

  public void capture(char capturingColor, String capturedPiece) {
    if (capturingColor == 'w') {
      whiteCaptures.add(capturedPiece);
    } else {
      blackCaptures.add(capturedPiece);
    }
  }

  public void promotePawn(int row, int col, String chosenPiece) {
    board.promotePawn(row, col, chosenPiece);
    pendingPromotion = false;
    flipTurn();
  }

  public boolean isWhiteTurn() {
    return isWhiteTurn;
  }

  public void flipTurn() {
    isWhiteTurn = !isWhiteTurn;
  }

  public boolean isCorrectTurn(Position from) {
    String piece = board.getPiece(from.row, from.col);
    if (piece == null) {
      return false;
    }

    char color = piece.charAt(0);
    return (isWhiteTurn && color == 'w' || !isWhiteTurn && color == 'b');
  }

  public boolean isPlayerInCheck() {
    char kingColor = isWhiteTurn() ? 'w' : 'b';
    return checkGenerator.isInCheck(kingColor, getBoard(), board.getCastlingRights(), board.getEnPassantSquare());
  }

  public GameStatus getGameStatus() {
    char color = isWhiteTurn() ? 'w' : 'b';
    return gameState.evaluate(color, getBoard(), board.getCastlingRights(),
        board.getEnPassantSquare());
  }

  // GETTERS
  public Board getBoard() {
    return board;
  }

  public int getPromotionRow() {
    return promotionRow;
  }

  public int getPromotionCol() {
    return promotionCol;
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

  public boolean getIsCastling() {
    return isCastling;
  }
}