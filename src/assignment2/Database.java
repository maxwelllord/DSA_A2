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
    private static final String dburl = "jdbc:derby://localhost:1527/InvDB";
    private static final String dbuserpass = "pdc";
    
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
            connection = DriverManager.getConnection(dburl, dbuserpass, dbuserpass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // Example methods for querying specific tables
    public ResultSet getCustomers() {
        return executeQuery("SELECT * FROM customers");
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
