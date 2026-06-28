package chess;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import chess.board.Board;
import chess.model.Move;
import chess.model.Position;
import chess.movegen.MoveGenerator;

@SpringBootApplication
public class EngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineApplication.class, args);
        Board board = new Board();
        MoveGenerator gen = new MoveGenerator();

        board.getSquares()[4][4] = "wB";
        List<Move> moves = gen.genMove(new Position(4, 4), board);

        int counter = 0;
        for (Move move : moves) {
            counter++;
            System.out.println(counter + ") King can move to: row="
                    + move.getToPos().row
                    + " col="
                    + move.getToPos().col);
        }
    }

}
