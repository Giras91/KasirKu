package com.extropos.java.tests;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.extropos.java.sqlite.DatabaseHelper;
import com.extropos.java.sqlite.DatabaseManager;
import com.extropos.java.sqlite.ds.OrderDataSource;
import com.extropos.java.sqlite.ds.MoveHistoryDataSource;
import com.extropos.java.entity.Order;
import com.extropos.java.entity.OrderDetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MoveHistoryInstrumentedTest {
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        DatabaseManager.initializeInstance(new DatabaseHelper(context));
    }

    @After
    public void tearDown() throws Exception {
        try {
            DatabaseManager.getInstance().closeDatabase();
        } catch (Exception ignore) {}
    }

    @Test
    public void testMoveAndUndoFlow() throws Exception {
        // Open DB
        android.database.sqlite.SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        OrderDataSource ods = new OrderDataSource(db);
        MoveHistoryDataSource mhs = new MoveHistoryDataSource(db);

    // Create test order
    String orderId = "TEST-" + UUID.randomUUID().toString();
    Order o = new Order();
    o.setOrderID(orderId);
    java.util.Date now = new java.util.Date();
    o.setCreatedOn(now);
    o.setUpdatedOn(now);
    o.setAmount(10.0);
    o.setUserID("test-user");
    o.setBranchID("test-branch");
    o.setTableID("T1");
    o.setTableName("Table 1");
    o.setOrderDetails(new java.util.ArrayList<com.extropos.java.entity.OrderDetails>());
    long created = ods.insert(o);
    assertTrue("Order should be created", created >= 0);

        // Verify initial table
        Order fetched = ods.get(orderId);
        assertNotNull(fetched);
        assertEquals("Table 1", fetched.getTableName());

        // Perform move: update order to T2 and insert move_history
        String moveId = UUID.randomUUID().toString();
        int updated = ods.updateTableAssignment(orderId, "T2", "Table 2");
        assertTrue("Order should be updated", updated >= 0);
        long mhInserted = mhs.insert(moveId, orderId, "Table 1", "Table 2");
        assertTrue("Move history should be inserted", mhInserted >= 0);

        // Verify moved
        Order moved = ods.get(orderId);
        assertEquals("Table 2", moved.getTableName());

        // Undo: restore table and delete move_history by moveId
        int restored = ods.updateTableAssignment(orderId, "T1", "Table 1");
        assertTrue("Order should be restored", restored >= 0);
        int del = mhs.deleteByMoveId(moveId);
        assertTrue("Move history should be deleted", del >= 0);

        // Verify restored
        Order restoredOrder = ods.get(orderId);
        assertEquals("Table 1", restoredOrder.getTableName());

        // Cleanup: delete order
        int delOrder = ods.delete(orderId);
        assertTrue("Order should be deleted", delOrder >= 0);

        // close db
        DatabaseManager.getInstance().closeDatabase();
    }
}
