/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Product;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 *
 * @author hayae
 */
public class ProductSearchTableModel extends AbstractTableModel{
    private String[] columnNames = {"ID", "Title", "Description", "Category", "Price", "Quantity"};
    private List<Product> products;
    
    private OrderEditorPanel orderEditorPanel;

    public ProductSearchTableModel(OrderEditorPanel orderEditorPanel,List<Product> products) {
        this.orderEditorPanel = orderEditorPanel;
        this.products = products;
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
    
    public void setProducts(List<Product> products) {
        this.products = products;
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getId();
            case 1:
                return product.getTitle();
            case 2:
                return product.getDescription();
            case 3:
                return product.getCategory();
            case 4:
                return product.getPrice();
            case 5:
                return product.getQuantity();
            default:
                return null;
        }
    }

    public void addTableMouseListener(final JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int rowIndex = table.rowAtPoint(e.getPoint());
                if (e.getClickCount() == 2) { // Double click
                    if (rowIndex >= 0) {
                        
                        System.out.println("Adding product:" + products.get(rowIndex).getId());
                        
                        orderEditorPanel.addProduct(products.get(rowIndex));
                    }
                }
            }
        });
    }
}
