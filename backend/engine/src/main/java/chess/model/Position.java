package chess.model;

public class Position {

  public final int row;
  public final int col;

  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public String toString() {
    return "Row " + row + " and col " + col;
  }
}
