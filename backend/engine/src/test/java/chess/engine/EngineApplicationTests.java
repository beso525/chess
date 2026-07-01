package chess.engine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import chess.board.Board;
import chess.model.Move;
import chess.model.Position;
import chess.movegen.MoveGenerator;

@SpringBootTest
class EngineApplicationTests {

    @Test
    void contextLoads() {
        Board board = new Board();
        // for (int r = 0; r < 8; r++) {
        //     for (int c = 0; c < 8; c++) {
        //         board.getSquares()[r][c] = null;
        //     }
        // }
        board.getSquares()[4][4] = "wQ";

        MoveGenerator gen = new MoveGenerator();
        List<Move> moves = gen.genMove(new Position(4, 4), board);

        assertEquals(19, moves.size());
    }

}
