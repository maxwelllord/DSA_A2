/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hayae
 */
public class Application {
    
    private List<Product> products = new ArrayList<>();    
    private List<Customer> customers = new ArrayList<>();    
    private List<Order> orders = new ArrayList<>();
    
    Database db;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    public Application(Database db) {
        this.db = db;
        
        //query the db to populate the arraylists
    }
    
    //unpack the result of a query into a Product object
    public void resultSetToProducts(ResultSet rs) {
        
        
        try {
        
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                String cat = rs.getString("category");
                
                BigDecimal price = rs.getBigDecimal("price");
                int quant = rs.getInt("quantity");
                
                Product newProduct = new Product(id, name, desc, cat, price, quant);
                products.add(newProduct);                
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void printProducts() {
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
