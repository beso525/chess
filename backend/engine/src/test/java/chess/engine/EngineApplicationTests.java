package chess.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import chess.board.Board;
import chess.model.CastlingRights;
import chess.model.EnPassantSquare;
import chess.movegen.CastlingMoveGenerator;
import chess.movegen.CheckGenerator;
import chess.movegen.LegalMovesFilter;
import chess.movegen.MoveGenerator;
import chess.rules.GameState;
import chess.rules.GameStatus;

@SpringBootTest
class EngineApplicationTests {

  private Board board;
  private GameState gamestate;
  private CastlingRights castlingRights;
  private EnPassantSquare enPassantSquare;

  @BeforeEach
  void setUp() {
    board = new Board();
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        board.getSquares()[r][c] = null;
      }
    }

    MoveGenerator moveGenerator = new MoveGenerator();
    CheckGenerator checkGenerator = new CheckGenerator(moveGenerator);
    CastlingMoveGenerator castlingMoveGenerator = new CastlingMoveGenerator(checkGenerator);
    LegalMovesFilter legalMovesFilter = new LegalMovesFilter(moveGenerator, checkGenerator, castlingMoveGenerator);
    gamestate = new GameState(legalMovesFilter, checkGenerator);

    castlingRights = new CastlingRights(true, true, true, true, true, true);
    enPassantSquare = new EnPassantSquare(-1, -1);
  }

  @Test
  void checkingForStaleMate() {
    board.getSquares()[0][0] = "wK";

    board.getSquares()[1][2] = "bQ";
    board.getSquares()[2][1] = "bK";

    GameStatus status = gamestate.evaluate('w', board, castlingRights, enPassantSquare);
    assertEquals(GameStatus.CHECKMATE, status);
  }
}
