package chess.model;

import chess.rules.GameStatus;

public class BoardResponse {

  private String[][] squares;
  private boolean whiteTurn;
  private boolean pendingPromotion;
  private int promotionRow;
  private int promotionCol;
  private boolean inCheck;
  private GameStatus gameStatus;

  public BoardResponse(
      String[][] squares,
      boolean whiteTurn,
      boolean pendingPromotion,
      int promotionRow,
      int promotionCol,
      boolean inCheck,
      GameStatus gameStatus) {
    this.squares = squares;
    this.whiteTurn = whiteTurn;
    this.pendingPromotion = pendingPromotion;
    this.promotionRow = promotionRow;
    this.promotionCol = promotionCol;
    this.inCheck = inCheck;
    this.gameStatus = gameStatus;
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

}
