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
    
    private String customerName;
    private String customerEmail;
    
    private String shippingAddress;
    private String billingAddress;
    
    private Timestamp orderDate;
    private Product[] lineItems;
    private OrderStatus status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    public enum OrderStatus {
        DRAFT("Draft"),
        PROCESSING("Processing"),
        SHIPPED("Shipped"),
        CANCELLED("Cancelled");

        private final String displayName;

        OrderStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructor
    public Order(String customerName, String customerEmail, Product[] products, String shippingAddress, String billingAddress) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.lineItems = products;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.status = OrderStatus.DRAFT;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}