/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.GUI;

import assignment2.Order;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hayae
 */
public class OrderTableModel extends AbstractTableModel {
    private String[] columnNames = {"Order No.", "Customer Name", "Total Price", };
    private List<Order> orders;
    
    private OrderEditorPanel orderEditorPanel;
    
    public JPanel tablePanel;

    public OrderTableModel(OrderEditorPanel orderEditorPanel,List<Order> orders) {
        this.orders = orders;
        this.orderEditorPanel = orderEditorPanel;

        JTable table = new JTable(this); 
        addTableMouseListener(table);          
        JScrollPane scrollPane = new JScrollPane(table);
        
        this.tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public void setProducts(List<Order> orders) {
        this.orders = orders;
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        String combinedName = order.getCustomerFName() + " " + order.getCustomerLName();
        String name = combinedName.equals(" ") ? "---" : combinedName;
        switch (columnIndex) {
            case 0:
                return order.getId();
            case 1:
                return name;
            case 2:
                return order.getTotalPrice();
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
                        System.out.println("Double clicked");
                        System.out.println(orders);
                        System.out.println(rowIndex);
                        System.out.println(orders.get(rowIndex));
                        orderEditorPanel.loadOrder(rowIndex, orders.get(rowIndex));
                    }
                }
            }
        });
    }
    
}
