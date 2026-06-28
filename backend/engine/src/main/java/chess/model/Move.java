package chess.model;

public class Move {

    private Position from;
    private Position to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position getFromPos() {
        return from;
    }

    public Position getToPos() {
        return to;
    }
}
