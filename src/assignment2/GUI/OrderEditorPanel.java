/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Application;
import assignment2.Order;
import assignment2.Product;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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

    JTextField productSearchField;    
    private List<Product> searchedProducts = new ArrayList<>();
    
    
    JPanel itemListPanel;    
    private List<Product> orderProducts = new ArrayList<>();
    
    private JTextField firstName;    
    private JTextField lastName;
    
    private ProductSearchTableModel searchTableModel;
    
    private Application app;
    private OrderTab orderTab;

    public OrderEditorPanel(Application app, OrderTab orderTab) {
        this.app = app;
        this.orderTab = orderTab;
        initComponents();
    }

    private void initComponents() {
        int yPos = 0;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Order Status Label
        JLabel firstnameLabel = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(firstnameLabel, gbc);

        // Order Shipping Address Text Field
        firstName = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(firstName, gbc);
        
        yPos++;

        // Order Status Label
        JLabel lastNameLabel = new JLabel("Last Name:");
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(lastNameLabel, gbc);

        // Order Shipping Address Text Field
        lastName = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lastName, gbc);
        
        yPos++;

        // Order Status Label
        JLabel orderStatusLabel = new JLabel("Order Status:");
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(orderStatusLabel, gbc);

        // Order Status Combo Box
        String[] statusOptions = {"Pending", "Processing", "Shipped", "Delivered"};
        orderStatus = new JComboBox<>(statusOptions);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(orderStatus, gbc);
        
        yPos++;

        // SHIPPING ADDDRESS LABEL
        JLabel orderShippingAddressLabel = new JLabel("Shipping Address:");
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;
        add(orderShippingAddressLabel, gbc);

        // SHIPPING ADDRESS FIELD
        orderShippingAddress = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(orderShippingAddress, gbc);
        
        yPos++;
            
        // SEARCH PRODUCTS LABEL
        JLabel searchLabel = new JLabel("Search Products:");
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;        
        add(searchLabel, gbc);

        // SEARCH PRODUCT FIELD
        productSearchField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        productSearchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = productSearchField.getText();
                // Perform actions with the search text
                System.out.println("Search text: " + searchText);
                
                if (searchText.equals("")) {
                    // Blank the table if there is no search query                    
                    searchTableModel.setProducts(new ArrayList<>());
                    return;
                }
                
                searchTableModel.setProducts(app.getProductsByTitle(searchText));
            }
        });
        add(productSearchField, gbc);
        
        yPos++;
        
        // PRODUCT SEARCH TABLE
        
        searchTableModel = new ProductSearchTableModel(this,searchedProducts);
        JTable table = new JTable(searchTableModel);
        
        searchTableModel.addTableMouseListener(table);
        table.setPreferredScrollableViewportSize(new Dimension(table.getPreferredSize().width, table.getRowHeight() * 5));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        JScrollPane searchScrollPane = new JScrollPane(table);
        
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(searchScrollPane, gbc);
        
        yPos++;
        
        //
        // PRODUCTS IN THE ORDER
        //
        
        itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        
        JLabel noProductsLabel = new JLabel("No products");
        itemListPanel.add(noProductsLabel);
        
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(itemListPanel,gbc); 
        yPos++;
            
        // ORDER TOTAL PRICE
        JLabel totalPriceLabel = new JLabel("TOTAL PRICE:");
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;        
        add(totalPriceLabel, gbc);
        
        yPos++;

        //Create button
        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            orderTab.gui.app.createOrder(createOrder());                
        });
        
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.RELATIVE;
        add(createButton, gbc);

        //Create button
        JButton discardButton = new JButton("Discard");
        discardButton.addActionListener(e -> {
            resetFields();   
            //orderTab.hideAllPanels();
            //productTab.tablePanel.setVisible(true);
        });
        
        gbc.gridx = 1;
        gbc.gridy = yPos;
        add(discardButton, gbc);
        
        yPos++;
    }
    
    private Order createOrder() {
        return new Order("", "", null, "", "");
        //orderProducts.toArray()
    }
    
    private void resetFields() {
        
    }
    
    public void addProduct(Product newProduct) {
        orderProducts.add(newProduct);
        updateOrderProducts();
    }
    
    public void updateOrderProducts() {
        itemListPanel.removeAll();
        itemListPanel.revalidate();
        
        if (this.orderProducts.size() == 0) {
            JLabel noProductsLabel = new JLabel("No products");
            itemListPanel.add(noProductsLabel);
        } else {           
        
            for (Product p : this.orderProducts) {
                OrderProductItem item = new OrderProductItem(this, p);
                itemListPanel.add(item);
            }
        }
        
        System.out.println(orderProducts);
        
        itemListPanel.repaint();
    }
}
