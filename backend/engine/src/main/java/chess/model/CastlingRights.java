package chess.model;

public class CastlingRights {

  public final boolean whiteKingMoved;
  public final boolean blackKingMoved;
  public final boolean blackKingSideRookMoved;
  public final boolean blackQueenSideRookMoved;
  public final boolean whiteKingSideRookMoved;
  public final boolean whiteQueenSideRookMoved;

  public CastlingRights(
      boolean whiteKingMoved,
      boolean whiteKingSideRookMoved,
      boolean whiteQueenSideRookMoved,
      boolean blackKingMoved,
      boolean blackKingSideRookMoved,
      boolean blackQueenSideRookMoved) {
    this.whiteKingMoved = whiteKingMoved;
    this.whiteKingSideRookMoved = whiteKingSideRookMoved;
    this.whiteQueenSideRookMoved = whiteQueenSideRookMoved;
    this.blackKingMoved = blackKingMoved;
    this.blackKingSideRookMoved = blackKingSideRookMoved;
    this.blackQueenSideRookMoved = blackQueenSideRookMoved;
  }
}
