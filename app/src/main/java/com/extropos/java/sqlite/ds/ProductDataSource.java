package com.extropos.java.sqlite.ds;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.extropos.java.entity.Product;
import com.extropos.java.sqlite.DbSchema;
import com.extropos.java.utils.Shared;

public class ProductDataSource {
	private SQLiteDatabase db;
	public ProductDataSource(SQLiteDatabase db)
	{
		this.db = db;
	}
	
	public long truncate()
	{
		return db.delete(DbSchema.TBL_PRODUCT,null,null);
	}
	
	public Product get(String code) {
		 
		Product item = new Product();
		 
		String selectQuery = " SELECT  *  FROM " + DbSchema.TBL_PRODUCT  + 
						       " Where " +DbSchema.COL_PRODUCT_CODE + " = '"+code+"'";
		
		Cursor c = db.rawQuery(selectQuery, null);
	
		if (c.moveToFirst()) {
			do {
				int colIndex;
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CODE);
				if (colIndex >= 0) item.setProductID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_NAME);
				if (colIndex >= 0) item.setProductName(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CATEGORY_CODE);
				if (colIndex >= 0) item.setCategoryID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CATEGORY_NAME);
				if (colIndex >= 0) item.setCategoryName(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_DESCRIPTION);
				if (colIndex >= 0) item.setDescription(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_PRICE);
				if (colIndex >= 0) item.setPrice(c.getDouble(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_DISCOUNT);
				if (colIndex >= 0) item.setDiscount(c.getDouble(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CREATED_BY);
				if (colIndex >= 0) item.setCreateBy(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_UPDATED_BY);
				if (colIndex >= 0) item.setUpdatedBy(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_MERCHANT_ID);
				if (colIndex >= 0) item.setMerchantID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_STATUS);
				if (colIndex >= 0) item.setStatus(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_REF_ID);
				if (colIndex >= 0) item.setRefID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_IMAGE);
				if (colIndex >= 0) item.setImage(c.getString(colIndex));
				
				try {  
					colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CREATED_ON);
					if (colIndex >= 0) item.setCreatedOn(Shared.dateformat.parse(c.getString(colIndex)));
					
					colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_UPDATED_ON);
					if (colIndex >= 0) item.setUpdatedOn(Shared.dateformat.parse(c.getString(colIndex)));
					
					colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_SYCN_ON);
					if (colIndex >= 0) item.setSycnOn(Shared.dateformat.parse(c.getString(colIndex)));
				} catch (Exception e) {  
				}
			
			} while (c.moveToNext());
		}
		return item;
	}
	
	
	public ArrayList<Product> getAll() {
		return getAll(true,null,null);
	}

	public ArrayList<Product> getAll(String keyword,String categoryid) {
		return getAll(false,keyword,categoryid);
	}
	
	public ArrayList<Product> getAll(boolean isAll,String keyword,String categoryid) {
		 
		ArrayList<Product> items = new ArrayList<Product>();

		ArrayList<String> where = new ArrayList<String>();


		String 	selectQuery = " SELECT  *  FROM " + DbSchema.TBL_PRODUCT;
		if(!isAll)
			where.add(DbSchema.COL_PRODUCT_STATUS + " = 1 ");
		if(keyword != null && !keyword.equals(""))
			where.add(DbSchema.COL_PRODUCT_NAME + " like '%" + keyword + "%' ");
		if(categoryid != null && !categoryid.equals(""))
			where.add(DbSchema.COL_PRODUCT_CATEGORY_CODE+ " = '" + categoryid + "' ");

		if(where.size() != 0)
			selectQuery += " where " + TextUtils.join(" AND ",where);

		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				Product item = new Product();
				int colIndex;
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CODE);
				if (colIndex >= 0) item.setProductID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_NAME);
				if (colIndex >= 0) item.setProductName(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CATEGORY_CODE);
				if (colIndex >= 0) item.setCategoryID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CATEGORY_NAME);
				if (colIndex >= 0) item.setCategoryName(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_DESCRIPTION);
				if (colIndex >= 0) item.setDescription(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_PRICE);
				if (colIndex >= 0) item.setPrice(c.getDouble(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_DISCOUNT);
				if (colIndex >= 0) item.setDiscount(c.getDouble(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CREATED_BY);
				if (colIndex >= 0) item.setCreateBy(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_UPDATED_BY);
				if (colIndex >= 0) item.setUpdatedBy(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_MERCHANT_ID);
				if (colIndex >= 0) item.setMerchantID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_STATUS);
				if (colIndex >= 0) item.setStatus(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_REF_ID);
				if (colIndex >= 0) item.setRefID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_IMAGE);
				if (colIndex >= 0) item.setImage(c.getString(colIndex));
				
				try {  
					colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_CREATED_ON);
					if (colIndex >= 0) item.setCreatedOn(Shared.dateformat.parse(c.getString(colIndex)));
					
					colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_UPDATED_ON);
					if (colIndex >= 0) item.setUpdatedOn(Shared.dateformat.parse(c.getString(colIndex)));
					
					colIndex = c.getColumnIndex(DbSchema.COL_PRODUCT_SYCN_ON);
					if (colIndex >= 0) item.setSycnOn(Shared.dateformat.parse(c.getString(colIndex)));
				} catch (Exception e) {  
				}
				
				items.add(item);
			} while (c.moveToNext());
		}
	
		return items;
	}
	
	public long insert(Product item)
	{
		ContentValues values = new ContentValues();
		values.put(DbSchema.COL_PRODUCT_CODE, item.getProductID());
		values.put(DbSchema.COL_PRODUCT_NAME, item.getProductName());
		values.put(DbSchema.COL_PRODUCT_CATEGORY_CODE, item.getCategoryID());
		values.put(DbSchema.COL_PRODUCT_DESCRIPTION, item.getDescription());
		values.put(DbSchema.COL_PRODUCT_PRICE, item.getPrice());
		values.put(DbSchema.COL_PRODUCT_DISCOUNT,item.getDiscount());
		values.put(DbSchema.COL_PRODUCT_CREATED_BY, "Device");
		values.put(DbSchema.COL_PRODUCT_UPDATED_BY, "Device");
		values.put(DbSchema.COL_PRODUCT_MERCHANT_ID, item.getMerchantID());
		values.put(DbSchema.COL_PRODUCT_STATUS, item.getStatus());
		values.put(DbSchema.COL_PRODUCT_REF_ID, item.getRefID());
		values.put(DbSchema.COL_PRODUCT_IMAGE, item.getImage());
		values.put(DbSchema.COL_PRODUCT_CREATED_ON,  Shared.dateformat.format(new Date()));
		values.put(DbSchema.COL_PRODUCT_UPDATED_ON,  Shared.dateformat.format(new Date()));
	//	values.put(DbSchema.COL_PRODUCT_SYCN_ON, Shared.dateformat.format(item.getSycnOn()));
		
		return db.insert(DbSchema.TBL_PRODUCT, null, values);
	}
	
	public long update(Product item,String lastCode)
	{
		ContentValues values = new ContentValues();
		if(item.getProductID() != null)
			values.put(DbSchema.COL_PRODUCT_CODE, item.getProductID());
		
		if(item.getProductName() != null)
			values.put(DbSchema.COL_PRODUCT_NAME, item.getProductName());
		
		if(item.getCategoryID() != null)
			values.put(DbSchema.COL_PRODUCT_CATEGORY_CODE, item.getCategoryID());
		
		if(item.getDescription() != null)
			values.put(DbSchema.COL_PRODUCT_DESCRIPTION, item.getDescription());
		
	//	if(item.getCreateBy() != null)
	//		values.put(DbSchema.COL_PRODUCT_CREATED_BY,item.getCreateBy());
		
	//	if(item.getUpdatedBy() != null)
			values.put(DbSchema.COL_PRODUCT_UPDATED_BY, "Device");
		
		if(item.getStatus() != null)
			values.put(DbSchema.COL_PRODUCT_STATUS, item.getStatus());
		
		if(item.getImage() != null)
			values.put(DbSchema.COL_PRODUCT_IMAGE, item.getImage());
		
		if(item.getRefID() != null)
			values.put(DbSchema.COL_PRODUCT_REF_ID, item.getRefID());
		
		if(item.getMerchantID() != null)
			values.put(DbSchema.COL_PRODUCT_MERCHANT_ID, item.getMerchantID());
		
		
		values.put(DbSchema.COL_PRODUCT_PRICE, item.getPrice());
		values.put(DbSchema.COL_PRODUCT_DISCOUNT, item.getDiscount());
		
	//	values.put(DbSchema.COL_PRODUCT_CREATED_ON, Shared.dateformat.format(item.getCreatedOn()));
		values.put(DbSchema.COL_PRODUCT_UPDATED_ON, Shared.dateformat.format(new Date()));
	//	values.put(DbSchema.COL_PRODUCT_SYCN_ON, Shared.dateformat.format(item.getSycnOn()));
		
		return db.update(DbSchema.TBL_PRODUCT, values, DbSchema.COL_PRODUCT_CODE+"= '"+lastCode+"' ", null);
	}
	
	public int delete(String code)
	{
		return db.delete(DbSchema.TBL_PRODUCT, DbSchema.COL_PRODUCT_CODE + "= '" + code + "'", null);
	}
	
	public boolean cekCode(String code) {
		 
		boolean has = false;
		String selectQuery = " SELECT  * FROM " + DbSchema.TBL_PRODUCT  + 
						      " Where lower(" +DbSchema.COL_PRODUCT_CODE + ") = '"+code.toLowerCase()+"'";
		 
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.getCount() > 0)
			has = true;
			
		return has;
	}
	
	public boolean cekName(String name) {
		 
		boolean has = false;
		String selectQuery = " SELECT  * FROM " + DbSchema.TBL_PRODUCT  + 
						      " Where lower(" +DbSchema.COL_PRODUCT_NAME + ") = '"+name.toLowerCase()+"'";
		 
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.getCount() > 0)
			has = true;
			
		return has;
	}
	
	public boolean cekAvailable(String code) {
		 
		boolean has = false;
		String selectQuery = " SELECT  * FROM " + DbSchema.TBL_PRODUCT_ORDER_DETAIL  + 
						      " Where lower(" +DbSchema.COL_PRODUCT_ORDER_DETAIL_PRODUCT_CODE + ") = '"+code.toLowerCase()+"'";
		 
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.getCount() > 0)
			has = true;
			
		return has;
	}

}
