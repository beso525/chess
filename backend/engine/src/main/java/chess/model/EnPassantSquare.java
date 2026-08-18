package chess.model;

public class EnPassantSquare {
  public final int row;
  public final int col;

  public EnPassantSquare(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public boolean isAvailable() {
    return row != -1;
  }
}
