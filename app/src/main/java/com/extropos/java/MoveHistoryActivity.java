package com.extropos.java;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.app.AlertDialog;
import android.util.Log;

import com.extropos.java.sqlite.DatabaseHelper;
import com.extropos.java.sqlite.DatabaseManager;

import java.sql.Timestamp;
import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

import com.extropos.java.sqlite.ds.MoveHistoryDataSource;
import com.extropos.java.entity.MoveHistory;
import com.extropos.java.MoveHistoryAdapter;

public class MoveHistoryActivity extends Activity {

    private RecyclerView listHistory;
    private MoveHistoryAdapter adapter;
    private static final String TAG = "MoveHistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_history);
    listHistory = findViewById(R.id.listHistory);
    listHistory.setLayoutManager(new LinearLayoutManager(this));
    loadHistory();
    }

    private void loadHistory() {
        final java.util.ArrayList<MoveHistory> rows = new java.util.ArrayList<MoveHistory>();
        try {
            DatabaseManager.initializeInstance(new DatabaseHelper(this));
            final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            MoveHistoryDataSource mhs = new MoveHistoryDataSource(db);
            rows.addAll(mhs.getAll());
        } catch (Exception e) {
            Log.e(TAG, "Failed to load move history", e);
        } finally {
            try { if (DatabaseManager.getInstance() != null) DatabaseManager.getInstance().closeDatabase(); } catch (Exception ignore) {}
        }

        adapter = new MoveHistoryAdapter(rows, new MoveHistoryAdapter.Callback() {
            @Override public void onItemClicked(MoveHistory mh) {
                // detail view
                new AlertDialog.Builder(MoveHistoryActivity.this)
                        .setTitle("Move Details")
                        .setMessage("Order: " + mh.getOrderId() + "\nFrom: " + mh.getFromTable() + "\nTo: " + mh.getToTable() + "\nWhen: " + mh.getMovedOn())
                        .setPositiveButton("Close", null)
                        .show();
            }

            @Override public void onItemSwiped(MoveHistory mh) {
                // handled via ItemTouchHelper in-place
            }
        });

        listHistory.setAdapter(adapter);

        // swipe to delete
        ItemTouchHelper.SimpleCallback cb = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) { return false; }
            @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                MoveHistory mh = adapter.getItem(pos);
                // delete with datasource
                try {
                    DatabaseManager.initializeInstance(new DatabaseHelper(MoveHistoryActivity.this));
                    SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
                    com.extropos.java.sqlite.ds.MoveHistoryDataSource mhs = new com.extropos.java.sqlite.ds.MoveHistoryDataSource(db);
                    int deleted = mhs.deleteByMoveId(mh.getMoveId());
                    if (deleted > 0) {
                        adapter.remove(pos);
                    } else {
                        // restore item
                        adapter.notifyItemChanged(pos);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to delete move_history", e);
                    adapter.notifyItemChanged(pos);
                } finally {
                    try { if (DatabaseManager.getInstance() != null) DatabaseManager.getInstance().closeDatabase(); } catch (Exception ignore) {}
                }
            }
        };
        new ItemTouchHelper(cb).attachToRecyclerView(listHistory);
    }
}
