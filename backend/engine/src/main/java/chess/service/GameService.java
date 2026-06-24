package chess.service;

import org.springframework.stereotype.Service;

import chess.board.Board;

@Service
public class GameService {

    private final Board board = new Board();

    public Board getBoard() {
        return board;
    }

    public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        board.movePiece(fromRow, fromCol, toRow, toCol);
    }
}
