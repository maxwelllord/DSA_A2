/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Product;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hayae
 */

public class ProductTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Item", "Quantity", "Price"};
        private List<Product> products;
        private MainWindow gui;

        public ProductTableModel(List<Product> products, MainWindow gui) {
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
                    
                    int rowIndex = table.rowAtPoint(e.getPoint());
                    
                    if (e.getClickCount() == 1) { // Single click
                        
                        gui.hideAllPanels();
                        gui.productDisplayPanel.setProductInfo(products.get(rowIndex));
                        gui.productDisplayPanel.setVisible(true);
                    
                    } else if (e.getClickCount() == 2) { // Double click
                        if (rowIndex >= 0) {
                            // Handle double click event on the row
                            
                            // Perform desired action with the double-clicked product
                            System.out.println("Attempting to load product Id:" + products.get(rowIndex).getId());
                            gui.productEditorPanel.loadProduct(products.get(rowIndex).getId());
                            
                            gui.hideAllPanels();
                            gui.productEditorPanel.setVisible(true);
                            
                            
                        }
                    }
                }
            });
        }
    }
