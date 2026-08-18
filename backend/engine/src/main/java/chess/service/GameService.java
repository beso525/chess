package chess.service;

import org.springframework.stereotype.Service;

import chess.board.Board;
import chess.model.CastlingRights;
import chess.model.EnPassantSquare;
import chess.movegen.CheckGenerator;
import chess.rules.GameState;
import chess.rules.GameStatus;

@Service
public class GameService {

  private final Board board = new Board();
  private final CheckGenerator checkGenerator;
  private final GameState gameState;

  private boolean isWhiteTurn = true;

  private boolean pendingPromotion = false;
  private int promotionRow = -1;
  private int promotionCol = -1;

  private boolean whiteKingMoved = false;
  private boolean blackKingMoved = false;
  private boolean blackKingSideRookMoved = false;
  private boolean blackQueenSideRookMoved = false;
  private boolean whiteKingSideRookMoved = false;
  private boolean whiteQueenSideRookMoved = false;

  private int enPassantCol = -1;
  private int enPassantRow = -1;

  public Board getBoard() {
    return board;
  }

  public GameService(CheckGenerator checkGenerator, GameState gameState) {
    this.checkGenerator = checkGenerator;
    this.gameState = gameState;
  }

  public int getPromotionRow() {
    return promotionRow;
  }

  public int getPromotionCol() {
    return promotionCol;
  }

  public boolean isPendingPromotion() {
    return pendingPromotion;
  }

  public void resetBoard() {
    board.resetStartingPosition();
    isWhiteTurn = true;
    pendingPromotion = false;
    promotionRow = -1;
    promotionCol = -1;
    whiteKingMoved = false;
    blackKingMoved = false;
    blackKingSideRookMoved = false;
    blackQueenSideRookMoved = false;
    whiteKingSideRookMoved = false;
    whiteQueenSideRookMoved = false;
  }

  public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
    String piece = board.getPiece(fromRow, fromCol);
    char color = piece.charAt(0);
    char type = piece.charAt(1);
    if (type == 'K') {
      if (color == 'w') {
        whiteKingMoved = true;
      } else {
        blackKingMoved = true;
      }
    }

    if (type == 'R') {
      if (color == 'w') {
        if (fromCol == 7)
          whiteKingSideRookMoved = true;
        if (fromCol == 0)
          whiteQueenSideRookMoved = true;
      } else {
        if (fromCol == 7)
          blackKingSideRookMoved = true;
        if (fromCol == 0)
          blackQueenSideRookMoved = true;
      }
    }

    if ((color == 'w' && type == 'P' && toRow == 0)
        || (color == 'b' && type == 'P' && toRow == 7)) {
      pendingPromotion = true;
      promotionRow = toRow;
      promotionCol = toCol;
    }
    board.movePiece(fromRow, fromCol, toRow, toCol);
    if (type == 'K' && Math.abs(toCol - fromCol) == 2) {
      if (toCol == 6) {
        board.movePiece(fromRow, 7, fromRow, 5);
      }

      if (toCol == 2) {
        board.movePiece(fromRow, 0, fromRow, 3);
      }
    }

    if (type == 'P' && Math.abs(toRow - fromRow) == 2) {
      enPassantCol = toCol;
      enPassantRow = (fromRow + toRow) / 2; // ?
    } else {
      enPassantCol = -1;
      enPassantRow = -1;
    }
    if (type == 'P' && fromCol != toCol) {
      System.out.println("detected");
      int capture = color == 'w' ? toRow + 1 : toRow - 1;
      board.getSquares()[capture][toCol] = null;
    }
  }

  public boolean isWhiteTurn() {
    return isWhiteTurn;
  }

  public void promotePawn(int row, int col, String chosenPiece) {
    char color = board.getPiece(row, col).charAt(0);
    board.getSquares()[row][col] = color + chosenPiece;
    pendingPromotion = false;
    promotionRow = -1;
    promotionCol = -1;
    isWhiteTurn = !isWhiteTurn;
  }

  public boolean isCorrectTurn(int fromRow, int fromCol) {
    String piece = board.getPiece(fromRow, fromCol);
    if (piece == null) {
      return false;
    }

    char color = piece.charAt(0);
    return (isWhiteTurn && color == 'w' || !isWhiteTurn && color == 'b');
  }

  public void flipTurn() {
    isWhiteTurn = !isWhiteTurn;
  }

  public boolean isPlayerInCheck() {
    char kingColor = isWhiteTurn() ? 'w' : 'b';
    return checkGenerator.isInCheck(kingColor, getBoard(), getCastlingRights(), getEnPassantSquare());
  }

  public GameStatus getGameStatus() {
    char color = isWhiteTurn ? 'w' : 'b';
    return gameState.evaluate(color, getBoard(), getCastlingRights(), getEnPassantSquare());
  }

  public boolean getWhiteKingMoved() {
    return whiteKingMoved;
  }

  public boolean getBlackKingMoved() {
    return blackKingMoved;
  }

  public boolean getWhiteKingSideRookMoved() {
    return whiteKingSideRookMoved;
  }

  public boolean getBlackKingSideRookMoved() {
    return blackKingSideRookMoved;
  }

  public boolean getWhiteQueenSideRookMoved() {
    return whiteQueenSideRookMoved;
  }

  public boolean getBlackQueenSideRookMoved() {
    return blackQueenSideRookMoved;
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