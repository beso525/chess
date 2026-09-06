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
import chess.movegen.MoveGenerator;
import chess.service.GameService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

  private final GameService gameService;
  private final MoveGenerator moveGenerator;
  private final LegalMovesFilter legalMovesFilter;

  public BoardController(GameService gameService, MoveGenerator moveGenerator, LegalMovesFilter legalMovesFilter) {
    this.gameService = gameService;
    this.moveGenerator = moveGenerator;
    this.legalMovesFilter = legalMovesFilter;
  }

  @GetMapping("/board")
  public BoardResponse getBoard() {
    return new BoardResponse(
        gameService.getBoard().getSquares(),
        gameService.getBoard().isWhiteTurn(),
        gameService.isPendingPromotion(),
        gameService.getBoard().getIsCastling(),
        gameService.isPlayerInCheck(),
        gameService.getBoard().getPromotionRow(),
        gameService.getBoard().getPromotionCol(),
        gameService.getGameStatus(),
        gameService.getWhiteCaptures(),
        gameService.getBlackCaptures(),
        gameService.getMoveHistory());
  }

  @PostMapping("/move")
  public ResponseEntity<BoardResponse> makeMove(@RequestBody MoveRequest move) {
    if (!gameService.getBoard().isCorrectTurn(move.getFromRow(), move.getFromCol())) {
      return ResponseEntity.badRequest().build();
    }
    gameService.makeMove(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
    if (!gameService.isPendingPromotion()) {
      gameService.getBoard().flipTurn();
    }

    return ResponseEntity.ok(
        new BoardResponse(
            gameService.getBoard().getSquares(),
            gameService.getBoard().isWhiteTurn(),
            gameService.isPendingPromotion(),
            gameService.getBoard().getIsCastling(),
            gameService.isPlayerInCheck(),
            gameService.getBoard().getPromotionRow(),
            gameService.getBoard().getPromotionCol(),
            gameService.getGameStatus(),
            gameService.getWhiteCaptures(),
            gameService.getBlackCaptures(),
            gameService.getMoveHistory()));
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
    return ResponseEntity.ok(
        new BoardResponse(
            gameService.getBoard().getSquares(),
            gameService.getBoard().isWhiteTurn(),
            gameService.isPendingPromotion(),
            gameService.getBoard().getIsCastling(),
            gameService.isPlayerInCheck(),
            gameService.getBoard().getPromotionRow(),
            gameService.getBoard().getPromotionCol(),
            gameService.getGameStatus(),
            gameService.getWhiteCaptures(),
            gameService.getBlackCaptures(),
            gameService.getMoveHistory()));
  }

  @PutMapping("/swap")
  public ResponseEntity<BoardResponse> promotePawn(@RequestBody PromotionRequest promote) {
    gameService.promotePawn(promote.getRow(), promote.getCol(), promote.getPiece());
    return ResponseEntity.ok(
        new BoardResponse(
            gameService.getBoard().getSquares(),
            gameService.getBoard().isWhiteTurn(),
            gameService.isPendingPromotion(),
            gameService.getBoard().getIsCastling(),
            gameService.isPlayerInCheck(),
            gameService.getBoard().getPromotionRow(),
            gameService.getBoard().getPromotionCol(),
            gameService.getGameStatus(),
            gameService.getWhiteCaptures(),
            gameService.getBlackCaptures(),
            gameService.getMoveHistory()));
  }

  @GetMapping("/check")
  public boolean isKingInCheck() {
    return gameService.isPlayerInCheck();
  }
}