/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Adam
 */
public class ChessboardModel {
    
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
    
    
    
}
