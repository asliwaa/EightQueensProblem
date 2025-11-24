/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package controller;

import model.ChessboardModel;
import view.ChessboardView;

/**
 *
 * @author Adam
 */
public class Projekt {
    
    /**
     * Depending on the content of command line arguments launches the app.
     * - if they meet the requirements, launches commandLineInput method from the controller
     * - if they don't, launches userInput method from the controller
     * 
     * @param args Should contain 8 positions in this format: XY, where X = [A,H] and Y = [1,8]
     */
    public static void main(String[] args) {
        ChessboardModel model = new ChessboardModel();
        ChessboardView view = new ChessboardView();
        
        ChessboardController controller = new ChessboardController(model, view);
        
        int numOfParams = args.length;
        
        if(numOfParams == 8) {
            int a=1;
            int b=2;
            char c=(char)a;

            System.out.println(c);
            controller.commandLineInput(args);
        } else {
            controller.userInput();
        }
    }
}
