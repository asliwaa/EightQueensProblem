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
    
    public static void main(String[] args) {
        ChessboardModel model = new ChessboardModel();
        ChessboardView view = new ChessboardView();
        
        ChessboardController controller = new ChessboardController(model, view);
        
        int numOfParams = args.length;
        
        if(numOfParams == 8) {
            controller.commandLineInput(args);
        } else {
            controller.userInput();
        }
    }
}
