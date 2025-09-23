package com.extropos.java;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.extropos.java.sqlite.DatabaseHelper;
import com.extropos.java.sqlite.DatabaseManager;
import com.extropos.java.sqlite.ds.OrderDataSource;
import com.extropos.java.entity.Order;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;

public class TableOrderActivity extends Activity {
    
    private TextView tvTableName;
    private TextView tvOrderCount;
    private TextView tvTableTotal;
    private ListView listOrders;
    private Button btnNewOrder;
    private Button btnBack;
    
    private String tableName;
    private String tableId;
    private ArrayList<Order> orders;
    private OrderDataSource orderDataSource;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_order);
        
        // Get table info from intent
        tableName = getIntent().getStringExtra("table_name");
        tableId = getIntent().getStringExtra("table_id");
        
        if (tableName == null) {
            Toast.makeText(this, "Table information not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        initViews();
        setupDatabase();
        loadOrders();
        setupClickListeners();
    }
    
    private void initViews() {
        tvTableName = findViewById(R.id.tvTableName);
        tvOrderCount = findViewById(R.id.tvOrderCount);
        tvTableTotal = findViewById(R.id.tvTableTotal);
        listOrders = findViewById(R.id.listOrders);
        btnNewOrder = findViewById(R.id.btnNewOrder);
        btnBack = findViewById(R.id.btnBack);
        
        tvTableName.setText(tableName);
    }
    
    private void setupDatabase() {
        try {
            DatabaseManager.initializeInstance(new DatabaseHelper(this));
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            orderDataSource = new OrderDataSource(db);
        } catch (Exception e) {
            Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void loadOrders() {
        try {
            orders = orderDataSource.getOrdersByTable(tableName);
            double total = orderDataSource.getTableTotal(tableName);
            
            tvOrderCount.setText("Orders: " + orders.size());
            
            NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("ms", "MY"));
            tvTableTotal.setText("Total: " + currency.format(total));
            
            TableOrderAdapter adapter = new TableOrderAdapter(this, orders);
            listOrders.setAdapter(adapter);
            
        } catch (Exception e) {
            Toast.makeText(this, "Error loading orders: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void setupClickListeners() {
        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableOrderActivity.this, QuickOrderActivity.class);
                intent.putExtra("table_name", tableName);
                intent.putExtra("table_id", tableId);
                startActivity(intent);
            }
        });
        
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadOrders(); // Refresh orders when returning to this activity
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            DatabaseManager.getInstance().closeDatabase();
        } catch (Exception e) {
            // Ignore
        }
    }
}