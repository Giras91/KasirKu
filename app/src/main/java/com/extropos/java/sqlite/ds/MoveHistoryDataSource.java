package com.extropos.java.sqlite.ds;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.extropos.java.sqlite.DbSchema;

import java.util.ArrayList;
import com.extropos.java.entity.MoveHistory;
import android.util.Log;

public class MoveHistoryDataSource {
    private SQLiteDatabase db;

    public MoveHistoryDataSource(SQLiteDatabase db) {
        this.db = db;
    }

    public long insert(String moveId, String orderId, String fromTable, String toTable) {
        ContentValues v = new ContentValues();
        v.put("move_id", moveId);
        v.put("order_id", orderId);
        v.put("from_table", fromTable);
        v.put("to_table", toTable);
        v.put("moved_on", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        try {
            long id = db.insert("move_history", null, v);
            return id >= 0 ? id : -1;
        } catch (Exception e) {
            Log.e("MoveHistoryDS", "insert failed", e);
            return -1;
        }
    }

    public int deleteByMoveId(String moveId) {
        try {
            int rows = db.delete("move_history", "move_id = ?", new String[] { moveId });
            return rows;
        } catch (Exception e) {
            Log.e("MoveHistoryDS", "delete failed", e);
            return -1;
        }
    }

    public ArrayList<MoveHistory> getAll() {
        ArrayList<MoveHistory> rows = new ArrayList<MoveHistory>();
        String[] cols = new String[] { "move_id", "order_id", "from_table", "to_table", "moved_on" };
        Cursor c = db.query("move_history", cols, null, null, null, null, "moved_on DESC");
        while (c.moveToNext()) {
            MoveHistory mh = new MoveHistory();
            mh.setMoveId(c.getString(0));
            mh.setOrderId(c.getString(1));
            mh.setFromTable(c.getString(2));
            mh.setToTable(c.getString(3));
            mh.setMovedOn(c.getString(4));
            rows.add(mh);
        }
        c.close();
        return rows;
    }
}
