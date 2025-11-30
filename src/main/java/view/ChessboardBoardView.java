/**
 * Package containing the view components for the Eight Queens Puzzle application (following the MVC pattern).
 */
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * 
 * @author Adam
 */
public class ChessboardBoardView extends JFrame {
    
    /** 8x8 array of JButtons representing the chessboard cells. */
    //private final JButton[][] cells = new JButton[8][8];
    private final ArrayList<ArrayList<JButton>> cells = new ArrayList<ArrayList<JButton>>(8);
    /** Label at the bottom displaying the placement status. */
    private final JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
    /** Button to start a new game/reset the board. */
    private final JButton resetButton = new JButton("Start New / Reset");

    /**
     * Constructs the ChessboardBoardView and initializes the GUI components.
     */
    public ChessboardBoardView() {
        super("Eight Queens - Board");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initializes and arranges the graphical interface components.
     */
    private void initComponents() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(6,6));
        
        JPanel mainPanel = new JPanel(new BorderLayout(6, 6));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        
        // Top: headers (A..H)
        JPanel north = new JPanel(new GridLayout(1, 9)); // POPRAWKA: Usunięto 'new'
        north.add(new JLabel("")); // corner
        for (char c = 'A'; c <= 'H'; c++) {
            JLabel l = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            north.add(l);
        }
        mainPanel.add(north, BorderLayout.NORTH);

        // Center: board with row labels
        JPanel center = new JPanel(new GridLayout(8, 9));
        
        for (int r = 0; r < 8; r++) {
            // A. Tworzymy listę dla wiersza przycisków
            ArrayList<JButton> rowList = new ArrayList<>();
            
            // row number label
            center.add(new JLabel(String.valueOf(r+1), SwingConstants.CENTER));
            
            for (int c = 0; c < 8; c++) {
                JButton cell = new JButton("#");
                cell.setEnabled(false);
                cell.setFocusable(false);
                
                if ((r + c) % 2 == 0) cell.setBackground(Color.WHITE);
                else cell.setBackground(Color.LIGHT_GRAY);
                
                // B. Dodajemy przycisk do listy wiersza
                rowList.add(cell);
                
                // Dodajemy przycisk do panelu (wizualnie)
                center.add(cell);
            }
            
            // C. Dodajemy wiersz do głównej listy cells
            cells.add(rowList);
        }
        
        mainPanel.add(center, BorderLayout.CENTER);
        cp.add(mainPanel, BorderLayout.CENTER);

        // bottom: status and reset button
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

    /** Update board cells from an 8x8 char[][] (expect '#' or 'X') */
    public void updateBoard(ArrayList<ArrayList<Character>> board) {
        if (board == null || board.size() != 8) return;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Character symbol = board.get(r).get(c);
                JButton btn = cells.get(r).get(c);
                
                if (symbol == 'X') {
                btn.setText("X"); // Ładny symbol hetmana
                //btn.setForeground(Color.BLACK);
            } else {
                btn.setText("#"); // Puste pole (lub "#" jeśli wolisz)
            }
            }
        }
    }

    /** Sets the status text displayed at the bottom of the window */
    public void setStatus(String text) {
        statusLabel.setText(text);
    }
    
    /** Adds an ActionListener to the Reset/Start New button */
    public void addResetListener(ActionListener l) {
        resetButton.addActionListener(l);
    }
}