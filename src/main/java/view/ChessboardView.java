/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Adam
 */
public class ChessboardView {
    
    /** Method that displays the chessboard with all 8 queens placed on it */
    public void displayChessboard(char[][] board ) {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
