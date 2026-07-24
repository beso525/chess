package chess.model;

public class BoardResponse {

    private String[][] squares;
    private boolean whiteTurn;
    private boolean pendingPromotion;
    private int promotionRow;
    private int promotionCol;

    public BoardResponse(
            String[][] squares,
            boolean whiteTurn,
            boolean pendingPromotion,
            int promotionRow,
            int promotionCol) {
        this.squares = squares;
        this.whiteTurn = whiteTurn;
        this.pendingPromotion = pendingPromotion;
        this.promotionRow = promotionRow;
        this.promotionCol = promotionCol;
    }

    public String[][] getSquares() {
        return squares;
    }

    public int getPromotionRow() {
        return promotionRow;
    }

    public int getPromotionCol() {
        return promotionCol;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public boolean isPendingPromotion() {
        return pendingPromotion;
    }
}
