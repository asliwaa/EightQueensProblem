package pojekt.eightqueensproblem.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Represents the chessboard model and game logic.
 * Manages the state of the board, validates moves, and checks for winning conditions.
 *
 * @author Adam
 * @version 5.0
 */
@Getter
public class ChessboardModel {
    
    /**
     * The size of the chessboard (8x8).
     */
    private static final int SIZE = 8;

    /**
     * The 2D grid representing the chessboard.
     * Stores the state of each square (EMPTY or QUEEN).
     */
    private ArrayList<ArrayList<SquareState>> board = new ArrayList<>();
    
    /**
     * A list storing the history of game events (logs).
     * Shared across the application life cycle in the current architecture.
     */
    private List<String> history = new ArrayList<>();
    
    /**
     * Constructs a new ChessboardModel and initializes the board.
     */
    public ChessboardModel() {
        clearBoard();
        history.add("New board initialized.");
    }
    
    /**
     * Adds a log entry to the game history.
     *
     * @param message The text message to be added to the history.
     */
    public void addHistoryLog(String message) {
        history.add(message);
    }
    
    /**
     * Resets the board to the initial empty state.
     * All squares are set to {@link SquareState#EMPTY}.
     */
    public void clearBoard() {
        board.clear(); 
        for (int i = 0; i < SIZE; i++) {
            ArrayList<SquareState> row = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                row.add(SquareState.EMPTY);
            }
            board.add(row);
        }
    }
    
    /**
     * Parses a string representation of a position into a {@link Position} object.
     * Converts chess notation (e.g., "A1") to zero-based indices.
     *
     * @param pos The position string (e.g., "A1", "H8").
     * @return A {@link Position} object with corresponding row and column indices.
     * @throws NullPointerException if the input string is null.
     * @throws IndexOutOfBoundsException if the input string format is invalid.
     */
    public Position parse(String pos) {
        String posUC = pos.toUpperCase();
        int c = posUC.charAt(0) - 'A';
        int r = posUC.charAt(1) - '1';
        return new Position(r, c);
    }
    
    /**
     * Places a queen at the specified position.
     * Assumes the position is valid (validation should be performed beforehand).
     *
     * @param pos The position string where the queen should be placed.
     */
    public void placeQueen(String pos) {
        Position p = parse(pos);
        board.get(p.row()).set(p.col(), SquareState.QUEEN);
    }
    
    /**
     * Validates if a queen can be placed at the specified position.
     * Checks for format correctness, boundaries, and whether the square is already occupied.
     *
     * @param pos The position string to validate.
     * @throws InvalidPositionException if the format is wrong, coordinates are out of bounds, or the field is occupied.
     */
    public void isValidPlacement(String pos) throws InvalidPositionException {
        if (pos == null || pos.length() != 2) {
            throw new InvalidPositionException("User input is too short or too long.");
        }
        
        Position p = parse(pos);
        
        if (p.col() < 0 || p.col() > 7 || p.row() < 0 || p.row() > 7) {
            throw new InvalidPositionException("Position out of range.");
        }
        
        if (board.get(p.row()).get(p.col()) != SquareState.EMPTY) {
            throw new InvalidPositionException("Position occupied by other queen");
        }   
    }
    
    /**
     * Retrieves the positions of all queens currently on the board.
     *
     * @return A list of {@link Position} objects representing queen locations.
     */
    private List<Position> getQueenPositions() {
        List<Position> queens = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board.get(r).get(c) == SquareState.QUEEN) {
                    queens.add(new Position(r, c));
                }
            }
        }
        return queens;
    }
    
    /**
     * Checks if the current arrangement of queens is a valid solution.
     * A solution is valid if no queen attacks another.
     *
     * @return {@code true} if no queens are attacking each other, {@code false} otherwise.
     */
    public boolean isSolutionValid() {
        List<Position> foundQueens = getQueenPositions();
        return foundQueens.stream()
                .noneMatch(p -> attacksAnotherQueen(p));
    }
    
    /**
     * Checks if a specific queen attacks any other queen on the board.
     * Checks rows, columns, and diagonals.
     *
     * @param p The position of the queen to check.
     * @return {@code true} if the queen at position {@code p} attacks another queen, {@code false} otherwise.
     */
    private boolean attacksAnotherQueen(Position p) {
        // Check row and column
        for (int i = 0; i < SIZE; i++) {
            if (i != p.col() && board.get(p.row()).get(i) == SquareState.QUEEN) return true;
            if (i != p.row() && board.get(i).get(p.col()) == SquareState.QUEEN) return true;
        }

        // Check diagonals
        int[] dr = {-1, -1, 1, 1};
        int[] dc = {-1, 1, -1, 1};
        for (int d = 0; d < 4; d++) {
            int r = p.row() + dr[d];
            int c = p.col() + dc[d];
            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                if (board.get(r).get(c) == SquareState.QUEEN) return true;
                r += dr[d];
                c += dc[d];
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the entire board using Stream API.
     *
     * @return A formatted string representing the board state.
     */
    @Override
    public String toString() {
        return board.stream()
            .map(row -> row.stream()
                           .map(square -> square.toString())
                           .collect(java.util.stream.Collectors.joining(" ")))
            .collect(java.util.stream.Collectors.joining("\n"));
    }
}