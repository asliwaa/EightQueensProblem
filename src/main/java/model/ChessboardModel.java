/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import lombok.*;

/**
 *
 * @author Adam
 */
@Getter
public class ChessboardModel{
    
    /** Constructor initializing an empty chessboard */
    public ChessboardModel() {
        clearBoard();
    }
    
    /** Board dimension */
    private static final int SIZE = 8;
    /** Double array list representing the chessboard */
    private ArrayList<ArrayList<SquareState>> board = new ArrayList<ArrayList<SquareState>>();
    
    /** Constant variable for the queen symbol */
    //private static final Character QUEEN_SYMBOL = 'X';
    /** Constant variable for the */
    //private static final Character EMPTY_SYMBOL = '#';
    
    /** Method that clears the chessboard */
    public void clearBoard() {
        board.clear(); 
        
        for (int i = 0; i < SIZE; i++) {
            //New ArrayList representing a row
            ArrayList<SquareState> row = new ArrayList<SquareState>();
            
            for (int j = 0; j < SIZE; j++) {
                //Filling newly created row with empty symbols
                row.add(SquareState.EMPTY);
            }
            
            //Adding the empty row to the main chessboard
            board.add(row);
        }
    }
    
    public Position parse(String pos) {
        String posUC = pos.toUpperCase();
        int c = posUC.charAt(0) - 'A';
        int r = posUC.charAt(1) - '1';
        return new Position(r,c);
    }
    
    /** Method that places a queen */
    public void placeQueen(String pos) {
        Position p = parse(pos);
        
        board.get(p.row()).set(p.col(), SquareState.QUEEN);
    }
    
    public void isValidPlacement(String pos) throws InvalidPositionException {
        //Checks if given position is not null and is of correct length
        if (pos==null || pos.length()!=2) {
            throw new InvalidPositionException("User input is too short or too long.");
        }
        
        Position p = parse(pos);
        
        //Checks if given position is in the correct format: XY where X=[A,H] and Y=[1,8]
        if (p.col()<0 || p.col()>7 || p.row()<0 || p.row()>7) {
            throw new InvalidPositionException("Position out of range.");
        }
        
        //Checks if given position is occupied
        if(board.get(p.row()).get(p.col()) != SquareState.EMPTY) {
            throw new InvalidPositionException("Position occupied by other queen");
        }   
    }
    /** Scans chessboard and returns a position if it's occupied by a queen */
    private ArrayList<Position> getQueenPositions() {
        ArrayList<Position> queens = new ArrayList<Position>();
        
        for (int r=0; r<SIZE; r++) {
            for (int c=0; c<SIZE; c++) {
                if(board.get(r).get(c) == SquareState.QUEEN) {
                    queens.add(new Position(r,c));
                }
            }
        }
        
        return queens;
    }
    
    /** Searches for a queen on the board, if found runs attackAnotherQueen to check if placement is correct for the puzzle */
    public boolean isSolutionValid() {
        ArrayList<Position> foundQueens = getQueenPositions();
        
        for(Position p : foundQueens) {
            if(attacksAnotherQueen(p))
                return false;
        }
        
        return true;
    }
    
    /** Checks whether a queen attacks any other queen. */
    private boolean attacksAnotherQueen(Position p) {
        //int row = p.row();
        //int col = p.col();
        
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
