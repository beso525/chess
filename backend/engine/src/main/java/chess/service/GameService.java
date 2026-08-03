package chess.service;

import org.springframework.stereotype.Service;

import chess.board.Board;
import chess.movegen.CheckGenerator;
import chess.rules.GameState;
import chess.rules.GameStatus;

@Service
public class GameService {

  private final Board board = new Board();
  private final CheckGenerator checkGenerator;
  private final GameState gameState;
  private boolean isWhiteTurn = true;
  private boolean pendingPromotion = false;
  private int promotionRow = -1;
  private int promotionCol = -1;

  public Board getBoard() {
    return board;
  }

  public GameService(CheckGenerator checkGenerator, GameState gameState) {
    this.checkGenerator = checkGenerator;
    this.gameState = gameState;
  }

  public int getPromotionRow() {
    return promotionRow;
  }

  public int getPromotionCol() {
    return promotionCol;
  }

  public boolean isPendingPromotion() {
    return pendingPromotion;
  }

  public void resetBoard() {
    board.resetStartingPosition();
    isWhiteTurn = true;
    pendingPromotion = false;
    promotionRow = -1;
    promotionCol = -1;
  }

  public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
    String piece = board.getPiece(fromRow, fromCol);
    char color = piece.charAt(0);
    char pawn = piece.charAt(1);

    if ((color == 'w' && pawn == 'P' && toRow == 0)
        || (color == 'b' && pawn == 'P' && toRow == 7)) {
      pendingPromotion = true;
      promotionRow = toRow;
      promotionCol = toCol;
    }
    board.movePiece(fromRow, fromCol, toRow, toCol);
  }

  public boolean isWhiteTurn() {
    return isWhiteTurn;
  }

  public void promotePawn(int row, int col, String chosenPiece) {
    char color = board.getPiece(row, col).charAt(0);
    board.getSquares()[row][col] = color + chosenPiece;
    pendingPromotion = false;
    promotionRow = -1;
    promotionCol = -1;
    isWhiteTurn = !isWhiteTurn;
  }

  public boolean isCorrectTurn(int fromRow, int fromCol) {
    String piece = board.getPiece(fromRow, fromCol);
    if (piece == null) {
      return false;
    }

    char color = piece.charAt(0);
    return (isWhiteTurn && color == 'w' || !isWhiteTurn && color == 'b');
  }

  public void flipTurn() {
    isWhiteTurn = !isWhiteTurn;
  }

  public boolean isPlayerInCheck() {
    char kingColor = isWhiteTurn() ? 'w' : 'b';
    return checkGenerator.isInCheck(kingColor, getBoard());
  }

  public GameStatus getGameStatus() {
    char color = isWhiteTurn ? 'w' : 'b';
    return gameState.evaluate(color, getBoard());
  }
}
