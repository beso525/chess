package chess.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import chess.board.Board;

@SpringBootTest
class EngineApplicationTests {

  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        board.getSquares()[r][c] = null;
      }
    }
  }

  @Test
  void checkingForStaleMate() {
    setUp();
    board.getSquares()[7][4] = "wK";
    board.getSquares()[7][5] = "wN";
    board.getSquares()[7][7] = "wR";

    board.getSquares()[7][0] = "wR";
  }

}
