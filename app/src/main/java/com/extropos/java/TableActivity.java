package com.extropos.java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import com.extropos.java.sqlite.DatabaseHelper;
import com.extropos.java.sqlite.DatabaseManager;
import com.extropos.java.sqlite.ds.OrderDataSource;
import com.extropos.java.entity.Order;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.LinearLayout.LayoutParams;
import java.text.NumberFormat;
import java.util.Locale;

public class TableActivity extends Activity {

    private GridView gridTables;
    private String[] tables = new String[] {
        "T1","T2","T3","T4","T5","T6","T7","T8","T9"
    };
    private boolean[] occupied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        gridTables = (GridView) findViewById(R.id.gridTables);
        Button btnHist = findViewById(R.id.btnMoveHistory);
        btnHist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TableActivity.this, MoveHistoryActivity.class));
            }
        });
    // initialize occupied flags
    occupied = new boolean[tables.length];
    loadOccupiedTables();
    gridTables.setAdapter(new TableAdapter());

        gridTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TableActivity.this, TableOrderActivity.class);
                intent.putExtra("table_name", tables[position]);
                intent.putExtra("table_id", String.valueOf(position + 1));
                startActivity(intent);
            }
        });

        gridTables.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(TableActivity.this);
                b.setTitle("Table " + tables[pos]);
                b.setItems(new String[]{"Clear Table (mark paid)", "Force Free (clear table assignment)", "Move Table"}, new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        DatabaseManager.initializeInstance(new DatabaseHelper(TableActivity.this));
                        try {
                            final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
                            final OrderDataSource ods = new OrderDataSource(db);
                            final String tname = tables[pos];

                            // Process Clear or Force Free
                            if (which == 0 || which == 1) {
                                java.util.ArrayList<Order> orders = ods.getAll();
                                for (Order o : orders) {
                                    if (o.getTableName() != null && o.getTableName().equalsIgnoreCase(tname)) {
                                        if (which == 0) {
                                            // mark paid and clear table assignment
                                            o.setStatus("paid");
                                            o.setTableID(null);
                                            o.setTableName(null);
                                        } else {
                                            // force clear table assignment, keep order
                                            o.setTableID(null);
                                            o.setTableName(null);
                                        }
                                        ods.delete(o.getOrderID());
                                        ods.insert(o);
                                    }
                                }
                            }

                            // Move Table flow
                            if (which == 2) {
                                final String srcTable = tables[pos];
                                final String[] dests = new String[tables.length - 1];
                                int idx = 0;
                                for (int i = 0; i < tables.length; i++) {
                                    if (i == pos) continue;
                                    dests[idx++] = tables[i];
                                }
                                android.app.AlertDialog.Builder pick = new android.app.AlertDialog.Builder(TableActivity.this);
                                pick.setTitle("Move " + srcTable + " to:");
                                pick.setItems(dests, new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(android.content.DialogInterface dialog2, int which2) {
                                        final String destTable = dests[which2];
                                        // confirm move
                                        android.app.AlertDialog.Builder confirm = new android.app.AlertDialog.Builder(TableActivity.this);
                                        confirm.setTitle("Confirm Move");
                                        confirm.setMessage("Move all open orders from " + srcTable + " to " + destTable + "?");
                                        confirm.setPositiveButton("Move", new android.content.DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(android.content.DialogInterface dialog3, int which3) {
                                                try {
                                                    java.util.ArrayList<Order> orders2 = ods.getAll();
                                                    final java.util.ArrayList<String> movedOrderIds = new java.util.ArrayList<String>();
                                                            final java.util.ArrayList<java.util.Map<String,String>> movedRows = new java.util.ArrayList<java.util.Map<String,String>>();
                                                            final java.util.ArrayList<String> moveIds = new java.util.ArrayList<String>();
                                                    for (Order o2 : orders2) {
                                                        if (o2.getTableName() != null && o2.getTableName().equalsIgnoreCase(srcTable)) {
                                                            String st = o2.getStatus();
                                                            if (st == null || !st.equalsIgnoreCase("paid")) {
                                                                // perform update
                                                                for (int j = 0; j < tables.length; j++) {
                                                                    if (tables[j].equalsIgnoreCase(destTable)) {
                                                                        o2.setTableID(String.valueOf(j+1));
                                                                        break;
                                                                    }
                                                                }
                                                                o2.setTableName(destTable);
                                                                ods.update(o2);
                                                                movedOrderIds.add(o2.getOrderID());
                                                                // prepare move_history row
                                                                java.util.Map<String,String> row = new java.util.HashMap<String,String>();
                                                                row.put("order_id", o2.getOrderID());
                                                                row.put("from_table", srcTable);
                                                                row.put("to_table", destTable);
                                                                movedRows.add(row);
                                                                // generate move_id and store for exact undo
                                                                String mid = java.util.UUID.randomUUID().toString();
                                                                moveIds.add(mid);
                                                            }
                                                        }
                                                    }
                                                    // insert move_history rows and show Snackbar undo
                                                    if (movedOrderIds.size() > 0) {
                                                        // insert rows
                                                            try {
                                                                final SQLiteDatabase dbFinal = db;
                                                                for (int ii = 0; ii < movedRows.size(); ii++) {
                                                                    java.util.Map<String,String> r = movedRows.get(ii);
                                                                    String mid = moveIds.get(ii);
                                                                    com.extropos.java.sqlite.ds.MoveHistoryDataSource mhs = new com.extropos.java.sqlite.ds.MoveHistoryDataSource(dbFinal);
                                                                    long rv = mhs.insert(mid, r.get("order_id"), r.get("from_table"), r.get("to_table"));
                                                                    if (rv < 0) Log.w("TableActivity", "Failed to insert move_history for order " + r.get("order_id"));
                                                                }
                                                            } catch (Exception e) {
                                                                Log.e("TableActivity", "Error inserting move_history", e);
                                                            }

                                                        final View anchor = gridTables;
                                                        Snackbar.make(anchor, movedOrderIds.size() + " orders moved", Snackbar.LENGTH_LONG)
                                                                .setAction("Undo", new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        try {
                                                                            java.util.ArrayList<Order> orders3 = ods.getAll();
                                                                            for (Order o3 : orders3) {
                                                                                if (movedOrderIds.contains(o3.getOrderID())) {
                                                                                    // move back to srcTable
                                                                                    for (int j = 0; j < tables.length; j++) {
                                                                                        if (tables[j].equalsIgnoreCase(srcTable)) {
                                                                                            o3.setTableID(String.valueOf(j+1));
                                                                                            break;
                                                                                        }
                                                                                    }
                                                                                    o3.setTableName(srcTable);
                                                                                    ods.update(o3);
                                                                                }
                                                                            }
                                                                            // remove move_history entries for these moves
                                                                            try {
                                                                                final SQLiteDatabase dbDel = db;
                                                                                for (String mid : moveIds) {
                                                                                    dbDel.execSQL("DELETE FROM move_history WHERE move_id='" + mid + "'");
                                                                                }
                                                                            } catch (Exception ignore) {}
                                                                        } catch (Exception e) {}
                                                                        // refresh UI
                                                                        occupied = new boolean[tables.length];
                                                                        loadOccupiedTables();
                                                                        gridTables.setAdapter(new TableAdapter());
                                                                    }
                                                                }).show();
                                                    }
                                                } catch (Exception ex2) {
                                                    // ignore
                                                }
                                            }
                                        });
                                        confirm.setNegativeButton("Cancel", null);
                                        confirm.show();
                                    }
                                });
                                pick.setNegativeButton("Cancel", null);
                                pick.show();
                            }
                        } catch (Exception ex) {
                            // ignore
                        } finally {
                            try {
                                if (DatabaseManager.getInstance() != null) DatabaseManager.getInstance().closeDatabase();
                            } catch (Exception ignore) {}
                            // reload occupancy
                            occupied = new boolean[tables.length];
                            loadOccupiedTables();
                            gridTables.setAdapter(new TableAdapter());
                        }
                    }
                });
                b.setNegativeButton("Cancel", null);
                b.show();
                return true;
            }
        });
    }

    private void loadOccupiedTables() {
        try {
            DatabaseManager.initializeInstance(new DatabaseHelper(this));
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            OrderDataSource ods = new OrderDataSource(db);
            java.util.ArrayList<Order> orders = ods.getAll();
            for (Order o : orders) {
                    // consider table occupied only if order status is not 'paid'
                    String status = o.getStatus();
                    if (status != null && status.equalsIgnoreCase("paid"))
                        continue;
                    String tname = o.getTableName();
                    if (tname != null) {
                    for (int i = 0; i < tables.length; i++) {
                        if (tables[i].equalsIgnoreCase(tname)) {
                            occupied[i] = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private class TableAdapter extends BaseAdapter {
        @Override
        public int getCount() { return tables.length; }

        @Override
        public Object getItem(int position) { return tables[position]; }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
            }

            TextView tvTableName = convertView.findViewById(R.id.tvTableName);
            TextView tvOrderCount = convertView.findViewById(R.id.tvOrderCount);
            TextView tvTableTotal = convertView.findViewById(R.id.tvTableTotal);

            String tableName = tables[position];
            tvTableName.setText(tableName);

            // Get order count and total for this table
            try {
                DatabaseManager.initializeInstance(new DatabaseHelper(TableActivity.this));
                SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
                OrderDataSource ods = new OrderDataSource(db);
                
                java.util.ArrayList<Order> tableOrders = ods.getOrdersByTable(tableName);
                double tableTotal = ods.getTableTotal(tableName);
                
                tvOrderCount.setText(tableOrders.size() + " orders");
                
                NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("ms", "MY"));
                tvTableTotal.setText(currency.format(tableTotal));
                
                // Set background color based on occupancy
                if (tableOrders.size() > 0) {
                    convertView.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                } else {
                    convertView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                }
                
                DatabaseManager.getInstance().closeDatabase();
            } catch (Exception e) {
                tvOrderCount.setText("0 orders");
                tvTableTotal.setText("RM 0.00");
                convertView.setBackgroundColor(getResources().getColor(android.R.color.white));
            }

            return convertView;
        }
    }
}
