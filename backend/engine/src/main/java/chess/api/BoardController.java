package chess.api;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chess.api.dto.MoveRequest;
import chess.model.BoardResponse;
import chess.model.Move;
import chess.model.Position;
import chess.movegen.MoveGenerator;
import chess.service.GameService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

    private final GameService gameService;
    private final MoveGenerator moveGenerator;

    public BoardController(GameService gameService, MoveGenerator moveGenerator) {
        this.gameService = gameService;
        this.moveGenerator = moveGenerator;
    }

    @GetMapping("/board")
    public BoardResponse getBoard() {
        return new BoardResponse(gameService.getBoard().getSquares());
    }

    @GetMapping("/legal-moves")
    public List<int[]> getLegalMoves(
            @RequestParam int row,
            @RequestParam int col
    ) {
        List<Move> moves = moveGenerator.genMove(new Position(row, col), gameService.getBoard());
        return moves.stream()
                .map(m -> new int[]{m.getToPos().row, m.getToPos().col})
                .toList();
    }

    @PostMapping("/move")
    public BoardResponse makeMove(@RequestBody MoveRequest move) {
        gameService.makeMove(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
        return new BoardResponse(gameService.getBoard().getSquares());
    }
}
