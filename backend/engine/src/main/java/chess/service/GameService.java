package chess.service;

import org.springframework.stereotype.Service;

import chess.board.Board;

@Service
public class GameService {

    private final Board board = new Board();
    private boolean isWhiteTurn = true;

    public Board getBoard() {
        return board;
    }

    public void resetBoard() {
        board.resetStartingPosition();
        isWhiteTurn = true;
    }

    public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        board.movePiece(fromRow, fromCol, toRow, toCol);
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
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
}
