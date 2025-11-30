package controller;

import model.ChessboardModel;
import model.InvalidPositionException;
import view.ChessboardBoardView;
import view.ChessboardEntryView;

import java.awt.event.ActionEvent;

/**
 * Controller class managing the interaction between the Model and the Views.
 * It handles user inputs, updates the model state, and switches between views.
 * 
 * @author Adam
 */
public class ChessboardController {
    
    /** Reference to the data model */
    private final ChessboardModel model;
    /** Reference to the input view (first window) */
    private final ChessboardEntryView entryView;
    /** Reference to the board visualization view (second window) */
    private final ChessboardBoardView boardView;
    
    /** Counter for the current queen being placed */
    private int currentQueenCounter = 1;
    
    /**
     * Constructor initializing the controller with model and views.
     * It also sets up the event listeners and shows the initial view.
     * @param model The game logic model.
     * @param entryView The view for entering positions.
     * @param boardView The view for displaying the chessboard.
     */
    public ChessboardController(ChessboardModel model, ChessboardEntryView entryView, ChessboardBoardView boardView) {
        this.model = model;
        this.entryView = entryView;
        this.boardView = boardView;
        
        initController();
    }
    
    /**
     * Sets up event listeners for buttons in the views and initializes the first screen.
     */
    private void initController() {
        //Attaching logic to the "Confirm" button in the entry view
        entryView.addConfirmListener((ActionEvent e) -> handleConfirmClick());
        
        //Attaching logic to the "Reset" button in the board view
        boardView.addResetListener((ActionEvent e) -> handleResetClick());
        
        //Displaying the entry window
        entryView.setVisible(true);
    }
    
    /**
     * Handles the action when the "Confirm" button is clicked.
     * Validates input, updates the model, and proceeds to the next queen or finishes input.
     */
    private void handleConfirmClick() {
        String pos = entryView.getTypedPosition();
        
        try {
            model.isValidPlacement(pos);
            model.placeQueen(pos);
            
            //Update the entry view (add to list and clear input)
            entryView.addAcceptedPosition(currentQueenCounter, pos);
            entryView.clearInput();
            
            currentQueenCounter++;
            
            if (currentQueenCounter > 8) {
                //Finishes input phase of the app
                finishInputPhase();
            } else {
                //Update the queen number label for the user
                entryView.setNr(currentQueenCounter);
            }
            
        } catch (InvalidPositionException ex) {
            //Displays the error message
            entryView.showError(ex.getMessage());
            entryView.clearInput();
        }
    }
    
    /**
     * Finalizes the input phase, hides the entry window, and displays the result board.
     * Checks if the placed queens form a valid solution.
     */
    private void finishInputPhase() {
        //Hide the entry window
        entryView.setVisible(false);
        
        //Update the board window with data from the model
        boardView.updateBoard(model.getBoard());
        
        //Check if the solution of the puzzle is correct
        boolean valid = model.isSolutionValid();
        
        if (valid) {
            boardView.setStatus("SUCCESS! No queens are attacking each other.");
        } else {
            boardView.setStatus("FAILURE. Queens are attacking each other.");
        }
        
        //Show the board window
        boardView.setVisible(true);
    }
    
    /**
     * Handles the action when the "Reset" button is clicked.
     * Clears the model and views, and returns to the input phase.
     */
    private void handleResetClick() {
        //Clears model data and counter
        model.clearBoard();
        currentQueenCounter = 1;
        
        //Clears lists and inputs in views)
        entryView.reset(); 
        
        //Goes back to the entry window
        boardView.setVisible(false);
        entryView.setVisible(true);
    }
}