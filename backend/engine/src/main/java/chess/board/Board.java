package chess.board;

public class Board {
  private String[][] squares = new String[8][8];

  public Board() {
    startingPosition();
  }

  private void startingPosition() {
    String[] backRank = {"R", "N", "B", "Q", "K", "B", "N", "R"};

        for (int i = 0; i < 8; i++) {
            squares[0][i] = "b" + backRank[i];
            squares[1][i] = "bP";

            squares[6][i] = "wP";
            squares[7][i] = "w" + backRank[i];
        }
  }

  public String[][] getSquares() {
    return squares;
  }

  public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
    String piece = squares[fromRow][fromCol];
    squares[fromRow][fromCol] = null;
    squares[toRow][toCol] = piece;
  }
}
