/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Application;
import javax.swing.JPanel;

/**
 *
 * @author hayae
 */
public class ProductTab extends JPanel {
    
    //main views of the GUI
    public ProductTableModel productTable;
    public JPanel tableContainer;
    public JPanel tablePanel;
    public ProductEditorPanel productEditorPanel;
    ProductDisplayPanel productDisplayPanel;

    public ProductTab(Application app) {
        
        this.productTable = new ProductTableModel(app.getProducts(), this);
        this.tablePanel = productTable.getTablePanel();
        
        this.productDisplayPanel = new ProductDisplayPanel(this);
        this.productDisplayPanel.setVisible(false);
        
        ProductEditorPanel pP = new ProductEditorPanel(app, this);
        this.productEditorPanel = pP;
        this.productEditorPanel.setVisible(true);
        
        add(tablePanel);
        add(productEditorPanel);
        add(productDisplayPanel);
        
        setVisible(true);
    }
}
