/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Application;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author hayae
 */
public class MainWindow extends JFrame {
    
    //main views of the GUI
    public ProductTableModel productTable;
    public JPanel tablePanel;
    public ProductPanel productPanel;
    
    String[] columnNames = {"Product", "Quantity", "Price"};
    Object[][] data = {};
    Application app;
    
    public MainWindow(Application app) {
        this.app = app;
        
        setTitle("InventorySystem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        
        productTable = new ProductTableModel(app.products, this);
        JTable table = new JTable(productTable);        
        productTable.addTableMouseListener(table);
        
        ProductPanel pP = new ProductPanel(this);
        this.productPanel = pP;
        this.productPanel.setVisible(false);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a panel for the table and add the scroll pane to it
        this.tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel and add components
        JPanel panel = new JPanel();
        panel.add(tablePanel);
        panel.add(productPanel);

        // Add the panel to the frame
        add(panel);

        // Set default window size
        setPreferredSize(new Dimension(1000, 1000));

        pack();
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //new GUI().setVisible(true);
            }
        });
    }
    
    public void hideAllPanels() {
        System.out.println("hiding all panels");
        this.tablePanel.setVisible(false);
        this.productPanel.setVisible(false);
    }
    
}
