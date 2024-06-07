/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Product;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
/**
 *
 * @author hayae
 */

public class OrderProductItem extends JPanel {
    
    public List<Product> products = new ArrayList<>(); 
    
    public OrderEditorPanel orderEditorPanel;
    
    private JLabel titleLabel;
    private JSpinner quantitySpinner;
    private JButton deleteButton;

    public OrderProductItem(OrderEditorPanel orderEditorPanel, Product product) {
        this.orderEditorPanel = orderEditorPanel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create the title label
        titleLabel = new JLabel(product.getTitle());
        add(titleLabel, BorderLayout.WEST);

        // Create the quantity spinner
        SpinnerModel spinnerModel = new SpinnerNumberModel(product.getQuantity(), 0, Integer.MAX_VALUE, 1);
        quantitySpinner = new JSpinner(spinnerModel);
        add(quantitySpinner, BorderLayout.CENTER);

        // Create the delete button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the delete action
                // You can remove this item from the list or perform any other desired action
                //JOptionPane.showMessageDialog(ItemPanel.this, "Item deleted: " + getTitle());
            }
        });
        add(deleteButton, BorderLayout.EAST);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public int getQuantity() {
        return (int) quantitySpinner.getValue();
    }

    public void setQuantity(int quantity) {
        quantitySpinner.setValue(quantity);
    }
}
