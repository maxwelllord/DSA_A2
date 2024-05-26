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
}