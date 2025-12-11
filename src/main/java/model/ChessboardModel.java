package model;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.*;

/**
 * Represents the data model for the 8 Queens Puzzle.
 * It stores the state of the chessboard and provides methods to manipulate and validate it.
 *
 * @author Adam
 * @version 4.0
 */
@Getter
public class ChessboardModel {
    
    /** Board dimension (8x8). */
    private static final int SIZE = 8;
    
    /** * Double array list representing the chessboard grid.
     * Stores the state of each square (EMPTY or QUEEN).
     */
    private ArrayList<ArrayList<SquareState>> board = new ArrayList<ArrayList<SquareState>>();
    
    /** * Constructor initializing an empty chessboard.
     */
    public ChessboardModel() {
        clearBoard();
    }
    
    /** * Clears the chessboard by setting all squares to EMPTY.
     * Uses Stream API to generate rows and columns.
     */
    public void clearBoard() {
        board.clear(); 
        
        IntStream.range(0, SIZE).forEach(i -> {
            ArrayList<SquareState> row = IntStream.range(0, SIZE)
                    .mapToObj(j -> SquareState.EMPTY)
                    .collect(Collectors.toCollection(ArrayList::new));
            board.add(row);
        });
    }
    
    /**
     * Parses a string representation of a position into a Position object.
     *
     * @param pos The position string (e.g., "A1").
     * @return A {@link Position} record containing row and column indices.
     */
    public Position parse(String pos) {
        String posUC = pos.toUpperCase();
        int c = posUC.charAt(0) - 'A';
        int r = posUC.charAt(1) - '1';
        return new Position(r,c);
    }
    
    /** * Places a queen at the specified position on the board.
     *
     * @param pos The position string where the queen should be placed.
     */
    public void placeQueen(String pos) {
        Position p = parse(pos);
        board.get(p.row()).set(p.col(), SquareState.QUEEN);
    }
    
    /**
     * Validates if a position string is syntactically correct and if the square is empty.
     *
     * @param pos The position string to validate.
     * @throws InvalidPositionException If the input is null, bad length, out of bounds, or occupied.
     */
    public void isValidPlacement(String pos) throws InvalidPositionException {
        // Checks if given position is not null and is of correct length
        if (pos == null || pos.length() != 2) {
            throw new InvalidPositionException("User input is too short or too long.");
        }
        
        Position p = parse(pos);
        
        // Checks if given position is in the correct format: XY where X=[A,H] and Y=[1,8]
        if (p.col() < 0 || p.col() > 7 || p.row() < 0 || p.row() > 7) {
            throw new InvalidPositionException("Position out of range.");
        }
        
        // Checks if given position is occupied
        if (board.get(p.row()).get(p.col()) != SquareState.EMPTY) {
            throw new InvalidPositionException("Position occupied by other queen");
        }   
    }

    /** * Scans the chessboard and returns a list of positions occupied by queens.
     * <p>
     * <b>Stream API Implementation:</b>
     * This method uses Java Streams and lambda expressions to filter the collection
     * and map coordinates to Position objects.
     * </p>
     *
     * @return An ArrayList of {@link Position} objects representing queen locations.
     */
    private ArrayList<Position> getQueenPositions() {
        return IntStream.range(0, SIZE)
                .boxed()
                .flatMap(r -> IntStream.range(0, SIZE)
                        .filter(c -> board.get(r).get(c) == SquareState.QUEEN)
                        .mapToObj(c -> new Position(r, c)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    /** * Checks if the current arrangement of queens is a valid solution to the puzzle.
     * A solution is valid if no queen attacks another.
     *
     * @return true if the solution is valid, false otherwise.
     */
    public boolean isSolutionValid() {
        ArrayList<Position> foundQueens = getQueenPositions();
        
        // Requirement: Keep the for-each loop here
        for (Position p : foundQueens) {
            if (attacksAnotherQueen(p))
                return false;
        }
        
        return true;
    }
    
    /** * Checks whether a specific queen attacks any other queen on the board.
     * Checks rows, columns, and diagonals.
     *
     * @param p The position of the queen to check.
     * @return true if the queen attacks another, false otherwise.
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
}