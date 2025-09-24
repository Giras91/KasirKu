package com.extropos.java.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.extropos.java.room.AppDatabase;

public class DatabaseManager {

    private int mOpenCounter;

    private static DatabaseManager instance;
    private static DatabaseHelper mDatabaseHelper;
    private static AppDatabase mRoomDatabase;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(DatabaseHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized void initializeRoomInstance(Context context) {
        if (mRoomDatabase == null) {
            mRoomDatabase = AppDatabase.getInstance(context);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public static synchronized AppDatabase getRoomInstance() {
        if (mRoomDatabase == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " Room database is not initialized, call initializeRoomInstance(..) method first.");
        }

        return mRoomDatabase;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if(mOpenCounter == 1) {
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if(mOpenCounter == 0) {
            mDatabase.close();

        }
    }
}