/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.ChessboardModel;
import model.InvalidPositionException;
import view.ChessboardView;

import java.util.Scanner;

/**
 *
 * @author Adam
 */
public class ChessboardController {
    
    private final ChessboardModel model;
    private final ChessboardView view;
    
    /** Constructor initializing the controller */
    public ChessboardController(ChessboardModel model, ChessboardView view) {
        this.model = model;
        this.view = view;
    }
    
    /** Method that places queens based on positions provided by command line arguments */
    public void commandLineInput(String[] a) {
        for (int i=0; i<8; i++) {
            model.placeQueen(a[i]);
        }
        
        view.displayChessboard(model.getBoard());
    }
    
    /** Method that places queens based on positions provided by the user */
    public void userInput() {
        
        /** Variable used to display the number of the queen which position being entered by user. */
        /*
        int n=1;

        for (int i=0; i<8; i++) {
            System.out.print("Enter position of the " + n + " Queen: ");
            String pos = scanner.next();
            
            while(!model.isValidPlacement(pos)) {
                System.out.println("Wrong position format or position occupied.");
                System.out.print("Enter position of the " + n + " Queen: ");
                pos = scanner.next();
            }

            model.placeQueen(pos);
            n++;
        }

        System.out.println();
        System.out.println();
        view.displayChessboard(model.getBoard());*/
        
        int n=1;
        
        try (Scanner scanner = new Scanner(System.in)) {
            while(n<=8) {
                boolean success = false;
                
                while(!success) {
                    System.out.print("Enter position of the " + n + " Queen: ");
                    String pos = scanner.next();
                    
                    try {
                        model.isValidPlacement(pos);
                        
                        success = true;
                        model.placeQueen(pos);
                    } catch(InvalidPositionException e) {
                        System.out.println("ERROR: " + e.getMessage());
                        System.out.println("Try again.");
                    }
                }
                n++;
            }
        }
        
        System.out.println();
        System.out.println();
        view.displayChessboard(model.getBoard());
    }

}
