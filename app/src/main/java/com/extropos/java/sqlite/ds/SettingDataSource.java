package com.extropos.java.sqlite.ds;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.extropos.java.entity.Setting;
import com.extropos.java.sqlite.DbSchema;

public class SettingDataSource {
	
	private SQLiteDatabase db;
	public SettingDataSource(SQLiteDatabase db)
	{
		this.db = db;
	}
	
	public long truncate()
	{
		return db.delete(DbSchema.TBL_SETTING,null,null);
	}
	
	public Setting get(String code) {
		 
		Setting item = new Setting();
		 
		String selectQuery = " SELECT  *  FROM " + DbSchema.TBL_SETTING  + 
						       " Where " +DbSchema.COL_SETTING_CODE + " = '"+code+"'";
		
		Cursor c = db.rawQuery(selectQuery, null);
	
		if (c.moveToFirst()) {
			do {
				int colIndex;
				
				colIndex = c.getColumnIndex(DbSchema.COL_SETTING_CODE);
				if (colIndex >= 0) item.setCode(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_SETTING_VALUE);
				if (colIndex >= 0) item.setValue(c.getString(colIndex));
				
			} while (c.moveToNext());
		}
		return item;
	}
	

	public ArrayList<Setting> getAll() {
		 
		ArrayList<Setting> items = new ArrayList<Setting>();
		String selectQuery = " SELECT  *  FROM " + DbSchema.TBL_SETTING ;
		Cursor c = db.rawQuery(selectQuery, null);
	
		if (c.moveToFirst()) {
			do {
				Setting item = new Setting();
				int colIndex;
				
				colIndex = c.getColumnIndex(DbSchema.COL_SETTING_CODE);
				if (colIndex >= 0) item.setCode(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_SETTING_VALUE);
				if (colIndex >= 0) item.setValue(c.getString(colIndex));
				
				items.add(item);
			} while (c.moveToNext());
		}
	
		return items;
	}
	
	public long insert(Setting item)
	{
		ContentValues values = new ContentValues();
		values.put(DbSchema.COL_SETTING_CODE, item.getCode());
		values.put(DbSchema.COL_SETTING_VALUE, item.getValue());
		
		return db.insert(DbSchema.TBL_SETTING, null, values);
	}
	
	public long update(Setting item,String lastCode)
	{
		ContentValues values = new ContentValues();
		values.put(DbSchema.COL_SETTING_CODE, item.getCode());
		values.put(DbSchema.COL_SETTING_VALUE, item.getValue());
		
		return db.update(DbSchema.TBL_SETTING, values, DbSchema.COL_SETTING_CODE+"= '"+lastCode+"' ", null);
	}
	
	public int delete(String code)
	{
		return db.delete(DbSchema.TBL_SETTING, DbSchema.COL_SETTING_CODE + "= '" + code + "'", null);
	}
	
	public boolean cekCode(String code) {
		 
		boolean has = false;
		String selectQuery = " SELECT  * FROM " + DbSchema.TBL_SETTING  + 
						      " Where lower(" +DbSchema.COL_SETTING_CODE + ") = '"+code.toLowerCase()+"'";
		 
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.getCount() > 0)
			has = true;
			
		return has;
	}

}
