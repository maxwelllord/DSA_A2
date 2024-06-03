/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Product;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author hayae
 */
public class OrderEditorPanel extends JPanel  {
    private JComboBox orderStatus; 
    private JTextField orderShippingAddress;
    private Product[] orderProducts;
    
    private final MainWindow gui;

    public OrderEditorPanel(MainWindow gui) {
        this.gui = gui;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Order Status Label
        JLabel orderStatusLabel = new JLabel("Order Status:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(orderStatusLabel, gbc);

        // Order Status Combo Box
        String[] statusOptions = {"Pending", "Processing", "Shipped", "Delivered"};
        orderStatus = new JComboBox<>(statusOptions);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(orderStatus, gbc);

        // Order Shipping Address Label
        JLabel orderShippingAddressLabel = new JLabel("Shipping Address:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(orderShippingAddressLabel, gbc);

        // Order Shipping Address Text Field
        orderShippingAddress = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(orderShippingAddress, gbc);

        // Order Products Label
        JLabel orderProductsLabel = new JLabel("Order Products:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(orderProductsLabel, gbc);

        // Order Products Table
        // Replace this junk data later
        String[] columnNames = {"Product", "Quantity", "Price"};
        Object[][] data = {
            {"Product 1", 2, 10.99},
            {"Product 2", 1, 5.99},
            {"Product 3", 3, 8.99}
        };
        JTable orderProductsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(orderProductsTable);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);
    }
}
