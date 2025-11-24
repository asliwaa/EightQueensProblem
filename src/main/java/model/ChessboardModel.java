/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.InvalidPositionException;

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
    /** Double array representing the chessboard */
    private char [][] board = new char[SIZE][SIZE];
    
    /** Getter for the board array */
    public char[][] getBoard() {
        return board;
    }
    
    /** Constant variable for the queen symbol */
    private static final char QUEEN_SYMBOL = 'X';
    /** Constant variable for the */
    private static final char EMPTY_SYMBOL = '#';
    
    /** Array for accepted positions */
    private char[] acceptedPositions = new char[SIZE];
    
    /** Method that clears the chessboard, used in the constructor */
    public void clearBoard() {
        for (int i=0; i<SIZE; i++) {
            for (int j=0; j<SIZE; j++ ) {
                board[i][j] = EMPTY_SYMBOL;
            }
        }
    }
    
    /** Method that takes user input for a queen's position and places it on the chessboard */
    public void placeQueen(String pos) {
        String posUC = pos.toUpperCase();
        
        int col = posUC.charAt(0) - 'A';
        int row = posUC.charAt(1) - '1';
        
        board[row][col] = QUEEN_SYMBOL;
        
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
        if(board[row][col] != EMPTY_SYMBOL) {
            throw new InvalidPositionException("Position occupied by other queen");
        }   
    }
    
    public boolean isSolutionValid() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == QUEEN_SYMBOL) {
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
            if (i != col && board[row][i] == QUEEN_SYMBOL) return true;
            if (i != row && board[i][col] == QUEEN_SYMBOL) return true;
        }

        // Check diagonals
        int[] dr = {-1, -1, 1, 1};
        int[] dc = {-1, 1, -1, 1};
        for (int d = 0; d < 4; d++) {
            int r = row + dr[d];
            int c = col + dc[d];
            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                if (board[r][c] == QUEEN_SYMBOL) return true;
                r += dr[d];
                c += dc[d];
            }
        }
        return false;
    }
    
}
