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
    
    /** Double array representing the chessboard */
    private char [][] board;
    /** Board dimension */
    private static final int SIZE = 8;
    
    /** Constant variable for the queen symbol */
    private static final char QUEEN_SYMBOL = 'X';
    /** Constant variable for the */
    private static final char EMPTY_SYMBOL = '#';
    
    void clearBoard() {
        for (int i=0; i<SIZE; i++) {
            for (int j=0; j<SIZE; j++ ) {
                board[i][j] = EMPTY_SYMBOL;
            }
        }
    }
    
    
    
}
