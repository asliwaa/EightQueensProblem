package controller;

import model.ChessboardModel;
import view.ChessboardBoardView;
import view.ChessboardEntryView;

import javax.swing.SwingUtilities;

/**
 * Main application class responsible for launching the 8 Queens Puzzle program.
 */
public class Projekt {
    
    /**
     * The main entry point of the application.
     * It initializes the Model-View-Controller (MVC) components within the Swing Event Dispatch Thread.
     * * @param args Command line arguments (not used in this version).
     */
    public static void main(String[] args) {
        // Swing applications must start in the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {
            
            // 1. Initialize the Model
            ChessboardModel model = new ChessboardModel();
            
            // 2. Initialize both Views (initially invisible)
            ChessboardEntryView entryView = new ChessboardEntryView();
            ChessboardBoardView boardView = new ChessboardBoardView();
            
            // 3. Initialize the Controller which manages the application flow
            // (The Controller's constructor will set entryView.setVisible(true))
            new ChessboardController(model, entryView, boardView);
        });
    }
}