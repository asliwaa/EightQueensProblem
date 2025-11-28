package controller;

import model.ChessboardModel;
import model.InvalidPositionException;
import view.ChessboardBoardView;
import view.ChessboardEntryView;

import java.awt.event.ActionEvent;

/**
 * Controller class managing the interaction between the Model and the Views.
 * It handles user inputs, updates the model state, and switches between views.
 */
public class ChessboardController {
    
    /** Reference to the data model */
    private final ChessboardModel model;
    /** Reference to the input view (first window) */
    private final ChessboardEntryView entryView;
    /** Reference to the board visualization view (second window) */
    private final ChessboardBoardView boardView;
    
    /** Counter for the current queen being placed (1-8) */
    private int currentQueenCounter = 1;
    
    /**
     * Constructor initializing the controller with model and views.
     * It also sets up the event listeners and shows the initial view.
     * * @param model The game logic model.
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
        // 1. Attach logic to the "Confirm" button in the entry view
        entryView.addConfirmListener((ActionEvent e) -> handleConfirmClick());
        
        // 2. Attach logic to the "Reset" button in the board view
        boardView.addResetListener((ActionEvent e) -> handleResetClick());
        
        // 3. Display the first window (Entry View)
        entryView.setVisible(true);
    }
    
    /**
     * Handles the action when the "Confirm" button is clicked.
     * Validates input, updates the model, and proceeds to the next queen or finishes input.
     */
    private void handleConfirmClick() {
        String pos = entryView.getTypedPosition();
        
        try {
            // A. Validate in Model (check format and if the square is empty)
            model.isValidPlacement(pos);
            
            // B. If valid -> Save to model
            model.placeQueen(pos);
            
            // C. Update the entry view (add to list and clear input)
            entryView.addAcceptedPosition(currentQueenCounter, pos);
            entryView.clearInput();
            
            // D. Proceed to the next queen
            currentQueenCounter++;
            
            if (currentQueenCounter > 8) {
                // END OF INPUT -> Switch views
                finishInputPhase();
            } else {
                // Update the queen number label for the user
                entryView.setNr(currentQueenCounter);
            }
            
        } catch (InvalidPositionException ex) {
            // E. Error handling - show popup message
            entryView.showError(ex.getMessage());
            // Optionally clear input to allow retry
            entryView.clearInput();
        }
    }
    
    /**
     * Finalizes the input phase, hides the entry window, and displays the result board.
     * Checks if the placed queens form a valid solution.
     */
    private void finishInputPhase() {
        // Hide the entry window
        entryView.setVisible(false);
        
        // Update the board window with data from the model
        boardView.updateBoard(model.getBoard());
        
        // Check if the solution is correct (no attacks)
        boolean valid = model.isSolutionValid();
        
        if (valid) {
            boardView.setStatus("✅ SUCCESS! No queens are attacking each other.");
        } else {
            boardView.setStatus("❌ FAILURE. Queens are attacking each other.");
        }
        
        // Show the board window
        boardView.setVisible(true);
    }
    
    /**
     * Handles the action when the "Reset" button is clicked.
     * Clears the model and views, and returns to the input phase.
     */
    private void handleResetClick() {
        // 1. Logical reset (clear model data and counter)
        model.clearBoard();
        currentQueenCounter = 1;
        
        // 2. Visual reset (clear lists and inputs in views)
        entryView.reset(); 
        
        // 3. Switch windows (Hide board, Show entry)
        boardView.setVisible(false);
        entryView.setVisible(true);
    }
}