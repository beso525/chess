package chess.board;

import chess.model.CastlingRights;
import chess.model.EnPassantSquare;

public class Board {

  private String[][] squares = new String[8][8];

  private int promotionRow = -1;
  private int promotionCol = -1;

  private boolean whiteKingMoved = false;
  private boolean blackKingMoved = false;
  private boolean blackKingSideRookMoved = false;
  private boolean blackQueenSideRookMoved = false;
  private boolean whiteKingSideRookMoved = false;
  private boolean whiteQueenSideRookMoved = false;
  private boolean isCastling = false;

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

  // GETTERS AND SETTERS
  public CastlingRights getCastlingRights() {
    return new CastlingRights(
        whiteKingMoved, whiteKingSideRookMoved, whiteQueenSideRookMoved,
        blackKingMoved, blackKingSideRookMoved, blackQueenSideRookMoved);
  }

  public EnPassantSquare getEnPassantSquare() {
    return new EnPassantSquare(enPassantRow, enPassantCol);
  }

  public boolean getIsCastling() {
    return isCastling;
  }

  public void setIsCastling(boolean isCastling) {
    this.isCastling = isCastling;
  };

  public int getPromotionRow() {
    return promotionRow;
  }

  public void setPromotionRow(int promotionRow) {
    this.promotionRow = promotionRow;
  }

  public int getPromotionCol() {
    return promotionCol;
  }

  public void setPromotionCol(int promotionCol) {
    this.promotionCol = promotionCol;
  }

  public boolean getWhiteKingMoved() {
    return whiteKingMoved;
  }

  public void setWhiteKingMoved(boolean whiteKingMoved) {
    this.whiteKingMoved = whiteKingMoved;
  }

  public boolean getBlackKingMoved() {
    return blackKingMoved;
  }

  public void setBlackKingMoved(boolean blackKingMoved) {
    this.blackKingMoved = blackKingMoved;
  }

  public boolean getWhiteKingSideRookMoved() {
    return whiteKingSideRookMoved;
  }

  public void setWhiteKingSideRookMoved(boolean whiteKingSideRookMoved) {
    this.whiteKingSideRookMoved = whiteKingSideRookMoved;
  }

  public boolean getBlackKingSideRookMoved() {
    return blackKingSideRookMoved;
  }

  public void setBlackKingSideRookMoved(boolean blackKingSideRookMoved) {
    this.blackKingSideRookMoved = blackKingSideRookMoved;
  }

  public boolean getWhiteQueenSideRookMoved() {
    return whiteQueenSideRookMoved;
  }

  public void setWhiteQueenSideRookMoved(boolean whiteQueenSideRookMoved) {
    this.whiteQueenSideRookMoved = whiteQueenSideRookMoved;
  }

  public boolean getBlackQueenSideRookMoved() {
    return blackQueenSideRookMoved;
  }

  public void setBlackQueenSideRookMoved(boolean blackQueenSideRookMoved) {
    this.blackQueenSideRookMoved = blackQueenSideRookMoved;
  }

  public void setEnPassantCol(int enPassantCol) {
    this.enPassantCol = enPassantCol;
  }

  public void setEnPassantRow(int enPassantRow) {
    this.enPassantRow = enPassantRow;
  }
}
