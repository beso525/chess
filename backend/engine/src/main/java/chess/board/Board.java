package chess.board;

import chess.model.CastlingRights;
import chess.model.EnPassantSquare;
import chess.model.MoveResult;
import chess.model.Position;

public class Board {

  private String[][] squares = new String[8][8];

  private boolean whiteKingMoved = false;
  private boolean blackKingMoved = false;
  private boolean blackKingSideRookMoved = false;
  private boolean blackQueenSideRookMoved = false;
  private boolean whiteKingSideRookMoved = false;
  private boolean whiteQueenSideRookMoved = false;

  private boolean isWhiteTurn = true;

  private int enPassantCol = -1;
  private int enPassantRow = -1;

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
    whiteKingMoved = false;
    blackKingMoved = false;
    blackKingSideRookMoved = false;
    blackQueenSideRookMoved = false;
    whiteKingSideRookMoved = false;
    whiteQueenSideRookMoved = false;
    isWhiteTurn = true;
    enPassantCol = -1;
    enPassantRow = -1;
    startingPosition();
  }

  public String[][] getSquares() {
    return squares;
  }

  public MoveResult applyMove(Position from, Position to) {
    String piece = getPiece(from.row, from.col);
    char color = piece.charAt(0);
    char type = piece.charAt(1);

    String pieceCaptured = getPiece(to.row, to.col);
    boolean isEnPassant = type == 'P' && from.col != to.col && getPiece(to.row, to.col) == null;
    String enPassantCapture = null;
    boolean pendingPromotion = false;

    // check if king has moved
    if (type == 'K') {
      if (color == 'w')
        whiteKingMoved = true;
      else
        blackKingMoved = true;
    }

    // check if and which rooks have moved
    if (type == 'R') {
      if (color == 'w') {
        if (from.col == 7)
          whiteKingSideRookMoved = true;
        if (from.col == 0)
          whiteQueenSideRookMoved = true;
      } else {
        if (from.col == 7)
          blackKingSideRookMoved = true;
        if (from.col == 0)
          blackQueenSideRookMoved = true;
      }

    }

    movePiece(from.row, from.col, to.row, to.col);
    boolean isCastling = type == 'K' && Math.abs(to.col - from.col) == 2;
    if (isCastling) {
      // where the rook moves if the king castled king side
      if (to.col == 6) {
        movePiece(from.row, 7, from.row, 5);
      }
      // and where it moves if the king castled queen side
      if (to.col == 2) {
        movePiece(from.row, 0, from.row, 3);
      }
    }

    // check if the pawn that moved
    if (type == 'P' && Math.abs(to.row - from.row) == 2) {
      enPassantCol = to.col;
      enPassantRow = (from.row + to.row) / 2;
    } else {
      enPassantCol = -1;
      enPassantRow = -1;
    }
    if (isEnPassant) {
      int capturedPawnRow = color == 'w' ? to.row + 1 : to.row - 1;
      enPassantCapture = getPiece(capturedPawnRow, to.col);
      getSquares()[capturedPawnRow][to.col] = null;
    }

    if ((color == 'w' && type == 'P' && to.row == 0)
        || (color == 'b' && type == 'P' && to.row == 7)) {
      pendingPromotion = true;
    }
    return new MoveResult(from, to, isCastling, piece, pieceCaptured, enPassantCapture, pendingPromotion);
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

  public boolean isWhiteTurn() {
    return isWhiteTurn;
  }

  public void flipTurn() {
    isWhiteTurn = !isWhiteTurn;
  }

  public boolean isCorrectTurn(int fromRow, int fromCol) {
    String piece = getPiece(fromRow, fromCol);
    if (piece == null) {
      return false;
    }

    char color = piece.charAt(0);
    return (isWhiteTurn && color == 'w' || !isWhiteTurn && color == 'b');
  }

  public CastlingRights getCastlingRights() {
    return new CastlingRights(
        whiteKingMoved, whiteKingSideRookMoved, whiteQueenSideRookMoved,
        blackKingMoved, blackKingSideRookMoved, blackQueenSideRookMoved);
  }

  public EnPassantSquare getEnPassantSquare() {
    return new EnPassantSquare(enPassantRow, enPassantCol);
  }

}
