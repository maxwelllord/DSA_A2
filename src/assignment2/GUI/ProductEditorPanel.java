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

    private MainWindow gui;
    private ProductTab productTab;
    
    public boolean isEditing = false;
    

    public ProductEditorPanel(MainWindow gui, ProductTab productTab) {
        this.gui = gui;
        this.productTab = productTab;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel titleLabel = new JLabel("Title:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(titleLabel, constraints);

        titleField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(titleField, constraints);

        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(descriptionLabel, constraints);

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(scrollPane, constraints);

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(categoryLabel, constraints);

        categoryComboBox = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(categoryComboBox, constraints);

        // Price
        JLabel priceLabel = new JLabel("Price:");
        constraints.gridx = 0;
        constraints.gridy = 3;
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
        constraints.gridy = 3;
        add(priceField, constraints);

        // Quantity
        JLabel quantityLabel = new JLabel("Quantity:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(quantityLabel, constraints);

        //spinner
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        constraints.gridx = 1;
        constraints.gridy = 4;
        add(quantitySpinner, constraints);

        //Create button
        createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            gui.app.createProduct(createProduct());                
        });

        //Create button
        discardButton = new JButton("Discard changes");
        discardButton.addActionListener(e -> {
            resetFields();
            productTab.productEditorPanel.setEditingFalse();
        });


        constraints.gridx = 0;
        constraints.gridy = 5;
        add(createButton, constraints);


        constraints.gridx = 1;
        constraints.gridy = 5;
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

        return new Product(-1, title, desc, cat, price, quant);
    }

    //load product for editing
    public void loadProduct(int productId) {
        Product p = gui.app.getProductById(productId);

        titleField.setText(p.getTitle());
        descriptionArea.setText(p.getDescription());
        categoryComboBox.setText(p.getCategory());
        priceField.setValue(p.getPrice());
        quantitySpinner.setValue(p.getQuantity());
    }

    public void resetFields() {            
        titleField.setText("");
        descriptionArea.setText("");
        categoryComboBox.setText("");
        priceField.setValue(BigDecimal.ZERO);
        quantitySpinner.setValue(0);
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
        System.out.println(priceField);
        return new BigDecimal(Double.parseDouble(priceField.getText()));
    }

    public int getQuantity() {
        return (int) quantitySpinner.getValue();
    }
    
    public void setEditingTrue() {
        this.isEditing = true;
        this.createButton.setText("Update");
    }
    
    public void setEditingFalse() {
        this.isEditing = false;
        this.createButton.setText("Create");
    }
}