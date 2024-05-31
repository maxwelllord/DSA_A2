/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Application;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author hayae
 */
public class MainWindow extends JFrame {
    
    //main views of the GUI
    public ProductTableModel productTable;
    public JPanel tableContainer;
    public JPanel tablePanel;
    public ProductEditorPanel productEditorPanel;
    ProductDisplayPanel productDisplayPanel;
    
    String[] columnNames = {"Product", "Quantity", "Price"};
    Object[][] data = {};
    Application app;
    
    public MainWindow(Application app) {
        this.app = app;
        
        setTitle("InventorySystem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        
        this.productTable = new ProductTableModel(app.products, this);
        this.tablePanel = productTable.getTablePanel();
        
        this.productDisplayPanel = new ProductDisplayPanel(this);
        this.productDisplayPanel.setVisible(false);
        
        ProductEditorPanel pP = new ProductEditorPanel(this);
        this.productEditorPanel = pP;
        this.productEditorPanel.setVisible(false);

        // Create a panel and add components
        JPanel panel = new JPanel();
        panel.add(tablePanel);
        panel.add(productEditorPanel);
        panel.add(productDisplayPanel);

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
        this.productEditorPanel.setVisible(false);
        this.productDisplayPanel.setVisible(false);
    }
    
}
