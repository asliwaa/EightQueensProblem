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
        for (int i=0; i<a.length; i++) {
            String pos = a[i];
            
            try {
                model.isValidPlacement(pos);

                model.placeQueen(pos);
                
                if(i == 7) {
                    view.displayChessboard(model.getBoard());
                }
                
            } catch (InvalidPositionException e) {
                System.out.println("ERROR: Incorrect command line argument has been found - " + e.getMessage());
                System.out.println("Switching to user input.");
                
                userInput();
            }
        }
    }
    
    /** Method that places queens based on positions provided by the user */
    public void userInput() {
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
        view.displayValidationResult(model.isSolutionValid());
    }
    
}

