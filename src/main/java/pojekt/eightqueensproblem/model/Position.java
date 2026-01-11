package pojekt.eightqueensproblem.model;

/**
 * An immutable record representing a 2D position on the chessboard.
 *
 * @author Adam
 * @version 1.0
 * @param row The row index (0-7).
 * @param col The column index (0-7).
 */
public record Position(int row, int col) {
}