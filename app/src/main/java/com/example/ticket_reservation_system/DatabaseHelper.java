package com.example.ticket_reservation_system;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ead.db";
    private static final int DATABASE_VERSION = 1;

    // Define your table and columns
//    private static final String TABLE_NAME = "users";
    private static final String USER_TABLE_NAME = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NIC = "nic";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_STATUS = "status";

    // Reservation table
    private static final String RESERVATION_TABLE_NAME = "reservations";
    private static final String COLUMN_RESERVATION_ID = "reservation_id";
    private static final String COLUMN_TRAIN_ID = "train_id";


//    additional cahnge --- COLUMN_SCHEDULE_ID to
    private static final String COLUMN_SCHEDULE_RESERVATION_ID = "schedule_id";
    private static final String COLUMN_TRAIN_NAME = "train_name";
    private static final String COLUMN_START = "start";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_BOOKING_DATE_TIME = "booking_date_time";
    private static final String COLUMN_START_DATE_TIME = "start_date_time";
    private static final String COLUMN_DESTINATION_DATE_TIME = "destination_date_time";
    private static final String COLUMN_SEATS = "seats";
    private static final String COLUMN_RESERVATION_STATUS = "reservation_status";
    private static final String COLUMN_IS_PAST = "is_past";

    // Schedule table
    private static final String SCHEDULE_TABLE_NAME = "schedules";
    private static final String COLUMN_SCHEDULE_ID = "schedule_id";
    private static final String COLUMN_TRAIN_ID_SCHEDULE = "train_id";
    private static final String COLUMN_TRAIN_NAME_SCHEDULE = "train_name";
    private static final String COLUMN_START_SCHEDULE = "start";
    private static final String COLUMN_DESTINATION_SCHEDULE = "destination";
    private static final String COLUMN_START_DATE_TIME_SCHEDULE = "start_date_time";
    private static final String COLUMN_DESTINATION_DATE_TIME_SCHEDULE = "destination_date_time";
    private static final String COLUMN_SCHEDULE_STATUS = "schedule_status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
