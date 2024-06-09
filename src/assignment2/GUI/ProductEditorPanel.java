/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Product;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.NumberFormat;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author hayae
 */

    
public class ProductEditorPanel extends JPanel {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField categoryComboBox;
    private JFormattedTextField priceField;
    private JSpinner quantitySpinner;
    private JButton createButton;
    private JButton discardButton;
    
    private JLabel panelHeader = new JLabel("Creating a new product");

    private MainWindow gui;
    private ProductTab productTab;
    
    public int indexUpdating = -1;
    private Product product; //the product being edited
    
    public ProductEditorPanel(MainWindow gui, ProductTab productTab) {
        this.gui = gui;
        this.productTab = productTab;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        
        int yPos = 0;

        // Header
        constraints.gridx = 1;
        constraints.gridy = yPos;
        add(panelHeader, constraints);
        
        yPos++;

        // Title
        JLabel titleLabel = new JLabel("Title:");
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(titleLabel, constraints);

        titleField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = yPos;
        add(titleField, constraints);
        
        yPos++;
        
        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(descriptionLabel, constraints);

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        constraints.gridx = 1;
        constraints.gridy = yPos;
        add(scrollPane, constraints);
        
        yPos++;

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(categoryLabel, constraints);

        categoryComboBox = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = yPos;
        add(categoryComboBox, constraints);
        
        yPos++;

        // Price
        JLabel priceLabel = new JLabel("Price:");
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(priceLabel, constraints);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);

        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setValueClass(BigDecimal.class);
        formatter.setAllowsInvalid(false);

        JFormattedTextField priceField = new JFormattedTextField(formatter);
        priceField.setFocusLostBehavior(JFormattedTextField.COMMIT);

        priceField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JFormattedTextField field = (JFormattedTextField) input;
                String text = field.getText();
                System.out.println(text);

                // Strip text of non-numerics
                text = text.replaceAll("[^\\d.]", "");

                if (text.isEmpty()) {
                    field.setValue(BigDecimal.ZERO);
                    return false;
                }

                try {
                    BigDecimal price = new BigDecimal(text);
                    if (price.compareTo(BigDecimal.ZERO) >= 0) {
                        field.setValue(price);
                        return true; // Valid price
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }

                field.setValue(BigDecimal.ZERO);
                return false;
            }
        });

        priceField.setColumns(10);
        constraints.gridx = 1;
        constraints.gridy = yPos;
        add(priceField, constraints);
        
        yPos++;

        // Quantity
        JLabel quantityLabel = new JLabel("Quantity:");
        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(quantityLabel, constraints);

        //spinner
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        constraints.gridx = 1;
        constraints.gridy = yPos;
        add(quantitySpinner, constraints);
        
        
        yPos++;

        //Create button
        createButton = new JButton("Create");
        createButton.addActionListener(e -> {
                Product newOrChangedProduct = createProduct();
            if (this.indexUpdating > -1) {
                gui.app.updateProduct(this.indexUpdating,newOrChangedProduct );                    
            } else {         
                gui.app.createProduct(newOrChangedProduct);      
            }             
        });

        //Create button
        discardButton = new JButton("Discard changes");
        discardButton.addActionListener(e -> {
            resetFields();
            productTab.productEditorPanel.setEditingFalse();
        });


        constraints.gridx = 0;
        constraints.gridy = yPos;
        add(createButton, constraints);


        constraints.gridx = 1;
        constraints.gridy = yPos;
        add(discardButton, constraints);

        this.priceField = priceField;

        //Reset the fields
        resetFields();
    }

    public Product createProduct() {
        String title = getTitle();
        String desc = getDescription();
        String cat = getCategory();
        BigDecimal price = getPrice();
        int quant = getQuantity();
        int id = this.product == null ? -1 : this.product.getId();

        return new Product(id, title, desc, cat, price, quant);
    }

    //load product for editing
    public void loadProduct(int rowIndex, int productId) {
        this.indexUpdating = rowIndex; // Pass the row index of the product being updated, so if it is updated we can just adjust one product rather than querying the DB again
        this.product = gui.app.getProductById(productId);
        
        System.out.println(this.product);

        titleField.setText(this.product.getTitle());
        descriptionArea.setText(this.product.getDescription());
        categoryComboBox.setText(this.product.getCategory());
        priceField.setValue(this.product.getPrice());
        quantitySpinner.setValue(this.product.getQuantity());
        
        this.createButton.setText("Update");
        panelHeader.setText("Editing product \"" + this.product.getTitle() + "\"");
    }

    public void resetFields() {    
        this.indexUpdating = -1;
        this.product = null;
        titleField.setText("");
        descriptionArea.setText("");
        categoryComboBox.setText("");
        priceField.setValue(BigDecimal.ZERO);
        quantitySpinner.setValue(0);
        panelHeader.setText("Creating a new product");
    }

    // Getter methods for retrieving the entered data
    public String getTitle() {
        return titleField.getText();
    }

    public String getDescription() {
        return descriptionArea.getText();
    }

    public String getCategory() {
        return (String) categoryComboBox.getText();
    }

    public BigDecimal getPrice() {
        return new BigDecimal(Double.parseDouble(priceField.getText()));
    }

    public int getQuantity() {
        return (int) quantitySpinner.getValue();
    }
    
    public void setEditingFalse() {
        this.createButton.setText("Create");
    }
}