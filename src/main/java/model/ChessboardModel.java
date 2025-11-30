/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Adam
 */
public class ChessboardModel{
    
    /** Constructor initializing an empty chessboard */
    public ChessboardModel() {
        clearBoard();
    }
    
    /** Board dimension */
    private static final int SIZE = 8;
    /** Double array list representing the chessboard */
    private ArrayList<ArrayList<Character>> board = new ArrayList<ArrayList<Character>>();
    
    /** Getter for the board array list */
    public ArrayList<ArrayList<Character>> getBoard() {
        return board;
    }
    
    /** Constant variable for the queen symbol */
    private static final Character QUEEN_SYMBOL = 'X';
    /** Constant variable for the */
    private static final Character EMPTY_SYMBOL = '#';
    
    /** ArrayList for accepted positions */
    private ArrayList<Character> acceptedPositions = new ArrayList<Character>();
    
    /** Method that clears the chessboard */
    public void clearBoard() {
        board.clear(); 
        
        for (int i = 0; i < SIZE; i++) {
            //New ArrayList representing a row
            ArrayList<Character> row = new ArrayList<>();
            
            for (int j = 0; j < SIZE; j++) {
                //Filling newly created row with empty symbols
                row.add(EMPTY_SYMBOL);
            }
            
            //Adding the empty row to the main chessboard
            board.add(row);
        }
    }
    
    
    /** Method that places a queen */
    public void placeQueen(String pos) {
        String posUC = pos.toUpperCase();
        
        int col = posUC.charAt(0) - 'A';
        int row = posUC.charAt(1) - '1';
        
        board.get(row).set(col, QUEEN_SYMBOL);
    }
    
    public void isValidPlacement(String pos) throws InvalidPositionException {
        //Checks if given position is not null and is of correct length
        if (pos==null || pos.length()!=2) {
            throw new InvalidPositionException("User input is too short or too long.");
        }
        
        String posUC = pos.toUpperCase();   
        char c = posUC.charAt(0);
        char r = posUC.charAt(1);
        
        //Checks if given position is in the correct format: XY where X=[A,H] and Y=[1,8]
        if (c<'A' || c>'H' || r<'1' || r>'8') {
            throw new InvalidPositionException("Position out of range.");
        }
        
        int col = c - 'A';
        int row = r - '1';
        
        //Checks if given position is occupied
        if(board.get(row).get(col) != EMPTY_SYMBOL) {
            throw new InvalidPositionException("Position occupied by other queen");
        }   
    }
    
    /** Searches for a queen on the board, if found runs attackAnotherQueen to check if placement is correct for the puzzle */
    public boolean isSolutionValid() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board.get(r).get(c) == QUEEN_SYMBOL) {
                    if (attacksAnotherQueen(r, c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /** Checks whether a queen attacks any other queen. */
    private boolean attacksAnotherQueen(int row, int col) {
        // Check row and column
        for (int i = 0; i < SIZE; i++) {
            if (i != col && board.get(row).get(i) == QUEEN_SYMBOL) return true;
            if (i != row && board.get(i).get(col) == QUEEN_SYMBOL) return true;
        }

        // Check diagonals
        int[] dr = {-1, -1, 1, 1};
        int[] dc = {-1, 1, -1, 1};
        for (int d = 0; d < 4; d++) {
            int r = row + dr[d];
            int c = col + dc[d];
            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                if (board.get(r).get(c) == QUEEN_SYMBOL) return true;
                r += dr[d];
                c += dc[d];
            }
        }
        return false;
    }
    
}
