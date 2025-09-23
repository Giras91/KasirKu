package com.extropos.java.sqlite.ds;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.extropos.java.entity.Order;
import com.extropos.java.entity.OrderDetails;
import com.extropos.java.sqlite.DbSchema;
import com.extropos.java.utils.Shared;

public class OrderDataSource {
	private SQLiteDatabase db;
	public OrderDataSource(SQLiteDatabase db)
	{
		this.db = db;
	}
	
	public long truncate()
	{
		return db.delete(DbSchema.TBL_ORDER,null,null);
	}
	
	public Order get(String code) {
		 
		Order item = new Order();
		 
		String selectQuery = 	" SELECT  o.*,u."+DbSchema.COL_USER_NAME+"  FROM " + DbSchema.TBL_ORDER   + " o " +
								" LEFT JOIN " +  DbSchema.TBL_USER +  " u ON u." +  DbSchema.COL_USER_CODE + " = o." + DbSchema.COL_ORDER_USER_ID +
								" Where " +DbSchema.COL_ORDER_CODE + " = '"+code+"'";
		
		Cursor c = db.rawQuery(selectQuery, null);
	
		if (c.moveToFirst()) {
			do {
				int colIndex;
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_CODE);
				if (colIndex >= 0) item.setOrderID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_DESCRIPTION);
				if (colIndex >= 0) item.setDescription(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_AMOUNT);
				if (colIndex >= 0) item.setAmount(c.getDouble(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_DESCRIPTION);
				if (colIndex >= 0) item.setDiscount(c.getDouble(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_BRANCH_ID);
				if (colIndex >= 0) item.setBranchID(c.getString(colIndex));

				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_STATUS);
				if (colIndex >= 0) item.setStatus(c.getString(colIndex));

				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_TABLE_ID);
				if (colIndex >= 0) item.setTableID(c.getString(colIndex));

				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_TABLE_NAME);
				if (colIndex >= 0) item.setTableName(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_USER_ID);
				if (colIndex >= 0) item.setUserID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_USER_NAME);
				if (colIndex >= 0) item.setUserName(c.getString(colIndex));
				
				try {  
					colIndex = c.getColumnIndex(DbSchema.COL_ORDER_ORDERED_ON);
					if (colIndex >= 0) item.setCreatedOn(Shared.dateformat.parse(c.getString(colIndex)));
					
					colIndex = c.getColumnIndex(DbSchema.COL_ORDER_UPDATED_ON);
					if (colIndex >= 0) item.setUpdatedOn(Shared.dateformat.parse(c.getString(colIndex)));
					
					colIndex = c.getColumnIndex(DbSchema.COL_ORDER_SYCN_ON);
					if (colIndex >= 0) item.setSycnOn(Shared.dateformat.parse(c.getString(colIndex)));
				} catch (Exception e) {  
				}
				
				
				String selectQueryDetail =  " SELECT  o.*,p."+DbSchema.COL_PRODUCT_NAME+",c."+DbSchema.COL_PRODUCT_PRODUCT_CATEGORY_NAME+"  FROM " + DbSchema.TBL_PRODUCT_ORDER_DETAIL  + " o " +
											" LEFT JOIN " +  DbSchema.TBL_PRODUCT +  " p ON p." +  DbSchema.COL_PRODUCT_CODE + " = o." + DbSchema.COL_PRODUCT_ORDER_DETAIL_PRODUCT_CODE +
											" LEFT JOIN " +  DbSchema.TBL_PRODUCT_CATEGORY +  " c ON c." +  DbSchema.COL_PRODUCT_CATEGORY_CODE + " = p." + DbSchema.COL_PRODUCT_CATEGORY_CODE +
											" WHERE " +DbSchema.COL_PRODUCT_ORDER_DETAIL_ORDER_CODE + " = '"+code+"'";
				Cursor cDetail = db.rawQuery(selectQueryDetail, null);
				
				ArrayList<OrderDetails> details = new ArrayList<OrderDetails>();
				if (cDetail.moveToFirst()) {
					do {
						
						OrderDetails  order = new OrderDetails();
						int colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_CODE);
						if (colIndexDetail >= 0) order.setDetailID(cDetail.getString(colIndexDetail));
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_NAME);
						if (colIndexDetail >= 0) order.setName(cDetail.getString(colIndexDetail));
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_ORDER_CODE);
						if (colIndexDetail >= 0) order.setOrderID(cDetail.getString(colIndexDetail));
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_PRODUCT_CODE);
						if (colIndexDetail >= 0) order.setProductID(cDetail.getString(colIndexDetail));
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_PRODUCT_CATEGORY_NAME);
						if (colIndexDetail >= 0) order.setCategoryName(cDetail.getString(colIndexDetail));
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_QTY);
						if (colIndexDetail >= 0) order.setQty(cDetail.getInt(colIndexDetail));
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_DISCOUNT);
						if (colIndexDetail >= 0) order.setDiscount(cDetail.getDouble(colIndexDetail));
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_PRICE);
						if (colIndexDetail >= 0) order.setPrice(cDetail.getDouble(colIndexDetail));
						
						details.add(order);
					} while (cDetail.moveToNext());
				}
				
				 item.setOrderDetails(details);
			
			} while (c.moveToNext());
		}
		return item;
	}
	
	
	public ArrayList<Order> getAll() {
		return getAll(null,null,null,null);
	}
	
	public ArrayList<Order> getAll(ArrayList<HashMap<String, String>> filter,String orderby,String limit,String offset) {
		 
		ArrayList<Order> items = new ArrayList<Order>();
		
		String selectQuery = " SELECT  *  FROM " + DbSchema.TBL_ORDER;
	
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				Order item = new Order();
				item.setOrderID(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_CODE)));
				item.setDescription(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_DESCRIPTION)));
				item.setAmount(c.getDouble(c.getColumnIndex(DbSchema.COL_ORDER_AMOUNT)));
				item.setDiscount(c.getDouble(c.getColumnIndex(DbSchema.COL_ORDER_DESCRIPTION)));
				item.setBranchID(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_BRANCH_ID)));
				if (c.getColumnIndex(DbSchema.COL_ORDER_STATUS) >= 0)
					item.setStatus(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_STATUS)));
				if (c.getColumnIndex(DbSchema.COL_ORDER_TABLE_ID) >= 0)
					item.setTableID(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_TABLE_ID)));
				if (c.getColumnIndex(DbSchema.COL_ORDER_TABLE_NAME) >= 0)
					item.setTableName(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_TABLE_NAME)));
				item.setUserID(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_USER_ID)));
				item.setUserName(c.getString(c.getColumnIndex(DbSchema.COL_USER_NAME)));
				
				try {  
				    item.setCreatedOn( Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_ORDERED_ON))));
				    item.setUpdatedOn( Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_UPDATED_ON))));
				    item.setSycnOn( Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_ORDER_SYCN_ON))));
				} catch (Exception e) {  
				}
				
				
				String selectQueryDetail =  " SELECT  o.*,p."+DbSchema.COL_PRODUCT_NAME+",c."+DbSchema.COL_PRODUCT_PRODUCT_CATEGORY_NAME+"  FROM " + DbSchema.TBL_PRODUCT_ORDER_DETAIL  + " o " +
											" LEFT JOIN " +  DbSchema.TBL_PRODUCT +  " p ON p." +  DbSchema.COL_PRODUCT_CODE + " = o." + DbSchema.COL_PRODUCT_ORDER_DETAIL_PRODUCT_CODE +
											" LEFT JOIN " +  DbSchema.TBL_PRODUCT_CATEGORY +  " c ON c." +  DbSchema.COL_PRODUCT_CATEGORY_CODE + " = p." + DbSchema.COL_PRODUCT_CATEGORY_CODE +
											" WHERE " +DbSchema.COL_PRODUCT_ORDER_DETAIL_ORDER_CODE + " = '"+item.getOrderID()+"'";
				Cursor cDetail = db.rawQuery(selectQueryDetail, null);
				
				ArrayList<OrderDetails> details = new ArrayList<OrderDetails>();
				if (cDetail.moveToFirst()) {
					do {
						
						OrderDetails  order = new OrderDetails();
						int colIndexDetail;
						
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_CODE);
						if (colIndexDetail >= 0) order.setDetailID(cDetail.getString(colIndexDetail));
						
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_NAME);
						if (colIndexDetail >= 0) order.setName(cDetail.getString(colIndexDetail));
						
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_ORDER_CODE);
						if (colIndexDetail >= 0) order.setOrderID(cDetail.getString(colIndexDetail));
						
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_PRODUCT_CODE);
						if (colIndexDetail >= 0) order.setProductID(cDetail.getString(colIndexDetail));
						
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_PRODUCT_CATEGORY_NAME);
						if (colIndexDetail >= 0) order.setCategoryName(cDetail.getString(colIndexDetail));
						
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_QTY);
						if (colIndexDetail >= 0) order.setQty(cDetail.getInt(colIndexDetail));
						
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_DISCOUNT);
						if (colIndexDetail >= 0) order.setDiscount(cDetail.getDouble(colIndexDetail));
						
						colIndexDetail = cDetail.getColumnIndex(DbSchema.COL_PRODUCT_ORDER_DETAIL_PRICE);
						if (colIndexDetail >= 0) order.setPrice(cDetail.getDouble(colIndexDetail));
						
						details.add(order);
					} while (cDetail.moveToNext());
				}
				
				 item.setOrderDetails(details);
				
				items.add(item);
			} while (c.moveToNext());
		}
	
		return items;
	}
	
	public long insert(Order item)
	{
		ContentValues values = new ContentValues();
		values.put(DbSchema.COL_ORDER_CODE, item.getOrderID());
		values.put(DbSchema.COL_ORDER_DESCRIPTION, item.getDescription());
		values.put(DbSchema.COL_ORDER_TAX, item.getTax());
		values.put(DbSchema.COL_ORDER_AMOUNT, item.getAmount());
		values.put(DbSchema.COL_ORDER_DISCOUNT,item.getDiscount());
		values.put(DbSchema.COL_ORDER_BRANCH_ID, item.getBranchID());
		if (item.getStatus() != null)
			values.put(DbSchema.COL_ORDER_STATUS, item.getStatus());
        values.put(DbSchema.COL_ORDER_TABLE_ID, item.getTableID());
        values.put(DbSchema.COL_ORDER_TABLE_NAME, item.getTableName());
		values.put(DbSchema.COL_ORDER_USER_ID, item.getUserID());
		values.put(DbSchema.COL_ORDER_ORDERED_ON,  Shared.dateformat.format(item.getCreatedOn()));
		values.put(DbSchema.COL_ORDER_UPDATED_ON,  Shared.dateformat.format(item.getUpdatedOn()));
	//	values.put(DbSchema.COL_ORDER_SYCN_ON, Shared.dateformat.format(item.getSycnOn()));
		db.insert(DbSchema.TBL_ORDER, null, values);
		
		for (OrderDetails detail : item.getOrderDetails()) {
			ContentValues valuesDetails = new ContentValues();
			valuesDetails.put(DbSchema.COL_PRODUCT_ORDER_DETAIL_CODE, detail.getDetailID());
			valuesDetails.put(DbSchema.COL_PRODUCT_ORDER_DETAIL_ORDER_CODE, detail.getOrderID());
			valuesDetails.put(DbSchema.COL_PRODUCT_ORDER_DETAIL_PRODUCT_CODE, detail.getProductID());
			valuesDetails.put(DbSchema.COL_PRODUCT_ORDER_DETAIL_PRICE, detail.getPrice());
			valuesDetails.put(DbSchema.COL_PRODUCT_ORDER_DETAIL_QTY,detail.getQty());
			valuesDetails.put(DbSchema.COL_PRODUCT_ORDER_DETAIL_DISCOUNT, detail.getDiscount());
			db.insert(DbSchema.TBL_PRODUCT_ORDER_DETAIL, null, valuesDetails);
		}
		
		return 1;
	}

	public int update(Order item) {
		ContentValues values = new ContentValues();
		values.put(DbSchema.COL_ORDER_DESCRIPTION, item.getDescription());
		values.put(DbSchema.COL_ORDER_TAX, item.getTax());
		values.put(DbSchema.COL_ORDER_AMOUNT, item.getAmount());
		values.put(DbSchema.COL_ORDER_DISCOUNT, item.getDiscount());
		values.put(DbSchema.COL_ORDER_BRANCH_ID, item.getBranchID());
		if (item.getStatus() != null)
			values.put(DbSchema.COL_ORDER_STATUS, item.getStatus());
		values.put(DbSchema.COL_ORDER_TABLE_ID, item.getTableID());
		values.put(DbSchema.COL_ORDER_TABLE_NAME, item.getTableName());
		values.put(DbSchema.COL_ORDER_USER_ID, item.getUserID());
		values.put(DbSchema.COL_ORDER_ORDERED_ON,  Shared.dateformat.format(item.getCreatedOn()));
		values.put(DbSchema.COL_ORDER_UPDATED_ON,  Shared.dateformat.format(item.getUpdatedOn()));
		return db.update(DbSchema.TBL_ORDER, values, DbSchema.COL_ORDER_CODE + " = ?", new String[] { item.getOrderID() });
	}

	public int updateTableAssignment(String orderId, String tableId, String tableName) {
		ContentValues values = new ContentValues();
		values.put(DbSchema.COL_ORDER_TABLE_ID, tableId);
		values.put(DbSchema.COL_ORDER_TABLE_NAME, tableName);
		return db.update(DbSchema.TBL_ORDER, values, DbSchema.COL_ORDER_CODE + " = ?", new String[] { orderId });
	}
	
	
	public int delete(String code)
	{
		db.delete(DbSchema.TBL_PRODUCT_ORDER_DETAIL, DbSchema.COL_PRODUCT_ORDER_DETAIL_ORDER_CODE + "= '" + code + "'", null);
		db.delete(DbSchema.TBL_ORDER, DbSchema.COL_ORDER_CODE + "= '" + code + "'", null);
		return 1;
	}
	
	public boolean cekCode(String code) {
		 
		boolean has = false;
		String selectQuery = " SELECT  * FROM " + DbSchema.TBL_ORDER  + 
						      " Where lower(" +DbSchema.COL_ORDER_CODE + ") = '"+code.toLowerCase()+"'";
		 
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.getCount() > 0)
			has = true;
			
		return has;
	}

	public ArrayList<Order> getOrdersByTable(String tableName) {
		ArrayList<Order> items = new ArrayList<Order>();
		
		String selectQuery = " SELECT  o.*,u."+DbSchema.COL_USER_NAME+"  FROM " + DbSchema.TBL_ORDER   + " o " +
							" LEFT JOIN " +  DbSchema.TBL_USER +  " u ON u." +  DbSchema.COL_USER_CODE + " = o." + DbSchema.COL_ORDER_USER_ID +
							" WHERE " + DbSchema.COL_ORDER_TABLE_NAME + " = ? AND (" + DbSchema.COL_ORDER_STATUS + " IS NULL OR " + DbSchema.COL_ORDER_STATUS + " != 'paid')";
		
		Cursor c = db.rawQuery(selectQuery, new String[]{tableName});
		if (c.moveToFirst()) {
			do {
				Order item = new Order();
				int colIndex;
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_CODE);
				if (colIndex >= 0) item.setOrderID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_DESCRIPTION);
				if (colIndex >= 0) item.setDescription(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_AMOUNT);
				if (colIndex >= 0) item.setAmount(c.getDouble(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_DISCOUNT);
				if (colIndex >= 0) item.setDiscount(c.getDouble(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_BRANCH_ID);
				if (colIndex >= 0) item.setBranchID(c.getString(colIndex));

				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_STATUS);
				if (colIndex >= 0) item.setStatus(c.getString(colIndex));

				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_TABLE_ID);
				if (colIndex >= 0) item.setTableID(c.getString(colIndex));

				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_TABLE_NAME);
				if (colIndex >= 0) item.setTableName(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_ORDER_USER_ID);
				if (colIndex >= 0) item.setUserID(c.getString(colIndex));
				
				colIndex = c.getColumnIndex(DbSchema.COL_USER_NAME);
				if (colIndex >= 0) item.setUserName(c.getString(colIndex));
				
				try {  
					colIndex = c.getColumnIndex(DbSchema.COL_ORDER_ORDERED_ON);
					if (colIndex >= 0) item.setCreatedOn(Shared.dateformat.parse(c.getString(colIndex)));
					
					colIndex = c.getColumnIndex(DbSchema.COL_ORDER_UPDATED_ON);
					if (colIndex >= 0) item.setUpdatedOn(Shared.dateformat.parse(c.getString(colIndex)));
					
					colIndex = c.getColumnIndex(DbSchema.COL_ORDER_SYCN_ON);
					if (colIndex >= 0) item.setSycnOn(Shared.dateformat.parse(c.getString(colIndex)));
				} catch (Exception e) {  
				}
				
				items.add(item);
			} while (c.moveToNext());
		}
		c.close();
		
		return items;
	}

	public double getTableTotal(String tableName) {
		double total = 0.0;
		String selectQuery = " SELECT SUM(" + DbSchema.COL_ORDER_AMOUNT + ") as total FROM " + DbSchema.TBL_ORDER + 
							" WHERE " + DbSchema.COL_ORDER_TABLE_NAME + " = ? AND (" + DbSchema.COL_ORDER_STATUS + " IS NULL OR " + DbSchema.COL_ORDER_STATUS + " != 'paid')";
		
		Cursor c = db.rawQuery(selectQuery, new String[]{tableName});
		if (c.moveToFirst()) {
			total = c.getDouble(c.getColumnIndex("total"));
		}
		c.close();
		
		return total;
	}

}
