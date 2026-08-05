package chess.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import chess.board.Board;
import chess.movegen.CheckGenerator;
import chess.movegen.LegalMovesFilter;
import chess.movegen.MoveGenerator;
import chess.rules.GameState;
import chess.rules.GameStatus;

@SpringBootTest
class EngineApplicationTests {

  private Board board;
  private GameState gameState;

  @BeforeEach
  void setUp() {
    board = new Board();
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        board.getSquares()[r][c] = null;
      }
    }
    MoveGenerator moveGen = new MoveGenerator();
    CheckGenerator checkGen = new CheckGenerator(moveGen);
    LegalMovesFilter legalMovesFilter = new LegalMovesFilter(moveGen, checkGen);
    gameState = new GameState(legalMovesFilter, checkGen);
  }

  @Test
  void checkingForStaleMate() {
    board.getSquares()[2][0] = "bK";
    board.getSquares()[2][6] = "wK";
    board.getSquares()[3][2] = "wQ";
    board.getSquares()[3][3] = "bB";

    GameStatus status = gameState.evaluate('b', board);
    assertEquals(GameStatus.CHECKMATE, status);
  }

}
