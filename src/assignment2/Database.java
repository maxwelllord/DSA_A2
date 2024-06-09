/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

/**
 *
 * @author hayae
 */
import java.sql.*;


public class Database {
    private static final String dbName = "inventoryDB";
    private static final String connectionUrl = "jdbc:derby:" + dbName + ";";
    
    private Connection connection;
    
    public static void main(String[] args) {
        Database db = new Database();
        Application app = new Application(db);
        
        ResultSet products = db.getProducts();
        app.resultSetToProducts(products);
        app.printProducts();
    }

    public Database() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
            
            //createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void createTables() {
        String query = "CREATE TABLE products (\n" +
        "    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
        "    name VARCHAR(100) NOT NULL,\n" +
        "    description VARCHAR(500),\n" +
        "    category VARCHAR(50),\n" +
        "    price DECIMAL(10, 2) NOT NULL,\n" +
        "    quantity INT NOT NULL\n" +
        ")";
        
        executeUpdate(query);
        
        query = "CREATE TABLE orders (\n" +
        "   id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
        "   customer_fname VARCHAR(50) NOT NULL,\n" +
        "   customer_lname VARCHAR(50) NOT NULL,\n" +
        "   status VARCHAR(20) NOT NULL,\n" +
        "   shipping_address VARCHAR(200) NOT NULL,\n" +
        "   total_price DECIMAL(10, 2) NOT NULL\n" +
        ")";
        
        executeUpdate(query);
        
        query = "CREATE TABLE order_items (\n" +
        "    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
        "    product_id INT NOT NULL,\n" +
        "    order_id INT NOT NULL,\n" +
        "    quantity INT NOT NULL,\n" +
        "    FOREIGN KEY (product_id) REFERENCES products(id),\n" +
        "    FOREIGN KEY (order_id) REFERENCES orders(id)\n" +
        ")";
        
        executeUpdate(query);
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int executeUpdateWithGeneratedKey(String query) {
        int generatedKey = -1;
    ResultSet resultSet = null;


        try {
            Statement statement = connection.createStatement();

            int rowsAffected = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedKey = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately (e.g., log the error, throw a custom exception)
            e.printStackTrace();
        }

        return generatedKey;
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public ResultSet getProductById(int id) {
        
         try {
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
             
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
    }

    public ResultSet getOrders() {
        return executeQuery("SELECT * FROM orders");
    }

    public ResultSet getProducts() {
        return executeQuery("SELECT * FROM products");
    }   
    
    public ResultSet getProductByTitleSubstring(String titleSubstring) {
        String query = "SELECT * FROM products WHERE name LIKE '%" + titleSubstring + "%'";
        System.out.println(query);
        return executeQuery(query);
    }
}
