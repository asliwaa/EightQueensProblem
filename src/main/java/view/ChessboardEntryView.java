/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;


/**
 *
 * @author Adam
 */
public class ChessboardEntryView extends JFrame {
    
    /** Label for the window title. */
    private final JLabel titleLabel = new JLabel("Enter queen's positions:", SwingConstants.CENTER);
    /** Label showing the number of the queen currently being placed*/
    private final JLabel nrLabel = new JLabel("1:"); 
    /** Text field for user input of the position*/
    private final JTextField positionField = new JTextField(4);
    /** Button to confirm the entered position. */
    private final JButton confirmButton = new JButton("Confirm");
    /** Data model for the JTable. */
    private DefaultTableModel tableModel;
    /** Table displaying the accepted queen positions. */
    private JTable acceptedTable;
    /** Title label for the accepted positions table. */
    private final JLabel acceptedTitle = new JLabel("Accepted queens positions", SwingConstants.CENTER);
    
    
    /** Constructs the ChessboardEntryView and initializes the GUI components. */
    public ChessboardEntryView() {
        super("Eight Queens - Entry");  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        initComponents();              
        pack();                        
        setLocationRelativeTo(null);   
    }
    
    private void initComponents() {
        JPanel cp = new JPanel(new BorderLayout());
        cp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setContentPane(cp);
        
        /** App title */
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        cp.add(titleLabel, BorderLayout.NORTH);
        
        
        /** ==== Main part of the entry window ==== */
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        
        /** Label "Nr." */
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        center.add(new JLabel("Nr."), gbc);
        /** Actual queen number */
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        center.add(nrLabel, gbc);
        
        /** Position text field */
        gbc.gridx = 2;
        center.add(positionField, gbc);
        positionField.setToolTipText("Enter position like A1 (column A-H, row 1-8)");
        positionField.setFocusAccelerator('1'); // Alt + 1
        
        /** Confirm button */
        gbc.gridx=3;
        confirmButton.setToolTipText("Confirm position (Alt+C)");
        confirmButton.setMnemonic('C'); //Alt + C
        center.add(confirmButton, gbc);
        
        cp.add(center, BorderLayout.PAGE_START);
        
        
        /** ==== Table part of the entry window ==== */
        JPanel tablePanel = new JPanel(new BorderLayout(5,5));
        acceptedTitle.setBorder (BorderFactory.createEmptyBorder(6,6,0,6));
        tablePanel.add(acceptedTitle, BorderLayout.NORTH);
        
        /** Column names */
        String[] columns = {"Queen", "Position"};
        tableModel = new DefaultTableModel(columns, 0) {
            /** Cells non editable */
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
                
        acceptedTable = new JTable(tableModel);       
        acceptedTable.setFillsViewportHeight(true);  
        
        JScrollPane sp = new JScrollPane(acceptedTable); 
        sp.setPreferredSize(new Dimension(350, 160));    
        tablePanel.add(sp, BorderLayout.CENTER);       

        cp.add(tablePanel, BorderLayout.CENTER); 
        
        
        // --- Tab Order ---
        positionField.setNextFocusableComponent(confirmButton);
        confirmButton.setNextFocusableComponent(positionField); // Loop back

        // --- General padding ---
        cp.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
    } 
    
    /** Return position entered by user */
    public String getTypedPosition() {
        return positionField.getText().trim().toUpperCase();
    }
    
    /** Sets the number of the queen that position is being currently entered */
    public void setNr(int nr) {
        nrLabel.setText(String.valueOf(nr)+":");
    }
    /** Clears position text field and sets focus on it */
    public void clearInput() {
        positionField.setText("");
        positionField.requestFocusInWindow();
    }
    
    /** Adds accepted position to the table */
    public void addAcceptedPosition(int queenNumber, String position) {
        tableModel.addRow(new Object[] {"Queen " + queenNumber, position.toUpperCase()});
    }
    
    public void addConfirmListener(ActionListener l) {
        confirmButton.addActionListener(l);
    }
    
    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /** Resets the view to initial state (clears table and text field) */
    public void reset() {
        tableModel.setRowCount(0);
        clearInput();
        setNr(1);
    }
}
