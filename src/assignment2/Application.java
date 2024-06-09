/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment2;


import assignment2.GUI.MainWindow;
import assignment2.Order.OrderStatus;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author hayae
 */
public class Application {
    
    private List<Product> products = new ArrayList<>();    
    private List<Order> orders = new ArrayList<>();
    
    Database db;
    MainWindow gui;
    
    public static void main(String[] args) {
        
        Database db = new Database();
        
        Application app = new Application(db);       
        
        app.setProducts(app.resultSetToProducts(db.getProducts()));
        app.setOrders(app.resultSetToOrders(db.getOrders()));
        
        MainWindow gui = new MainWindow(app);
        app.gui = gui;
        gui.setVisible(true);
    }

    public Application(Database db) {
        this.db = db;
    }
    
    // Unpack the ResultSet of a query into a list of products
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
    
    // Unpack ResultSet into a list of Orders
    public List<Order> resultSetToOrders(ResultSet rs) {
        List<Order> temp = new ArrayList<>();  
        try {
        
            while (rs.next()) {
                temp.add(rsToOrder(rs));                
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

            return new Product(id, name, desc, cat, price, quant);     
        } catch (Exception e) {
            e.printStackTrace();
        }   
        
        return null;
    }
    
    public Order rsToOrder(ResultSet rs) {
        
        try {
            int id = rs.getInt("id");
            String fname = rs.getString("customer_fname");
            String lname = rs.getString("customer_lname");
            String ship_address = rs.getString("shipping_address");
            OrderStatus status = Order.OrderStatus.valueOf(rs.getString("status").toUpperCase());

            BigDecimal price = rs.getBigDecimal("total_price");

            return new Order(id, fname, lname, null, ship_address, status, price);     
        }catch (Exception e) {
            e.printStackTrace();
        }   
        
        return null;        
    }
    
    public void createProduct(Product newProduct) {
        System.out.println("Creating " + newProduct);
        
        String query = "INSERT INTO PRODUCTS (NAME, DESCRIPTION, CATEGORY, PRICE, QUANTITY)\n" +
            "VALUES ('" + newProduct.getTitle() + "', '" + newProduct.getDescription() + "', '" +
            newProduct.getCategory() + "', " + newProduct.getPrice() + ", " +
            newProduct.getQuantity() + ")";
        
        this.db.executeUpdate(query);
        
        products.add(newProduct);
        this.gui.productTab.productTable.updateTable(products);
    }
    
    public void updateProduct(int rowIndex, Product updatedProduct) {
        System.out.println("Updating the product " + updatedProduct.getId());
        
        String query = "UPDATE PRODUCTS SET " +
            "NAME = '" + updatedProduct.getTitle() + "', " +
            "DESCRIPTION = '" + updatedProduct.getDescription() + "', " +
            "CATEGORY = '" + updatedProduct.getCategory() + "', " +
            "PRICE = " + updatedProduct.getPrice() + ", " +
            "QUANTITY = " + updatedProduct.getQuantity() + " " +
            "WHERE ID = " + updatedProduct.getId();
        
        this.db.executeUpdateWithGeneratedKey(query);
        
        products.set(rowIndex, updatedProduct);
        this.gui.productTab.productTable.updateTable(products);
    }
    
    public int deleteProductById(int productId) {
        System.out.println("Deleting the product with ID: " + productId);

        return db.deleteProductById(productId);
    }
    
    public int createOrder(Order newOrder) {

        Product[] lineItems = newOrder.getLineItems();
        
        if (lineItems == null) {
            throw new IllegalArgumentException("Line items cannot be null");
        }
        
        String query = "INSERT INTO ORDERS (CUSTOMER_FNAME, CUSTOMER_LNAME, STATUS, SHIPPING_ADDRESS, TOTAL_PRICE)\n" +
            "VALUES ('" +
                newOrder.getCustomerFName() + "', '" +
                newOrder.getCustomerLName() + "', '" +
                newOrder.getStatus() + "', '" +
                newOrder.getShippingAddress() + "'," +
                newOrder.getTotalPrice() + ")";
        
        int orderId = this.db.executeUpdateWithGeneratedKey(query);
        
        System.out.println("ID:" + orderId);
        
        String orderItemsQuery = "INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, QUANTITY)\n VALUES ";
        
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
        this.gui.orderTab.orderTable.fireTableDataChanged();

        return orderId;
    }
    
    
    public void updateOrder(Order updatedOrder) {
        int orderId = updatedOrder.getId();
        
        System.out.println(orderId);

        String query = "UPDATE ORDERS SET " +
            "CUSTOMER_FNAME = '" + updatedOrder.getCustomerFName() + "', " +
            "CUSTOMER_LNAME = '" + updatedOrder.getCustomerLName() + "', " +
            "STATUS = '" + updatedOrder.getStatus() + "', " +
            "SHIPPING_ADDRESS = '" + updatedOrder.getShippingAddress() + "', " +
            "TOTAL_PRICE = " + updatedOrder.getTotalPrice() + " " +
            "WHERE ID = " + orderId;

        this.db.executeUpdate(query);

        String deleteOrderItemsQuery = "DELETE FROM ORDER_ITEMS WHERE ORDER_ID = " + orderId;
        this.db.executeUpdate(deleteOrderItemsQuery);

        String orderItemsQuery = "INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, QUANTITY)\n VALUES ";
        Product[] lineItems = updatedOrder.getLineItems();
        
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

        // Update the order in the orders list
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == orderId) {
                orders.set(i, updatedOrder);
                break;
            }
        }
        this.gui.orderTab.orderTable.fireTableDataChanged();
    }
    
    public HashMap<Integer,Product> loadProductsFromOrder(int orderId) {
        String query = "SELECT p.* FROM PRODUCTS p " +
                       "JOIN ORDER_ITEMS oi ON p.ID = oi.PRODUCT_ID " +
                       "WHERE oi.ORDER_ID = " + orderId;
        
        
        List<Product> orderItems = resultSetToProducts(this.db.executeQuery(query));
        HashMap<Integer,Product> prods = new HashMap<>();
        
        for (Product p : orderItems) {
            prods.put(p.getId(), p);
        }
        
        return prods;
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
    
    public List<Product> getProductsByTitle(String titleSubstring) {
        return resultSetToProducts(db.getProductByTitleSubstring(titleSubstring));
    }
    
    public Order getOrderById(int orderId) {
        return resultSetToOrders(db.getOrderById(orderId)).get(0);
    }
    
    public int deleteOrderById(int orderId) {
        return db.deleteOrdertById(orderId);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
