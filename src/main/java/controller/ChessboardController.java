package controller;

import model.ChessboardModel;
import model.InvalidPositionException;
import view.ChessboardBoardView;
import view.ChessboardEntryView;

import java.awt.event.ActionEvent;

/**
 * Controller class managing the interaction between the Model and the Views.
 * It handles user inputs (GUI and CLI), updates the model state, and switches between views.
 * * @author Adam
 * * @version 4.0
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
    
    /** Command line arguments passed from main */
    private final String[] args;
    
    /**
     * Constructor initializing the controller with model, views, and arguments.
     * It also sets up the event listeners and decides which view to show first.
     * @param model The game logic model.
     * @param entryView The view for entering positions.
     * @param boardView The view for displaying the chessboard.
     * @param args Command line arguments.
     */
    public ChessboardController(ChessboardModel model, ChessboardEntryView entryView, ChessboardBoardView boardView, String[] args) {
        this.model = model;
        this.entryView = entryView;
        this.boardView = boardView;
        this.args = args;
        
        initController();
    }
    
    /**
     * Sets up event listeners and initializes the application state.
     * Checks if valid command line arguments are provided to skip the entry phase.
     */
    private void initController() {
        // Attaching logic to the "Confirm" button in the entry view
        entryView.addConfirmListener((ActionEvent e) -> handleConfirmClick());
        
        // Attaching logic to the "Reset" button in the board view
        boardView.addResetListener((ActionEvent e) -> handleResetClick());
        
        // Check if command line arguments are valid (must be exactly 8 positions)
        if (args != null && args.length == 8) {
            boolean success = processCommandLineArguments();
            
            if (success) {
                // If arguments were valid, skip entry view and show the board
                finishInputPhase();
                return; 
            } else {
                // If CLI processing failed, clean up and fall back to GUI entry
                System.out.println("Switching to manual input mode due to errors.");
                handleResetClick(); 
            }
        }
        
        // Default behavior: Display the entry window
        entryView.setVisible(true);
    }
    
    /**
     * Attempts to process the command line arguments.
     * @return true if all 8 positions are valid and placed successfully, false otherwise.
     */
    private boolean processCommandLineArguments() {
        System.out.println("Processing command line arguments...");
        try {
            for (String pos : args) {
                // Validate format and rules (throws InvalidPositionException if wrong)
                model.isValidPlacement(pos);
                // Place the queen
                model.placeQueen(pos);
            }
            return true; // All 8 queens placed successfully
            
        } catch (InvalidPositionException ex) {
            System.err.println("CLI Error: Invalid position found in arguments - " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.err.println("CLI Error: Unexpected error - " + ex.getMessage());
            return false;
        }
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
            
            // Update the entry view (add to list and clear input)
            entryView.addAcceptedPosition(currentQueenCounter, pos);
            entryView.clearInput();
            
            currentQueenCounter++;
            
            if (currentQueenCounter > 8) {
                // Finishes input phase of the app
                finishInputPhase();
            } else {
                // Update the queen number label for the user
                entryView.setNr(currentQueenCounter);
            }
            
        } catch (InvalidPositionException ex) {
            // Displays the error message
            entryView.showError(ex.getMessage());
            entryView.clearInput();
        }
    }
    
    /**
     * Finalizes the input phase, hides the entry window, and displays the result board.
     * Checks if the placed queens form a valid solution.
     */
    private void finishInputPhase() {
        // Hide the entry window (might be already hidden if in CLI mode, but that's safe)
        entryView.setVisible(false);
        
        // Update the board window with data from the model
        boardView.updateBoard(model.getBoard());
        
        // Check if the solution of the puzzle is correct
        boolean valid = model.isSolutionValid();
        
        if (valid) {
            boardView.setStatus("SUCCESS! No queens are attacking each other.");
        } else {
            boardView.setStatus("FAILURE. Queens are attacking each other.");
        }
        
        // Show the board window
        boardView.setVisible(true);
    }
    
    /**
     * Handles the action when the "Reset" button is clicked.
     * Clears the model and views, and returns to the input phase.
     */
    private void handleResetClick() {
        // Clears model data and counter
        model.clearBoard();
        currentQueenCounter = 1;
        
        // Clears lists and inputs in views
        entryView.reset(); 
        
        // Goes back to the entry window
        boardView.setVisible(false);
        entryView.setVisible(true);
    }
}