package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.SquareState;

/**
 * The secondary GUI window that visualizes the chessboard.
 * It displays the 8x8 grid with placed queens and shows the validation status.
 *
 * @author Adam
 * @version 1.0
 */
public class ChessboardBoardView extends JFrame {
    
    /**
     * A grid of JButtons representing the chessboard cells.
     * Stored as a list of rows to allow easy indexing.
     */
    private final ArrayList<ArrayList<JButton>> cells = new ArrayList<ArrayList<JButton>>(8);
    
    /** Label at the bottom displaying the placement status (Success/Failure). */
    private final JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
    
    /** Button to reset the board and start a new game. */
    private final JButton resetButton = new JButton("Start New / Reset");

    /**
     * Constructs the ChessboardBoardView and initializes the GUI components.
     * Sets up the window properties such as title, close operation, and location.
     */
    public ChessboardBoardView() {
        super("Eight Queens - Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initializes and arranges the graphical interface components.
     * Creates the chessboard grid, adds row/column labels, and sets up the status panel.
     */
    private void initComponents() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(6,6));
        
        JPanel mainPanel = new JPanel(new BorderLayout(6, 6));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        
        // Top: headers (A..H)
        JPanel north = new JPanel(new GridLayout(1, 9));
        north.add(new JLabel("")); // corner
        for (char c = 'A'; c <= 'H'; c++) {
            JLabel l = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            north.add(l);
        }
        mainPanel.add(north, BorderLayout.NORTH);

        // Center: board with row labels
        JPanel center = new JPanel(new GridLayout(8, 9));
        
        for (int r = 0; r < 8; r++) {
            // A. Create a list for the row of buttons
            ArrayList<JButton> rowList = new ArrayList<>();
            
            // row number label
            center.add(new JLabel(String.valueOf(r+1), SwingConstants.CENTER));
            
            for (int c = 0; c < 8; c++) {
                JButton cell = new JButton("#");
                cell.setEnabled(false);
                cell.setFocusable(false);
                
                if ((r + c) % 2 == 0) cell.setBackground(Color.WHITE);
                else cell.setBackground(Color.LIGHT_GRAY);
                
                // B. Add button to row list
                rowList.add(cell);
                
                // Add button to panel (visually)
                center.add(cell);
            }
            
            // C. Add row to main cells list
            cells.add(rowList);
        }
        
        mainPanel.add(center, BorderLayout.CENTER);
        cp.add(mainPanel, BorderLayout.CENTER);

        // Bottom: status and reset button
        JPanel southPanel = new JPanel(new BorderLayout());
        statusLabel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
        southPanel.add(statusLabel, BorderLayout.NORTH);
        
        // Reset Button added
        resetButton.setMnemonic('S'); // Alt+S shortcut
        resetButton.setToolTipText("Click to clear the board and start a new game (Alt+S)");
        resetButton.getAccessibleContext().setAccessibleDescription("Button to clear the current placement and return to the entry screen to start a new game.");
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.add(resetButton);
        southPanel.add(buttonWrapper, BorderLayout.SOUTH);
        
        cp.add(southPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(450, 480));
    }

    /**
     * Updates the visual representation of the board based on the model state.
     * Iterates through the provided board state and updates the text of corresponding JButtons.
     *
     * @param board The current state of the chessboard, represented as a list of lists of {@link SquareState}.
     */
    public void updateBoard(ArrayList<ArrayList<SquareState>> board) {
        if (board == null || board.size() != 8) return;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                SquareState state = board.get(r).get(c);
                JButton btn = cells.get(r).get(c);
                
                if (state == SquareState.QUEEN) {
                    btn.setText("X");
                } else {
                    btn.setText("#");
                }
            }
        }
    }

    /**
     * Sets the status text displayed at the bottom of the window.
     *
     * @param text The status message to display (e.g., "SUCCESS" or "FAILURE").
     */
    public void setStatus(String text) {
        statusLabel.setText(text);
    }
    
    /**
     * Registers an ActionListener for the "Reset/Start New" button.
     *
     * @param l The ActionListener to handle the button click event.
     */
    public void addResetListener(ActionListener l) {
        resetButton.addActionListener(l);
    }
}