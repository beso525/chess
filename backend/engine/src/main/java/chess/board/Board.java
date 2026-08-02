package chess.board;

public class Board {

  private String[][] squares = new String[8][8];

  public Board() {
    startingPosition();
  }

  private void startingPosition() {
    String[] backRank = { "R", "N", "B", "Q", "K", "B", "N", "R" };

    for (int i = 0; i < 8; i++) {
      squares[0][i] = "b" + backRank[i];
      squares[1][i] = "bP";

      squares[6][i] = "wP";
      squares[7][i] = "w" + backRank[i];
    }
  }

  public final void resetStartingPosition() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        squares[i][j] = null;
      }
    }
    startingPosition();
  }

  public String[][] getSquares() {
    return squares;
  }

  public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
    String piece = squares[fromRow][fromCol];
    squares[fromRow][fromCol] = null;
    squares[toRow][toCol] = piece;
  }

  public String getPiece(int row, int col) {
    return squares[row][col];
  }

  public boolean isWhite(int row, int col) {
    String piece = squares[row][col];
    return piece != null && piece.charAt(0) == 'w';
  }

  public boolean isEmpty(int row, int col) {
    return squares[row][col] == null;
  }

  public boolean isEnemy(int row, int col, char myColor) {
    if (isEmpty(row, col)) {
      return false;
    }
    return squares[row][col].charAt(0) != myColor;
  }

  public Board copy() {
    Board copy = new Board();
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        copy.getSquares()[r][c] = this.squares[r][c];
      }
    }
    return copy;
  }

}
