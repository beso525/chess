package chess.model;

public class MoveRecord {

  private Position from;
  private Position to;

  private String pieceMoved;
  private String pieceCaptured;

  private String notation;

  private CastlingRights prevCastlingRights;
  private EnPassantSquare prevEnPassantSquare;
  private boolean wasPromotion;
  private String promotedFrom;

  public MoveRecord(Position from,
      Position to,
      String pieceMoved,
      String pieceCaptured,
      String notation,
      CastlingRights prevCastlingRights,
      EnPassantSquare prevEnPassantSquare) {
    this.from = from;
    this.to = to;
    this.pieceMoved = pieceMoved;
    this.pieceCaptured = pieceCaptured;
    this.notation = notation;
    this.prevCastlingRights = prevCastlingRights;
    this.prevEnPassantSquare = prevEnPassantSquare;
  }

  @Override
  public String toString() {
    return notation;
  }
}
