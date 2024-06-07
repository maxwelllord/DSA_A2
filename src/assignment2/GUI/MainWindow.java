/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Application;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author hayae
 */
public class MainWindow extends JFrame {
    
    public ProductTab productTab;
    public OrderTab orderTab;
    
    String[] columnNames = {"Product", "Quantity", "Price"};
    Object[][] data = {};
    Application app;
    
    public MainWindow(Application app) {
        this.app = app;
        
        setTitle("InventorySystem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        
        productTab = new ProductTab(app, this);
        orderTab = new OrderTab(app);

        // Create a JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.add(orderTab);
        tabbedPane.add(productTab);
        
        tabbedPane.setTitleAt(0,"Orders");        
        tabbedPane.setTitleAt(1,"Products");
        
        add(tabbedPane);
        // Set default window size
        setPreferredSize(new Dimension(1000, 1000));

        pack();
        setLocationRelativeTo(null);
    }   
}
