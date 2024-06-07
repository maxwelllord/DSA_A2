/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Application;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author hayae
 */
public class OrderTab extends JPanel {
    
    private OrderEditorPanel orderEditorPanel;
    public MainWindow gui;
    
    public OrderTab(Application app) {
        
        this.orderEditorPanel = new OrderEditorPanel(app,this);
        orderEditorPanel.setVisible(true);
        
        add(orderEditorPanel);
        
        setVisible(true);
    }
    
    
    
}
