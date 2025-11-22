/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.ChessboardModel;
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
        
        /** Scanner initializer, placed before the 'for' loop so it doesn't block the input stream*/
        Scanner scanner = new Scanner(System.in);
        /** Variable used to display the number of the queen which position being entered by user. */
        int n=1;

        for (int i=0; i<8; i++) {
                

                System.out.print("Enter position of the " + n + " Queen: ");
                String pos = scanner.next();

                n++;

                model.placeQueen(pos);
            }

            System.out.println();
            System.out.println();
            view.displayChessboard(model.getBoard());
    }

}
