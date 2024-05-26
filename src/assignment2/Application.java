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
    
    List<Product> products = new ArrayList<>();    
    private List<Customer> customers = new ArrayList<>();    
    private List<Order> orders = new ArrayList<>();
    
    Database db;
    GUI gui;
    
    public static void main(String[] args) {
        
        Database db = new Database();
        
        Application app = new Application(db);        
        app.resultSetToProducts(db.getProducts());    
        GUI gui = new GUI(app);
        app.gui = gui;
        gui.setVisible(true);
        
        //Product p = new Product(-1, "PRODUCT NAME1", "test product please ignore", "testp", new BigDecimal(12.4), 100);
        
        //app.createProduct(p);
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
    
    public void createProduct(Product newProduct) {
        //title desc cat price quant
        String query = "INSERT INTO PRODUCTS (NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY)\n" +
            "VALUES ('" + newProduct.getTitle() + "', '" + newProduct.getDescription() + "', '" +
            newProduct.getCategory() + "', " + newProduct.getPrice() + ", " +
            newProduct.getQuantity() + ")";
        this.db.executeUpdate(query);
        
        products.add(newProduct);
        this.gui.productTable.updateTable(products);
    }
    
    public void printProducts() {
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
