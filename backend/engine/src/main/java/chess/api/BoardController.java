package chess.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chess.api.dto.MoveRequest;
import chess.model.BoardResponse;
import chess.service.GameService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

    private final GameService gameService;

    public BoardController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/board")
    public BoardResponse getBoard() {
        return new BoardResponse(gameService.getBoard().getSquares());
    }

    @PostMapping("/move")
    public BoardResponse makeMove(@RequestBody MoveRequest move) {
        gameService.makeMove(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
        return new BoardResponse(gameService.getBoard().getSquares());
    }
}
