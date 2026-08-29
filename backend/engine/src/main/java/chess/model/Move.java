package chess.model;

public class Move {

  private Position from;
  private Position to;
  private boolean isPromotion;

  public Move(Position from, Position to) {
    this.from = from;
    this.to = to;
  }

  public Position getFromPos() {
    return from;
  }

  public Position getToPos() {
    return to;
  }

  public boolean isPromotion() {
    return isPromotion;
  }

  @Override
  public String toString() {
    return "Move from " + from + " to " + to + "." + " There is promotion? " + isPromotion;
  }
}
