/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package assignment2;

import assignment2.GUI.MainWindow;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hayae
 */
public class ApplicationTest {
    
    Database db;
    Application app;
    
    @Before
    public void init() {        
        db = new Database();        
        app = new Application(db);       
        
        MainWindow gui = new MainWindow(app);
        app.gui = gui;
    }
    

    @Test
    public void createAddEditAndDeleteProduct() {
        
        // Create a new product to test
        Product testProduct = new Product(-1, "TEST PRODUCT", "Test description", "Category 5", new BigDecimal(10.00), 122);
        
        // Push it to the database
        app.createProduct(testProduct);
        
        // Retrieve it via its title substring
        Product retrievedProduct = app.getProductsByTitle("TEST").get(0);
        
        // Some data we want to update
        String newTitle = "T3ST PRODUCT";
        String newCategory = "CAT1";
        
        // Set the new data
        retrievedProduct.setTitle(newTitle);
        retrievedProduct.setCategory(newCategory);
        
        // Update the product in the DB
        app.updateProduct(0, retrievedProduct); //because we don't care about the GUI for this test we can just set the row index to 0
        
        // Now retrieve it
        retrievedProduct = app.getProductsByTitle("T3ST").get(0);
        
        // Check if our values have been updated
        Assert.assertEquals(retrievedProduct.getTitle(), newTitle);        
        Assert.assertEquals(retrievedProduct.getCategory(), newCategory);
        
        // Try to delete the product we just created, fail if method returns 0
        // 0 means zero rows were updated (and thus no product was deleted
        if (app.deleteProductById(retrievedProduct.getId()) == 0) {
            Assert.fail("Couldn't delete product, No product was found by that ID");
        }
    }
    
    @Test
    public void deleteNonExistentProductById() {
        int returnedValue = app.deleteProductById(999999);
        Assert.assertEquals(0, returnedValue);
    }
    

    @Test(expected = IllegalArgumentException.class)
    public void createOrderWithNoProducts() {
        
        // Create a new order to test
        Order testOrder = new Order("PRODUCT WITH NO ORDERS", "", null, "123 fake street", Order.OrderStatus.DRAFT, new BigDecimal(0.00));
        
        //  Try to create our productless order
        app.createOrder(testOrder);
    }
    
    @Test
    public void deleteNonExistentOrderById() {
        int returnedValue = app.deleteOrderById(999999);
        Assert.assertEquals(0, returnedValue);
    }
    
    @Test
    public void editorOrderProducts() {
        
        app.setProducts(app.resultSetToProducts(db.getProducts())); 
        
        // Grab the first second and third products
        Product firstProduct = app.getProducts().get(0);
        Product secondProduct = app.getProducts().get(1);
        Product thirdProduct = app.getProducts().get(2);
        
        Product[] prods = {firstProduct, secondProduct};
        
        Order testOrder = new Order("TEST EDITING ORDER PRODUCTS", "", prods, "123 fake street", Order.OrderStatus.DRAFT, new BigDecimal(0.00));
        
        //  Create our order and grab its id
        int orderId = app.createOrder(testOrder);
        
        // Now lets change our line items
        Product[] productsToSet = {secondProduct, thirdProduct};
        
        //Set the id for our id-less order and change the line items to our new products
        testOrder.setId(orderId);
        testOrder.setLineItems(productsToSet);
        
        //update the database
        app.updateOrder(testOrder);
        
        //Grab the products from our order
        Product[] updatedProducts = app.loadProductsFromOrder(orderId).values().toArray(new Product[0]);
        
        //Because the products get pushed into the database they need to be checked in reverse order
        Assert.assertEquals(updatedProducts[0].getTitle(),productsToSet[1].getTitle());         
        Assert.assertEquals(updatedProducts[0].getTitle(),productsToSet[1].getTitle());  
        
        //Clean up our test products
        db.deleteProductsFromOrder(orderId, updatedProducts);
        
        //Delete our test order
        app.deleteOrderById(orderId);    
    }
    
    @Test
    public void createEditDeleteOrder() {   
        
        app.setProducts(app.resultSetToProducts(db.getProducts())); 
        
        //for the test order let's just use the first product in the database
        Product firstProduct = app.getProducts().get(0);
        
        Product[] prods = {firstProduct};
        
        // Some initial data to test later
        String cFName = "TESTCUSTOMER FNAME";
        Order.OrderStatus status = Order.OrderStatus.DRAFT;
        
        // Create a new order to test
        Order testOrder = new Order(cFName, "TESTCUSTOMER LNAME", prods, "123 fake street", status, new BigDecimal(0.00));
        
        //  Create our order and grab its id
        int orderId = app.createOrder(testOrder);
        
        // Retrieve it via its title substring
        Order retrievedOrder = app.getOrderById(orderId);
        
        //Check some of our data is the same
        Assert.assertEquals(retrievedOrder.getCustomerFName(), cFName);        
        Assert.assertEquals(retrievedOrder.getStatus(), status);
        
        //Define some new vars to update
        String newCFName = "NEW FNAME";
        Order.OrderStatus newStatus = Order.OrderStatus.CANCELLED;
        
        //Set the vars
        retrievedOrder.setCustomerFName(newCFName);
        retrievedOrder.setStatus(newStatus);
        
        // Retrieve products for the order to be updated
        retrievedOrder.setLineItems(app.loadProductsFromOrder(orderId).values().toArray(new Product[0]));
        
        //Update the order
        app.updateOrder(retrievedOrder);
        
        // Get the order again
        retrievedOrder = app.getOrderById(orderId);
        
        // Check some of our data
        Assert.assertEquals(retrievedOrder.getCustomerFName(), newCFName);        
        Assert.assertEquals(retrievedOrder.getStatus(), newStatus);
        
        // Before we can delete our order we need to retrieve and then delete our products  
        retrievedOrder.setLineItems(app.loadProductsFromOrder(orderId).values().toArray(new Product[0]));        
        db.deleteProductsFromOrder(orderId, retrievedOrder.getLineItems());
        
        //System.out.println(retrievedOrder);
        if (app.deleteOrderById(orderId) == 0) {
            Assert.fail("Couldn't delete order, No order was found by that ID");
        }
    }
}
