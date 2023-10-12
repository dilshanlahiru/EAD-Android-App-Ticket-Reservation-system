package com.example.ticket_reservation_system;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ead.db";
    private static final int DATABASE_VERSION = 1;

    // Define your table and columns
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NIC = "nic";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_USER_ID + " TEXT PRIMARY KEY," +
                COLUMN_NIC + " TEXT," +
                COLUMN_USER_NAME + " TEXT," +
                COLUMN_STATUS + " INTEGER" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here if needed
    }

    // Add a method to retrieve the user name from the database
    public String getUserName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = null;

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            userName = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return userName;
    }

    // Add a method to retrieve the NIC from the database
    public String getNIC() {
        SQLiteDatabase db = this.getReadableDatabase();
        String nic = null;

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NIC + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            nic = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return nic;
    }

    // Add a method to retrieve the user ID from the database
    public String getUserID() {
        SQLiteDatabase db = this.getReadableDatabase();
        String userID = null;

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_ID + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            userID = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return userID;
    }

}
