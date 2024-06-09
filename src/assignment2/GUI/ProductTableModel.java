/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Product;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hayae
 */

public class ProductTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Item", "Quantity", "Price", "ID"};
    private List<Product> products;
    private ProductTab productTab;
    
    private JPanel tablePanel;
    
    private final int DOUBLE_CLICK_DELAY = 200; //ideally this would be derived from the system
    private Timer singleClickTimer;

    public ProductTableModel(List<Product> products, ProductTab productTab) {
        this.products = products;
        this.productTab = productTab;

        JTable table = new JTable(this);        
        this.addTableMouseListener(table);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a panel for the table and add the scroll pane to it
        this.tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }

    public void updateTable(List<Product> products) {
        System.out.println(products);
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
                return "$ " + product.getPrice().setScale(2, RoundingMode.UNNECESSARY).toString();
            case 3:
                return product.getId();
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
                    if (singleClickTimer != null) {
                        singleClickTimer.cancel();
                    }
                    singleClickTimer = new Timer();
                    singleClickTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            productTab.productDisplayPanel.setProductInfo(products.get(rowIndex));
                            productTab.productDisplayPanel.setVisible(true);
                            productTab.productEditorPanel.setVisible(false);
                        }
                    }, DOUBLE_CLICK_DELAY);
                } else if (e.getClickCount() == 2) { // Double click
                    if (singleClickTimer != null) {
                        singleClickTimer.cancel();
                    }
                    if (rowIndex >= 0) {
                        System.out.println("Attempting to load product Id:" + products.get(rowIndex).getId());
                        productTab.productEditorPanel.loadProduct(rowIndex, products.get(rowIndex).getId());
                        productTab.productEditorPanel.setVisible(true);
                        productTab.productDisplayPanel.setVisible(false);
                    }
                }
            }
        });
    }
}
