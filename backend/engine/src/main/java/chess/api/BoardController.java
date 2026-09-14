package chess.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chess.api.dto.MoveRequest;
import chess.api.dto.PromotionRequest;
import chess.model.BoardResponse;
import chess.model.Move;
import chess.model.Position;
import chess.movegen.LegalMovesFilter;
import chess.service.GameService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

  private final GameService gameService;
  private final LegalMovesFilter legalMovesFilter;

  public BoardController(GameService gameService, LegalMovesFilter legalMovesFilter) {
    this.gameService = gameService;
    this.legalMovesFilter = legalMovesFilter;
  }

  private BoardResponse buildResponse() {
    return new BoardResponse(
        gameService.getBoard().getSquares(),
        gameService.isWhiteTurn(),
        gameService.isPendingPromotion(),
        gameService.getIsCastling(),
        gameService.isPlayerInCheck(),
        gameService.getPromotionRow(),
        gameService.getPromotionCol(),
        gameService.getGameStatus(),
        gameService.getWhiteCaptures(),
        gameService.getBlackCaptures(),
        gameService.getMoveHistory());
  }

  @GetMapping("/board")
  public BoardResponse getBoard() {
    return buildResponse();
  }

  @PostMapping("/move")
  public ResponseEntity<BoardResponse> makeMove(@RequestBody MoveRequest move) {

    Position from = new Position(move.getFromRow(), move.getFromCol());
    Position to = new Position(move.getToRow(), move.getToCol());

    if (!gameService.isCorrectTurn(from)) {
      return ResponseEntity.badRequest().build();
    }
    gameService.makeMove(from, to);

    if (!gameService.isPendingPromotion()) {
      gameService.flipTurn();
    }

    return ResponseEntity.ok(buildResponse());
  }

  @GetMapping("/legal-moves")
  public List<Map<String, Integer>> getLegalMoves(
      @RequestParam int row,
      @RequestParam int col) {
    List<Move> moves = legalMovesFilter.filterLegalMoves(
        new Position(row, col),
        gameService.getBoard(),
        gameService.getBoard().getCastlingRights(),
        gameService.getBoard().getEnPassantSquare());

    return moves.stream()
        .map(m -> {
          Map<String, Integer> map = new HashMap<>();
          map.put("toRow", m.getToPos().row);
          map.put("toCol", m.getToPos().col);
          return map;
        }).toList();
  }

  @PostMapping("/reset")
  public ResponseEntity<BoardResponse> resetBoard() {
    gameService.resetBoard();
    return ResponseEntity.ok(buildResponse());
  }

  @PutMapping("/swap")
  public ResponseEntity<BoardResponse> promotePawn(@RequestBody PromotionRequest promote) {
    gameService.promotePawn(promote.getRow(), promote.getCol(), promote.getPiece());
    return ResponseEntity.ok(buildResponse());
  }

  @GetMapping("/check")
  public boolean isKingInCheck() {
    return gameService.isPlayerInCheck();
  }
}