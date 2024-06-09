/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Product;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author hayae
 */

public class OrderProductItem extends JPanel {
    
    public OrderEditorPanel orderEditorPanel;
    
    private JLabel titleLabel;
    private JSpinner quantitySpinner;
    private JButton deleteButton;
    
    private Product product;

    public OrderProductItem(OrderEditorPanel orderEditorPanel, Product product) {
        this.product = product;
        this.orderEditorPanel = orderEditorPanel;
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create the title label
        titleLabel = new JLabel(product.getTitle());
        
        JLabel priceLabel = new JLabel("$ " + product.getPrice().toString());

        // Create the quantity spinner
        SpinnerModel spinnerModel = new SpinnerNumberModel(product.getQuantity(), 0, Integer.MAX_VALUE, 1);
        quantitySpinner = new JSpinner(spinnerModel);
        quantitySpinner.setPreferredSize(new Dimension(60, quantitySpinner.getPreferredSize().height));
        addQuantitySpinnerListener(quantitySpinner);

        // Create the delete button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderEditorPanel.removeProduct(product.getId());
            }
        });
        
        JPanel row = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        row.add(titleLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        row.add(deleteButton, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 0);

        row.add(quantitySpinner, gbc);
        gbc.gridx = 3;        
        row.add(priceLabel, gbc);

        add(row, BorderLayout.CENTER);

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
    
    private void addQuantitySpinnerListener(JSpinner spinner) {
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Code to be executed when the spinner value changes
                int quantity = (int) quantitySpinner.getValue();
                product.setQuantity(quantity);
                orderEditorPanel.orderProducts.put(product.getId(),product);
            }
        });
    }
}
