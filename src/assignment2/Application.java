/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;


import assignment2.GUI.MainWindow;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hayae
 */
public class Application {
    
    public List<Product> products = new ArrayList<>();    
    private List<Customer> customers = new ArrayList<>();    
    private List<Order> orders = new ArrayList<>();
    
    Database db;
    MainWindow gui;
    
    public static void main(String[] args) {
        
        Database db = new Database();
        
        Application app = new Application(db);        
        app.resultSetToProducts(db.getProducts());    
        MainWindow gui = new MainWindow(app);
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
                products.add(rsToProduct(rs));                
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public Product rsToProduct(ResultSet rs) {
        
        try {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String desc = rs.getString("description");
            String cat = rs.getString("category");

            BigDecimal price = rs.getBigDecimal("price");
            int quant = rs.getInt("quantity");
            Timestamp created = rs.getTimestamp("created_at");
            Timestamp updated = rs.getTimestamp("updated_at");
            //Timestamp updated = rs.getTimestamp("updated_at");

            return new Product(id, name, desc, cat, price, quant, created, updated);     
        }catch (Exception e) {
            e.printStackTrace();
        }   
        
        return null;
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
    
    public Product getProductById(int id) {
        ResultSet rs = this.db.getProductById(id);
        System.out.println(rs);
        
        try {            
            while (rs.next()) {                
                return rsToProduct(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }     
        
        return null;
    }
    
    public void printProducts() {
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
