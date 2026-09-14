package chess.model;

public class MoveResult {
  private final Position from;
  private final Position to;
  private final boolean wasCastling;
  private final String pieceMoved;
  private final String pieceCaptured;
  private final String enPassantCapture;
  private final boolean isPendingPromotion;

  public MoveResult(Position from, Position to, boolean wasCastling, String pieceMoved, String pieceCaptured,
      String enPassantCapture, boolean isPendingPromotion) {
    this.from = from;
    this.to = to;
    this.wasCastling = wasCastling;
    this.pieceMoved = pieceMoved;
    this.pieceCaptured = pieceCaptured;
    this.enPassantCapture = enPassantCapture;
    this.isPendingPromotion = isPendingPromotion;
  }

  public Position getFromPos() {
    return from;
  }

  public Position getToPos() {
    return to;
  }

  public boolean isCastling() {
    return wasCastling;
  }

  public String getPieceMoved() {
    return pieceMoved;
  }

  public String getPieceCaptured() {
    return pieceCaptured;
  }

  public String getEnPassantCapture() {
    return enPassantCapture;
  }

  public boolean isPendingPromotion() {
    return isPendingPromotion;
  }
}
