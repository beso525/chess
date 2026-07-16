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

    public String getPiece(int row, int col) {
        return squares[row][col];
    }

    // i should later track whether the player is playing with white or black pieces
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
}
