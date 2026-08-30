package chess.model;

import java.util.List;

import chess.rules.GameStatus;

public class BoardResponse {

  private String[][] squares;
  private boolean whiteTurn;
  private boolean pendingPromotion;
  private boolean wasCastling;
  private boolean inCheck;
  private int promotionRow;
  private int promotionCol;
  private GameStatus gameStatus;
  private List<String> whiteCaptures;
  private List<String> blackCaptures;

  public BoardResponse(
      String[][] squares,
      boolean whiteTurn,
      boolean pendingPromotion,
      boolean wasCastling,
      boolean inCheck,
      int promotionRow,
      int promotionCol,
      GameStatus gameStatus,
      List<String> whiteCaptures,
      List<String> blackCaptures) {
    this.squares = squares;
    this.whiteTurn = whiteTurn;
    this.pendingPromotion = pendingPromotion;
    this.wasCastling = wasCastling;
    this.inCheck = inCheck;
    this.promotionRow = promotionRow;
    this.promotionCol = promotionCol;
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

  public boolean getWasCastling() {
    return wasCastling;
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
