package pojekt.eightqueensproblem.model;

/**
 * Represents the state of a single square on the chessboard.
 * Can be either empty or occupied by a queen.
 *
 * @author Adam
 * @version 5.0
 */
public enum SquareState {
    
    /**
     * Represents a square occupied by a queen.
     */
    QUEEN,
    
    /**
     * Represents an empty square.
     */
    EMPTY;

    /**
     * Returns the string representation of the state.
     * Used for textual representation of the board.
     *
     * @return "X" if the square is occupied by a queen, "_" if empty.
     */
    @Override
    public String toString() {
        if (this == QUEEN) return "X";
        return "_";
    }
}