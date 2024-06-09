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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author hayae
 */
public class OrderEditorPanel extends JPanel  {
    //Fields that need to be read to create an order
    
    private JLabel editorHeader = new JLabel("Creating a new order");
    private JButton createButton;
    
    private JTextField firstName;    
    private JTextField lastName;
    private JTextField orderShippingAddress;
    private JComboBox orderStatus;
    
    private JLabel totalPriceValue;
    
    private JTextField productSearchField;       
    private JPanel itemListPanel; 
    
    private List<Product> searchedProducts = new ArrayList<>(); //products display to the user to be picked for the order
    public HashMap<Integer, Product> orderProducts = new HashMap<>(); //products in the order
    
    private ProductSearchTableModel searchTableModel;
    
    private Application app;
    private OrderTab orderTab;
    
    public int indexUpdating = -1;
    private Order order; //the order being updated, if one is present

    public OrderEditorPanel(Application app, OrderTab orderTab) {
        this.app = app;
        this.orderTab = orderTab;
        initComponents();
    }

    private void initComponents() {
        int yPos = 0;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);        
        add(editorHeader);
        
        yPos++;

        // Order Status Label
        JLabel firstnameLabel = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = yPos;
        add(firstnameLabel, gbc);

        // Order Shipping Address Text Field
        firstName = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        add(firstName, gbc);
        
        yPos++;

        // Order Status Label
        JLabel lastNameLabel = new JLabel("Last Name:");
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(lastNameLabel, gbc);

        // Order Shipping Address Text Field
        lastName = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = yPos;
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
        Order.OrderStatus[] orderStatuses = Order.OrderStatus.values();      
        
        orderStatus = new JComboBox<>(orderStatuses);
        gbc.gridx = 1;
        gbc.gridy = yPos;
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
        add(orderShippingAddress, gbc);
        
        yPos++;
        
        // SPLITTER ENDING CUSTOMER DATA
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.CENTER;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        add(separator, gbc);
        
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
        
        // ORDER TOTAL 
        JLabel totalPriceLabel = new JLabel("TOTAL PRICE:");
        gbc.gridx = 0;
        gbc.gridy = yPos;    
        add(totalPriceLabel, gbc);
        
        // TOTAL PRICE VALUE
        
        totalPriceValue = new JLabel("$ 0.00");
        gbc.gridx = 1;
        gbc.gridy = yPos;  
        add(totalPriceValue, gbc);
        
        yPos++;

        //Create button
        this.createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            if (this.indexUpdating > -1) {
                app.updateOrder(createOrder());
            } else {
                app.createOrder(createOrder());                    
            }            
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
    }
    
    private Order createOrder() {
        Product[] prods = orderProducts.values().toArray(new Product[0]);
        
        if (prods.length == 0) {
            System.out.println("Order needs at least one product");
            return null;
        }
        
        BigDecimal totalPrice = new BigDecimal(0.0);
        
        for (Product p: prods) {
            totalPrice = totalPrice.add(p.getPrice());
        }
        
        Order o = new Order(
            this.firstName.getText(),
            this.lastName.getText(),
            prods,
            this.orderShippingAddress.getText(),
            (Order.OrderStatus) this.orderStatus.getSelectedItem(),
            totalPrice
        );
        
        if (this.indexUpdating > -1) {
            o.setId(order.getId());
        }
        
        return o;
    }
    
    public void loadOrder(int rowIndex, Order order) {
        this.indexUpdating = rowIndex;
        this.order = order;
        
        editorHeader.setText("Editing Order No." + order.getId());
        this.createButton.setText("Save Changes");
        
        firstName.setText(order.getCustomerFName());    
        lastName.setText(order.getCustomerLName());    
        orderShippingAddress.setText(order.getShippingAddress());
        
        orderStatus.setSelectedItem(order.getStatus());
        totalPriceValue.setText(order.getTotalPrice().toString());
        
        //Load the products
        orderProducts = app.loadProductsFromOrder(order.getId());
        renderLineItems();
    }
    
    private void resetFields() {
        this.indexUpdating = -1;
        this.order = null;
        
        editorHeader.setText("Creating a new order");
        this.createButton.setText("Create");
        
        firstName.setText("");    
        lastName.setText("");    
        orderShippingAddress.setText("");
        
        orderStatus.setSelectedItem("");
        totalPriceValue.setText("");
        
        //Reset products
        orderProducts = new HashMap<>();
        renderLineItems();
    }
    
    public void removeProduct(int productId) {
        System.out.println("Trying to delete " + productId);
        this.orderProducts.remove(productId);
        renderLineItems();
        
    }
    
    public void addProduct(Product newProduct) {
        orderProducts.put(newProduct.getId(),new Product(newProduct));
        
        renderLineItems();
    }
    
    public void renderLineItems() {
        this.itemListPanel.removeAll();
        
        if (this.orderProducts.isEmpty()) {
            System.out.println("Order products is empty");
            JLabel noProductsLabel = new JLabel("No products");
            this.itemListPanel.add(noProductsLabel);
            return;
        } 
        
        BigDecimal totalPrice = new BigDecimal(0);
        
        for (Product p : this.orderProducts.values()) {
            totalPrice = totalPrice.add(p.getPrice());
            OrderProductItem item = new OrderProductItem(this, p);
            itemListPanel.add(item);
        }
        
        this.totalPriceValue.setText("$ " + totalPrice);
        
        itemListPanel.revalidate();
        itemListPanel.repaint();
        
    }
}
