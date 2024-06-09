/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author hayae
 */
public class Product {
    
    private int id;
    private String title;
    private String description;
    private String category;
    private BigDecimal price;
    private int quantity;
    
    public Product(int id, String title, String description, String category, BigDecimal price, int quantity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    // For creating a product to add to an order
    public Product(Product other) {
        this.id = other.id;
        this.title = other.title;
        this.description = other.description;
        this.category = other.category;
        this.price = other.price;
        this.quantity = 1;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return this.id + " | " + this.title + " $" + this.price;
    }
}
