/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hayae
 */
public class GUI extends JFrame {
    
    public ProductTableModel productTable;
    
    String[] columnNames = {"Product", "Quantity", "Price"};
    Object[][] data = {};
    
    public GUI(Application app) {
        setTitle("InventorySystem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        // Create components
        JLabel label = new JLabel("I love inventories!");
        JButton button = new JButton("Click Me");
        JTextField textField = new JTextField(20);
        
        productTable = new ProductTableModel(app.products);
        JTable table = new JTable(productTable);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a panel for the table and add the scroll pane to it
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel and add components
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(button);
        panel.add(textField);
        panel.add(tablePanel);

        // Add the panel to the frame
        add(panel);

        pack();
        setLocationRelativeTo(null);
    }
    
    public class ProductTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Item", "Quantity", "Price"};
        private List<Product> products;

        public ProductTableModel(List<Product> products) {
            this.products = products;
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //new GUI().setVisible(true);
            }
        });
    }
    
}