//        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
//                COLUMN_USER_ID + " TEXT PRIMARY KEY," +
//                COLUMN_NIC + " TEXT," +
//                COLUMN_USER_NAME + " TEXT," +
//                COLUMN_STATUS + " INTEGER" +
//                ")";
//        db.execSQL(createTableQuery);
        String createUserTableQuery = "CREATE TABLE " + USER_TABLE_NAME + " (" +
                COLUMN_USER_ID + " TEXT PRIMARY KEY," +
                COLUMN_NIC + " TEXT," +
                COLUMN_USER_NAME + " TEXT," +
                COLUMN_STATUS + " INTEGER" +
                ")";
        db.execSQL(createUserTableQuery);

        // Create the reservation table
        String createReservationTableQuery = "CREATE TABLE " + RESERVATION_TABLE_NAME + " (" +
                COLUMN_RESERVATION_ID + " TEXT PRIMARY KEY," +
                COLUMN_TRAIN_ID + " TEXT," +
                COLUMN_SCHEDULE_RESERVATION_ID + " TEXT," +
                COLUMN_TRAIN_NAME + " TEXT," +
                COLUMN_START + " TEXT," +
                COLUMN_DESTINATION + " TEXT," +
                COLUMN_BOOKING_DATE_TIME + " TEXT," +
                COLUMN_START_DATE_TIME + " TEXT," +
                COLUMN_DESTINATION_DATE_TIME + " TEXT," +
                COLUMN_SEATS + " INTEGER," +
                COLUMN_RESERVATION_STATUS + " INTEGER," +
                COLUMN_IS_PAST + " INTEGER" +
                ")";
        db.execSQL(createReservationTableQuery);

        String createScheduleTableQuery = "CREATE TABLE " + SCHEDULE_TABLE_NAME + " (" +
                COLUMN_SCHEDULE_ID + " TEXT PRIMARY KEY," +
                COLUMN_TRAIN_ID_SCHEDULE + " TEXT," +
                COLUMN_TRAIN_NAME_SCHEDULE + " TEXT," +
                COLUMN_START_SCHEDULE + " TEXT," +
                COLUMN_DESTINATION_SCHEDULE + " TEXT," +
                COLUMN_START_DATE_TIME_SCHEDULE + " TEXT," +
                COLUMN_DESTINATION_DATE_TIME_SCHEDULE + " TEXT," +
                COLUMN_SCHEDULE_STATUS + " INTEGER" +
                ")";
        db.execSQL(createScheduleTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here if needed
    }

    // Add a method to retrieve the user name from the database
    public String getUserName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = null;

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_NAME + " FROM " + USER_TABLE_NAME, null);
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

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NIC + " FROM " + USER_TABLE_NAME, null);
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

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USER_ID + " FROM " + USER_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            userID = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return userID;
    }

    public void insertReservations(List<Reservation> reservations) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            for (Reservation reservation : reservations) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_RESERVATION_ID, reservation.getReservationId());
                values.put(COLUMN_TRAIN_ID, reservation.getTrainId());
                values.put(COLUMN_SCHEDULE_RESERVATION_ID, reservation.getScheduleId());
                values.put(COLUMN_TRAIN_NAME, reservation.getTrainName());
                values.put(COLUMN_START, reservation.getStart());
                values.put(COLUMN_DESTINATION, reservation.getDestination());
                values.put(COLUMN_BOOKING_DATE_TIME, reservation.getBookingDateTime());
                values.put(COLUMN_START_DATE_TIME, reservation.getStartDateTime());
                values.put(COLUMN_DESTINATION_DATE_TIME, reservation.getDestinationDateTime());
                values.put(COLUMN_SEATS, reservation.getSeats());
                values.put(COLUMN_RESERVATION_STATUS, reservation.getStatus());
                values.put(COLUMN_IS_PAST, reservation.isPast() ? 1 : 0);

                db.insert(RESERVATION_TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + RESERVATION_TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String reservationId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_ID));
                String trainId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRAIN_ID));
                String scheduleId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULE_RESERVATION_ID));
                String trainName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRAIN_NAME));
                String start = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION));
                String bookingDateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_DATE_TIME));
                String startDateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE_TIME));
                String destinationDateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION_DATE_TIME));
                int seats = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SEATS));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_STATUS));
                boolean isPast = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_PAST)) != 0;

                Reservation reservation = new Reservation(reservationId, trainId, scheduleId, trainName, start, destination,
                        bookingDateTime, startDateTime, destinationDateTime, seats, status, isPast);
                reservations.add(reservation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return reservations;
    }

    public void insertSchedules(List<Schedule> schedules) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            for (Schedule schedule : schedules) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_SCHEDULE_ID, schedule.getScheduleId());
                values.put(COLUMN_TRAIN_ID, schedule.getTrainId());
                values.put(COLUMN_TRAIN_NAME, schedule.getTrainName());
                values.put(COLUMN_START, schedule.getStart());
                values.put(COLUMN_DESTINATION, schedule.getDestination());
                values.put(COLUMN_START_DATE_TIME, schedule.getStartDateTime());
                values.put(COLUMN_DESTINATION_DATE_TIME, schedule.getDestinationDateTime());
                values.put(COLUMN_SCHEDULE_STATUS, schedule.getStatus());

                db.insert(SCHEDULE_TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SCHEDULE_TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String scheduleId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULE_ID));
                String trainId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRAIN_ID));
                String trainName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRAIN_NAME));
                String start = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION));
                String startDateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE_TIME));
                String destinationDateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION_DATE_TIME));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULE_STATUS));

                Schedule schedule = new Schedule(scheduleId, trainId, trainName, start, destination, startDateTime, destinationDateTime, status);
                schedules.add(schedule);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return schedules;
    }






}
