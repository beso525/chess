package chess.model;

public class BoardResponse {

    private String[][] squares;
    private boolean whiteTurn;

    public BoardResponse(String[][] squares, boolean whiteTurn) {
        this.squares = squares;
        this.whiteTurn = whiteTurn;
    }

    public String[][] getSquares() {
        return squares;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }
}
