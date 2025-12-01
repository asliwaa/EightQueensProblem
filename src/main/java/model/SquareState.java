package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing the state of a single square on the chessboard.
 *
 * @author Adam
 * @version 3.0
 */
@Getter
@RequiredArgsConstructor
public enum SquareState {
    
    /** Represents an empty square on the board. */
    EMPTY("#"), 
    
    /** Represents a square occupied by a queen. */
    QUEEN("X");
    
    /** The string symbol associated with the state. */
    private final String symbol;
    
    /**
     * Overriding toString method to display actual symbol (# or X) instead of its name.
     *
     * @return The string representation of the state.
     */
    @Override
    public String toString() {
        return symbol;
    }
}