package chess.model;

import java.util.List;

import chess.rules.GameStatus;

public class BoardResponse {

  private String[][] squares;
  private boolean whiteTurn;
  private boolean pendingPromotion;
  private int promotionRow;
  private int promotionCol;
  private boolean inCheck;
  private GameStatus gameStatus;
  private List<String> whiteCaptures;
  private List<String> blackCaptures;

  public BoardResponse(
      String[][] squares,
      boolean whiteTurn,
      boolean pendingPromotion,
      int promotionRow,
      int promotionCol,
      boolean inCheck,
      GameStatus gameStatus,
      List<String> whiteCaptures,
      List<String> blackCaptures) {
    this.squares = squares;
    this.whiteTurn = whiteTurn;
    this.pendingPromotion = pendingPromotion;
    this.promotionRow = promotionRow;
    this.promotionCol = promotionCol;
    this.inCheck = inCheck;
    this.gameStatus = gameStatus;
    this.whiteCaptures = whiteCaptures;
    this.blackCaptures = blackCaptures;
  }

  public String[][] getSquares() {
    return squares;
  }

  public int getPromotionRow() {
    return promotionRow;
  }

  public int getPromotionCol() {
    return promotionCol;
  }

  public boolean isWhiteTurn() {
    return whiteTurn;
  }

  public boolean isPendingPromotion() {
    return pendingPromotion;
  }

  public boolean isInCheck() {
    return inCheck;
  }

  public GameStatus getGameStatus() {
    return gameStatus;
  }

  public List<String> getWhiteCaptures() {
    return whiteCaptures;
  }

  public List<String> getBlackCaptures() {
    return blackCaptures;
  }
}
