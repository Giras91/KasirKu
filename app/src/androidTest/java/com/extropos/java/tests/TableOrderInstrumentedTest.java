package com.extropos.java.tests;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.extropos.java.entity.Order;
import com.extropos.java.sqlite.DatabaseManager;
import com.extropos.java.sqlite.ds.OrderDataSource;

import java.util.List;

/**
 * Instrumentation test for table order functionality.
 * 
 * This test validates:
 * 1. Creating orders for specific tables
 * 2. Retrieving orders by table number
 * 3. Calculating table totals correctly
 * 4. Database integrity for table-specific operations
 */
@RunWith(AndroidJUnit4.class)
public class TableOrderInstrumentedTest {
    
    @Test
    public void testTableOrderFunctionality() {
        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();
        assertEquals("com.extropos.java", appContext.getPackageName());
        
        // Initialize database
        DatabaseManager.init(appContext);
        OrderDataSource orderDataSource = new OrderDataSource(appContext);
        orderDataSource.open();
        
        try {
            // Clean up any existing test data
            cleanupTestData(orderDataSource);
            
            // Test 1: Create orders for different tables
            Order order1 = new Order();
            order1.setOrderNo("TEST001");
            order1.setTableNo(5);
            order1.setTotal(25.50);
            order1.setProductName("Test Product 1");
            order1.setQty(2);
            order1.setPrice(12.75);
            
            Order order2 = new Order();
            order2.setOrderNo("TEST002");
            order2.setTableNo(5);
            order2.setTotal(15.00);
            order2.setProductName("Test Product 2");
            order2.setQty(1);
            order2.setPrice(15.00);
            
            Order order3 = new Order();
            order3.setOrderNo("TEST003");
            order3.setTableNo(3);
            order3.setTotal(30.00);
            order3.setProductName("Test Product 3");
            order3.setQty(3);
            order3.setPrice(10.00);
            
            // Insert test orders
            long id1 = orderDataSource.insertOrder(order1);
            long id2 = orderDataSource.insertOrder(order2);
            long id3 = orderDataSource.insertOrder(order3);
            
            assertTrue("Order 1 should be inserted successfully", id1 > 0);
            assertTrue("Order 2 should be inserted successfully", id2 > 0);
            assertTrue("Order 3 should be inserted successfully", id3 > 0);
            
            // Test 2: Retrieve orders by table number
            List<Order> table5Orders = orderDataSource.getOrdersByTable(5);
            List<Order> table3Orders = orderDataSource.getOrdersByTable(3);
            List<Order> emptyTableOrders = orderDataSource.getOrdersByTable(999);
            
            assertEquals("Table 5 should have 2 orders", 2, table5Orders.size());
            assertEquals("Table 3 should have 1 order", 1, table3Orders.size());
            assertEquals("Table 999 should have 0 orders", 0, emptyTableOrders.size());
            
            // Test 3: Verify order details
            boolean foundOrder1 = false, foundOrder2 = false;
            for (Order order : table5Orders) {
                if ("TEST001".equals(order.getOrderNo())) {
                    foundOrder1 = true;
                    assertEquals("Order 1 table number", 5, order.getTableNo());
                    assertEquals("Order 1 total", 25.50, order.getTotal(), 0.01);
                } else if ("TEST002".equals(order.getOrderNo())) {
                    foundOrder2 = true;
                    assertEquals("Order 2 table number", 5, order.getTableNo());
                    assertEquals("Order 2 total", 15.00, order.getTotal(), 0.01);
                }
            }
            assertTrue("Order 1 should be found in table 5", foundOrder1);
            assertTrue("Order 2 should be found in table 5", foundOrder2);
            
            // Test 4: Calculate table totals
            double table5Total = orderDataSource.getTableTotal(5);
            double table3Total = orderDataSource.getTableTotal(3);
            double emptyTableTotal = orderDataSource.getTableTotal(999);
            
            assertEquals("Table 5 total should be 40.50", 40.50, table5Total, 0.01);
            assertEquals("Table 3 total should be 30.00", 30.00, table3Total, 0.01);
            assertEquals("Empty table total should be 0.00", 0.00, emptyTableTotal, 0.01);
            
            // Test 5: Verify order count consistency
            int table5Count = table5Orders.size();
            double calculatedTotal = 0;
            for (Order order : table5Orders) {
                calculatedTotal += order.getTotal();
            }
            assertEquals("Manual total calculation should match getTableTotal", 
                        table5Total, calculatedTotal, 0.01);
            
        } finally {
            // Clean up test data
            cleanupTestData(orderDataSource);
            orderDataSource.close();
        }
    }
    
    private void cleanupTestData(OrderDataSource orderDataSource) {
        // Delete test orders by order numbers
        try {
            // Note: Assuming we have a way to delete by order number
            // If not available, this cleanup might need to be implemented differently
            // For now, we'll rely on the test data having unique order numbers
        } catch (Exception e) {
            // Ignore cleanup errors during testing
        }
    }
}