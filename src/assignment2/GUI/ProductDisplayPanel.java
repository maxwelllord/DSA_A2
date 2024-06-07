/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

/**
 *
 * @author hayae
 */
import assignment2.Product;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProductDisplayPanel extends JPanel  {
    
    private final MainWindow gui;
    private ProductTab productTab;
    
    //display fields
    private JLabel idLabel;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JLabel categoryLabel;
    private JLabel priceLabel;
    private JLabel quantityLabel;
    private JLabel createdAtLabel;
    private JLabel updatedAtLabel;
    
    private JButton backButton;

    public ProductDisplayPanel(MainWindow gui, ProductTab productTab) {
        this.gui = gui;
        this.productTab = productTab;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        idLabel = new JLabel();
        add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        titleLabel = new JLabel();
        add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        descriptionLabel = new JLabel();
        add(descriptionLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        categoryLabel = new JLabel();
        add(categoryLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        priceLabel = new JLabel();
        add(priceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        quantityLabel = new JLabel();
        add(quantityLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Created At:"), gbc);

        gbc.gridx = 1;
        createdAtLabel = new JLabel();
        add(createdAtLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Updated At:"), gbc);

        gbc.gridx = 1;
        updatedAtLabel = new JLabel();
        add(updatedAtLabel, gbc);
        
        this.backButton = new JButton("Go back");
        backButton.addActionListener(e -> {
            this.productTab.hideAllPanels();
            this.productTab.tablePanel.setVisible(true);
        });

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(backButton);
    }

    public void setProductInfo(Product product) {
        idLabel.setText(String.valueOf(product.getId()));
        titleLabel.setText(product.getTitle());
        descriptionLabel.setText(product.getDescription());
        categoryLabel.setText(product.getCategory());
        priceLabel.setText(String.valueOf(product.getPrice()));
        quantityLabel.setText(String.valueOf(product.getQuantity()));
        createdAtLabel.setText(product.getCreatedAt().toString());
        updatedAtLabel.setText(product.getUpdatedAt().toString());
     }
}
