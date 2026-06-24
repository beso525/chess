package chess.model;

public class BoardResponse {

    private String[][] squares;

    public BoardResponse(String[][] squares) {
        this.squares = squares;
    }

    public String[][] getSquares() {
        return squares;
    }
}
