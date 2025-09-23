package com.extropos.java.sqlite.ds;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.extropos.java.entity.TableService;

import java.util.ArrayList;
import java.util.List;

/**
 * Data source for TableService operations
 */
public class TableServiceDataSource {
    private static final String TAG = "TableServiceDataSource";

    // Database table and column names
    public static final String TABLE_TABLE_SERVICE = "table_service";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TABLE_ID = "table_id";
    public static final String COLUMN_TABLE_NAME = "table_name";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_WAITER_ID = "waiter_id";
    public static final String COLUMN_WAITER_NAME = "waiter_name";
    public static final String COLUMN_CUSTOMER_NAME = "customer_name";
    public static final String COLUMN_CUSTOMER_PHONE = "customer_phone";
    public static final String COLUMN_SERVICE_START_TIME = "service_start_time";
    public static final String COLUMN_SERVICE_END_TIME = "service_end_time";
    public static final String COLUMN_RESERVATION_TIME = "reservation_time";
    public static final String COLUMN_SPECIAL_REQUESTS = "special_requests";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_ESTIMATED_BILL = "estimated_bill";
    public static final String COLUMN_GUEST_COUNT = "guest_count";

    private SQLiteDatabase database;

    public TableServiceDataSource(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Insert a new table service record
     */
    public long insert(TableService tableService) {
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TABLE_ID, tableService.getTableId());
            values.put(COLUMN_TABLE_NAME, tableService.getTableName());
            values.put(COLUMN_STATUS, tableService.getStatus());
            values.put(COLUMN_WAITER_ID, tableService.getWaiterId());
            values.put(COLUMN_WAITER_NAME, tableService.getWaiterName());
            values.put(COLUMN_CUSTOMER_NAME, tableService.getCustomerName());
            values.put(COLUMN_CUSTOMER_PHONE, tableService.getCustomerPhone());
            values.put(COLUMN_SERVICE_START_TIME, tableService.getServiceStartTime());
            values.put(COLUMN_SERVICE_END_TIME, tableService.getServiceEndTime());
            values.put(COLUMN_RESERVATION_TIME, tableService.getReservationTime());
            values.put(COLUMN_SPECIAL_REQUESTS, tableService.getSpecialRequests());
            values.put(COLUMN_NOTES, tableService.getNotes());
            values.put(COLUMN_ESTIMATED_BILL, tableService.getEstimatedBill());
            values.put(COLUMN_GUEST_COUNT, tableService.getGuestCount());

            long id = database.insert(TABLE_TABLE_SERVICE, null, values);
            tableService.setId(id);
            return id;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting table service", e);
            return -1;
        }
    }

    /**
     * Update an existing table service record
     */
    public int update(TableService tableService) {
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TABLE_ID, tableService.getTableId());
            values.put(COLUMN_TABLE_NAME, tableService.getTableName());
            values.put(COLUMN_STATUS, tableService.getStatus());
            values.put(COLUMN_WAITER_ID, tableService.getWaiterId());
            values.put(COLUMN_WAITER_NAME, tableService.getWaiterName());
            values.put(COLUMN_CUSTOMER_NAME, tableService.getCustomerName());
            values.put(COLUMN_CUSTOMER_PHONE, tableService.getCustomerPhone());
            values.put(COLUMN_SERVICE_START_TIME, tableService.getServiceStartTime());
            values.put(COLUMN_SERVICE_END_TIME, tableService.getServiceEndTime());
            values.put(COLUMN_RESERVATION_TIME, tableService.getReservationTime());
            values.put(COLUMN_SPECIAL_REQUESTS, tableService.getSpecialRequests());
            values.put(COLUMN_NOTES, tableService.getNotes());
            values.put(COLUMN_ESTIMATED_BILL, tableService.getEstimatedBill());
            values.put(COLUMN_GUEST_COUNT, tableService.getGuestCount());

            return database.update(TABLE_TABLE_SERVICE, values,
                    COLUMN_ID + " = ?", new String[]{String.valueOf(tableService.getId())});
        } catch (Exception e) {
            Log.e(TAG, "Error updating table service", e);
            return 0;
        }
    }

    /**
     * Delete a table service record
     */
    public int delete(long id) {
        try {
            return database.delete(TABLE_TABLE_SERVICE,
                    COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e(TAG, "Error deleting table service", e);
            return 0;
        }
    }

    /**
     * Get table service by ID
     */
    public TableService getById(long id) {
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_TABLE_SERVICE, null,
                    COLUMN_ID + " = ?", new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                return cursorToTableService(cursor);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting table service by ID", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * Get table service by table ID
     */
    public TableService getByTableId(int tableId) {
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_TABLE_SERVICE, null,
                    COLUMN_TABLE_ID + " = ?", new String[]{String.valueOf(tableId)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                return cursorToTableService(cursor);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting table service by table ID", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * Get table service by table name
     */
    public TableService getByTableName(String tableName) {
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_TABLE_SERVICE, null,
                    COLUMN_TABLE_NAME + " = ?", new String[]{tableName},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                return cursorToTableService(cursor);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting table service by table name", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * Get all table services
     */
    public List<TableService> getAll() {
        List<TableService> tableServices = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_TABLE_SERVICE, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    tableServices.add(cursorToTableService(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting all table services", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return tableServices;
    }

    /**
     * Get table services by status
     */
    public List<TableService> getByStatus(String status) {
        List<TableService> tableServices = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_TABLE_SERVICE, null,
                    COLUMN_STATUS + " = ?", new String[]{status},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    tableServices.add(cursorToTableService(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting table services by status", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return tableServices;
    }

    /**
     * Get table services by waiter
     */
    public List<TableService> getByWaiter(String waiterId) {
        List<TableService> tableServices = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_TABLE_SERVICE, null,
                    COLUMN_WAITER_ID + " = ?", new String[]{waiterId},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    tableServices.add(cursorToTableService(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting table services by waiter", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return tableServices;
    }

    /**
     * Get active table services (currently being served)
     */
    public List<TableService> getActiveServices() {
        List<TableService> tableServices = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_TABLE_SERVICE, null,
                    COLUMN_SERVICE_START_TIME + " > 0 AND " + COLUMN_SERVICE_END_TIME + " = 0",
                    null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    tableServices.add(cursorToTableService(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting active table services", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return tableServices;
    }

    /**
     * Start service for a table
     */
    public boolean startService(int tableId, String waiterId, String waiterName, int guestCount) {
        try {
            TableService tableService = getByTableId(tableId);
            if (tableService == null) {
                tableService = new TableService(tableId, "T" + tableId);
            }

            tableService.setStatus(TableService.STATUS_OCCUPIED);
            tableService.setWaiterId(waiterId);
            tableService.setWaiterName(waiterName);
            tableService.setServiceStartTime(System.currentTimeMillis());
            tableService.setGuestCount(guestCount);

            if (tableService.getId() > 0) {
                return update(tableService) > 0;
            } else {
                return insert(tableService) > 0;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error starting table service", e);
            return false;
        }
    }

    /**
     * End service for a table
     */
    public boolean endService(int tableId) {
        try {
            TableService tableService = getByTableId(tableId);
            if (tableService != null && tableService.hasActiveService()) {
                tableService.setServiceEndTime(System.currentTimeMillis());
                tableService.setStatus(TableService.STATUS_CLEANING);
                return update(tableService) > 0;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error ending table service", e);
            return false;
        }
    }

    /**
     * Mark table as available after cleaning
     */
    public boolean markTableAvailable(int tableId) {
        try {
            TableService tableService = getByTableId(tableId);
            if (tableService != null) {
                tableService.setStatus(TableService.STATUS_AVAILABLE);
                tableService.setWaiterId(null);
                tableService.setWaiterName(null);
                tableService.setCustomerName(null);
                tableService.setCustomerPhone(null);
                tableService.setServiceStartTime(0);
                tableService.setServiceEndTime(0);
                tableService.setReservationTime(0);
                tableService.setSpecialRequests(null);
                tableService.setNotes(null);
                tableService.setEstimatedBill(0.0);
                tableService.setGuestCount(0);
                return update(tableService) > 0;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error marking table as available", e);
            return false;
        }
    }

    /**
     * Reserve a table
     */
    public boolean reserveTable(int tableId, String customerName, String customerPhone,
                               long reservationTime, String specialRequests, int guestCount) {
        try {
            TableService tableService = getByTableId(tableId);
            if (tableService == null) {
                tableService = new TableService(tableId, "T" + tableId);
            }

            tableService.setStatus(TableService.STATUS_RESERVED);
            tableService.setCustomerName(customerName);
            tableService.setCustomerPhone(customerPhone);
            tableService.setReservationTime(reservationTime);
            tableService.setSpecialRequests(specialRequests);
            tableService.setGuestCount(guestCount);

            if (tableService.getId() > 0) {
                return update(tableService) > 0;
            } else {
                return insert(tableService) > 0;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error reserving table", e);
            return false;
        }
    }

    /**
     * Convert cursor to TableService object
     */
    private TableService cursorToTableService(Cursor cursor) {
        TableService tableService = new TableService();

        tableService.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
        tableService.setTableId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TABLE_ID)));
        tableService.setTableName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TABLE_NAME)));
        tableService.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)));
        tableService.setWaiterId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WAITER_ID)));
        tableService.setWaiterName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WAITER_NAME)));
        tableService.setCustomerName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CUSTOMER_NAME)));
        tableService.setCustomerPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CUSTOMER_PHONE)));
        tableService.setServiceStartTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_START_TIME)));
        tableService.setServiceEndTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_END_TIME)));
        tableService.setReservationTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_TIME)));
        tableService.setSpecialRequests(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SPECIAL_REQUESTS)));
        tableService.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)));
        tableService.setEstimatedBill(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ESTIMATED_BILL)));
        tableService.setGuestCount(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GUEST_COUNT)));

        return tableService;
    }
}