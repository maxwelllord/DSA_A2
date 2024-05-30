/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.String.format;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author hayae
 */
public class GUI extends JFrame {
    
    public ProductTableModel productTable;
    public ProductPanel productPanel;
    
    String[] columnNames = {"Product", "Quantity", "Price"};
    Object[][] data = {};
    Application app;
    
    public GUI(Application app) {
        this.app = app;
        
        setTitle("InventorySystem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        
        productTable = new ProductTableModel(app.products, this);
        JTable table = new JTable(productTable);        
        productTable.addTableMouseListener(table);
        
        ProductPanel pP = new ProductPanel(this);
        this.productPanel = pP;

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a panel for the table and add the scroll pane to it
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel and add components
        JPanel panel = new JPanel();
        panel.add(tablePanel);
        panel.add(pP);

        // Add the panel to the frame
        add(panel);

        // Set default window size
        setPreferredSize(new Dimension(1000, 1000));

        pack();
        setLocationRelativeTo(null);
    }
    
    public class ProductTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Item", "Quantity", "Price"};
        private List<Product> products;
        private GUI gui;

        public ProductTableModel(List<Product> products, GUI gui) {
            this.products = products;
            this.gui = gui;
        }
        
        public void updateTable(List<Product> products) {
            this.products = products;        
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return products.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Product product = products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return product.getTitle();
                case 1:
                    return product.getQuantity();
                case 2:
                    return product.getPrice();
                default:
                    return null;
            }
        }

        public Product getProductAt(int rowIndex) {
            return products.get(rowIndex);
        }

        public void addTableMouseListener(final JTable table) {
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1) { // Single click, probably not needed
                    
                    } else if (e.getClickCount() == 2) { // Double click
                        int rowIndex = table.rowAtPoint(e.getPoint());
                        if (rowIndex >= 0) {
                            // Handle double click event on the row
                            
                            // Perform desired action with the double-clicked product
                            System.out.println("Attempting to load product Id:" + products.get(rowIndex).getId());
                            gui.productPanel.loadProduct(products.get(rowIndex).getId());
                            
                            
                        }
                    }
                }
            });
        }
    }
    
    public class ProductPanel extends JPanel {
        private JTextField titleField;
        private JTextArea descriptionArea;
        private JTextField categoryComboBox;
        private JFormattedTextField priceField;
        private JSpinner quantitySpinner;
        private JButton createButton;
        
        private GUI gui;        
        private boolean isEditing = false;

        public ProductPanel(GUI gui) {
            this.gui = gui;
            
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
                app.createProduct(createProduct());
                
            });
            constraints.gridx = 1;
            constraints.gridy = 5;
            add(createButton, constraints);
            
            this.priceField = priceField;
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
            
            System.out.println(p.getPrice());
            
            titleField.setText(p.getTitle());
            descriptionArea.setText(p.getDescription());
            categoryComboBox.setText(p.getCategory());
            priceField.setValue(p.getPrice());
            quantitySpinner.setValue(p.getQuantity());
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
            return new BigDecimal(priceField.getText());
        }

        public int getQuantity() {
            return (int) quantitySpinner.getValue();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //new GUI().setVisible(true);
            }
        });
    }
    
}
