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

    Board board = new Board();
    MoveGenerator gen = new MoveGenerator();

    @Test
    void contextLoads() {
        board.getSquares()[6][4] = "wP";
        board.getSquares()[5][3] = "bP";
        List<Move> moves = gen.genMove(new Position(6, 4), board);
        for (Move move : moves) {
            System.out.println(move);
        }
        assertEquals(3, moves.size());
    }

}
