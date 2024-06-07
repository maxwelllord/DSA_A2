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
        app.products = app.resultSetToProducts(db.getProducts());    
        MainWindow gui = new MainWindow(app);
        app.gui = gui;
        gui.setVisible(true);
    }

    public Application(Database db) {
        this.db = db;
        
        //query the db to populate the arraylists
    }
    
    //unpack the result of a query into a Product object
    public List<Product> resultSetToProducts(ResultSet rs) {
        List<Product> temp = new ArrayList<>();  
        try {
        
            while (rs.next()) {
                temp.add(rsToProduct(rs));                
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }  
        
        return temp;
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
        this.gui.productTab.productTable.updateTable(products);
    }
    
    public void createOrder(Order newOrder) {
        
        String query = "INSERT INTO ORDERS (CUSTOMER_FNAME, CUSTOMER_LNAME, STATUS, SHIPPING_ADDRESS)\n" +
            "VALUES ('" +
                newOrder.getCustomerFName() + "', '" +
                newOrder.getCustomerLName() + "', '" +
                newOrder.getStatus() + "', '" +
                newOrder.getShippingAddress() + "')";
        
        int orderId = this.db.executeUpdateWithGeneratedKey(query);
        
        System.out.println("ID:" + orderId);
        
        String orderItemsQuery = "INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, QUANTITY)\n VALUES ";

        Product[] lineItems = newOrder.getLineItems();
        for (int i = 0; i < lineItems.length; i++) {
            Product lineItem = lineItems[i];
            System.out.println(lineItem);
            orderItemsQuery += "(" + orderId + "," +
                lineItem.getId() + ", " +
                lineItem.getQuantity() + ")";

            if (i < lineItems.length - 1) {
                orderItemsQuery += ", ";
            }
        }

        this.db.executeUpdate(orderItemsQuery);
        
        orders.add(newOrder); //add the order to the order table
        
        //this.gui.productTab.productTable.updateTable(products);
        
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
    
    public List<Product> getProductsByTitle(String titleSubstring) {
        return resultSetToProducts(db.getProductByTitleSubstring(titleSubstring));
    }
}
