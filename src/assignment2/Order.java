/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

/**
 *
 * @author hayae
 */
import java.sql.Timestamp;

public class Order {
    private int id;
    
    private String customerFName;
    private String customerLName;
    
    private String shippingAddress;
    
    private Timestamp orderDate;
    private Product[] lineItems;
    private OrderStatus status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    public enum OrderStatus {
        DRAFT("Draft"),
        PROCESSING("Paid"),
        SHIPPED("Shipped"),
        CANCELLED("Cancelled");

        private final String name;

        OrderStatus(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Constructor
    public Order(String customerFName, String customerLName, Product[] products, String shippingAddress, OrderStatus status) {
        this.customerFName = customerFName;
        this.customerLName = customerLName;
        this.lineItems = products;
        this.shippingAddress = shippingAddress;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerFName() {
        return customerFName;
    }

    public void setCustomerFName(String customerFName) {
        this.customerFName = customerFName;
    }

    public String getCustomerLName() {
        return customerLName;
    }

    public void setCustomerLName(String customerLName) {
        this.customerLName = customerLName;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    
    //For updating the products in an order
    public void setLineItems(Product[] products) {
        this.lineItems = products;
    }

    public Product[] getLineItems() {
        return lineItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}