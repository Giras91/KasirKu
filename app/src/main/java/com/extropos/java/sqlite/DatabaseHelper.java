package com.extropos.java.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	public DatabaseHelper(Context context) {
		super(context, DbSchema.DB_NAME, null, DbSchema.DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DbSchema.CREATE_TBL_PRODUCT_CATEGORY);
		db.execSQL(DbSchema.CREATE_TBL_PRODUCT);
		db.execSQL(DbSchema.CREATE_TBL_SETTING);
		db.execSQL(DbSchema.CREATE_TBL_USER);
		db.execSQL(DbSchema.CREATE_TBL_ORDER);
		db.execSQL(DbSchema.CREATE_TBL_PRODUCT_ORDER_DETAIL);
		// create move history table
		db.execSQL(DbSchema.CREATE_TBL_MOVE_HISTORY);
		// create table service table
		db.execSQL(DbSchema.CREATE_TBL_TABLE_SERVICE);
		db.execSQL(DbSchema.INSERT_TBL_USER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Strict migration from version 1 -> 2: backup product_order, recreate schema, restore
		if (oldVersion < 2) {
			try {
				// backup existing data
				db.execSQL("CREATE TABLE product_order_backup AS SELECT * FROM " + DbSchema.TBL_ORDER + ";");
				// drop old table and recreate all tables (new schema includes status and move_history)
				db.execSQL(DbSchema.DROP_TBL_PRODUCT_ORDER);
				db.execSQL(DbSchema.CREATE_TBL_ORDER);
				db.execSQL(DbSchema.CREATE_TBL_PRODUCT_ORDER_DETAIL);
				db.execSQL(DbSchema.CREATE_TBL_MOVE_HISTORY);
				// restore data: map existing columns and leave status NULL
				db.execSQL("INSERT INTO " + DbSchema.TBL_ORDER + "(order_id, ordered_on, updated_on, sycn_on, description, tax, discount, amount, user_id, branch_id, table_id, table_name) SELECT order_id, ordered_on, updated_on, sycn_on, description, tax, discount, amount, user_id, branch_id, table_id, table_name FROM product_order_backup;");
				// drop backup
				db.execSQL("DROP TABLE IF EXISTS product_order_backup;");
			} catch (Exception e) {
				// fallback to best-effort ALTER
				try { db.execSQL("ALTER TABLE " + DbSchema.TBL_ORDER + " ADD COLUMN " + DbSchema.COL_ORDER_STATUS + " TEXT;"); } catch (Exception ignore) {}
			}
		}
		// Ensure move_history exists when upgrading to DB_VERSION 3
		if (oldVersion < 3) {
			try {
				db.execSQL(DbSchema.CREATE_TBL_MOVE_HISTORY);
			} catch (Exception ignore) {}
		}
		// Ensure table_service exists when upgrading to DB_VERSION 4
		if (oldVersion < 4) {
			try {
				db.execSQL(DbSchema.CREATE_TBL_TABLE_SERVICE);
			} catch (Exception ignore) {}
		}
		// For future major upgrades, fallback recreate
		if (oldVersion < newVersion && oldVersion >= 2) {
			db.execSQL(DbSchema.DROP_TBL_PRODUCT_CATEGORY);
			db.execSQL(DbSchema.DROP_TBL_PRODUCT);
			db.execSQL(DbSchema.DROP_TBL_SETTING);
			db.execSQL(DbSchema.DROP_TBL_USER);
			db.execSQL(DbSchema.DROP_TBL_ORDER);
			db.execSQL(DbSchema.DROP_TBL_PRODUCT_ORDER_DETAIL);
			db.execSQL(DbSchema.DROP_TBL_MOVE_HISTORY);
			db.execSQL(DbSchema.DROP_TBL_TABLE_SERVICE);
			onCreate(db);
		}
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		try {
			// ensure move_history exists for older DBs opened by newer code
			db.execSQL("CREATE TABLE IF NOT EXISTS " + DbSchema.TBL_MOVE_HISTORY + " (" + DbSchema.COL_MOVE_HISTORY_ID + " TEXT PRIMARY KEY, " + DbSchema.COL_MOVE_HISTORY_ORDER_ID + " TEXT, " + DbSchema.COL_MOVE_HISTORY_FROM + " TEXT, " + DbSchema.COL_MOVE_HISTORY_TO + " TEXT, " + DbSchema.COL_MOVE_HISTORY_MOVED_ON + " DATETIME" + ");");
			// ensure table_service exists for older DBs opened by newer code
			db.execSQL("CREATE TABLE IF NOT EXISTS " + DbSchema.TBL_TABLE_SERVICE + " (" +
				DbSchema.COL_TABLE_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				DbSchema.COL_TABLE_SERVICE_TABLE_ID + " INTEGER, " +
				DbSchema.COL_TABLE_SERVICE_TABLE_NAME + " TEXT, " +
				DbSchema.COL_TABLE_SERVICE_STATUS + " TEXT, " +
				DbSchema.COL_TABLE_SERVICE_WAITER_ID + " TEXT, " +
				DbSchema.COL_TABLE_SERVICE_WAITER_NAME + " TEXT, " +
				DbSchema.COL_TABLE_SERVICE_CUSTOMER_NAME + " TEXT, " +
				DbSchema.COL_TABLE_SERVICE_CUSTOMER_PHONE + " TEXT, " +
				DbSchema.COL_TABLE_SERVICE_SERVICE_START_TIME + " INTEGER, " +
				DbSchema.COL_TABLE_SERVICE_SERVICE_END_TIME + " INTEGER, " +
				DbSchema.COL_TABLE_SERVICE_RESERVATION_TIME + " INTEGER, " +
				DbSchema.COL_TABLE_SERVICE_SPECIAL_REQUESTS + " TEXT, " +
				DbSchema.COL_TABLE_SERVICE_NOTES + " TEXT, " +
				DbSchema.COL_TABLE_SERVICE_ESTIMATED_BILL + " REAL, " +
				DbSchema.COL_TABLE_SERVICE_GUEST_COUNT + " INTEGER" +
				");");
		} catch (Exception ignore) {}
	}
}
