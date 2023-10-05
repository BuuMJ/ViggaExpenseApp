package com.example.viggaexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelpers extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "list_trip";
    private static final String ID_COLUMN_NAME = "trip_id";
    private static final String TRIPNAME_COLUMN_NAME = "name";
    private static final String TRIPBUDGET_COLUMN_NAME = "budget";
    private static final String TRIPDESTI_COLUMN_NAME = "desti";
    private static final String STARTDATE_COLUMN_NAME = "start_date";
    private static final String ENDDATE_COLUMN_NAME = "end_date";
    private static final String TRIPDESC_COLUMN_NAME = "desc";
    private SQLiteDatabase database;
    private static final String DATABASE_CREATE_QUERY = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            DATABASE_NAME, ID_COLUMN_NAME, TRIPNAME_COLUMN_NAME, TRIPBUDGET_COLUMN_NAME, TRIPDESTI_COLUMN_NAME, STARTDATE_COLUMN_NAME, ENDDATE_COLUMN_NAME, TRIPDESC_COLUMN_NAME
    );
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_ID_COLUMN_NAME = "user_id";
    private static final String USER_NAME_COLUMN_NAME = "username";
    private static final String USER_Password_COLUMN_NAME = "password";
    private static final String USER_TRIP_ID_COLUMN_NAME = "trip_id";

    private static final String USER_TABLE_CREATE_QUERY = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s INTEGER, " +
                    "FOREIGN KEY(%s) REFERENCES %s(%s))",
            USER_TABLE_NAME, USER_ID_COLUMN_NAME, USER_NAME_COLUMN_NAME, USER_Password_COLUMN_NAME,
            USER_TRIP_ID_COLUMN_NAME, USER_TRIP_ID_COLUMN_NAME, "list_trip", ID_COLUMN_NAME
    );
    public DatabaseHelpers(Context context){
        super(context, DATABASE_NAME, null,1);
        database = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_QUERY);
        db.execSQL(USER_TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        Log.w(this.getClass().getName(),DATABASE_NAME + "database upgrade to version" + newVersion + " - old data lost");
        onCreate(db);
    }
    public long inserDetails(String name, String budget, String desti, String start_date, String end_date, String desc){
        ContentValues rowValues = new ContentValues();
        rowValues.put(TRIPNAME_COLUMN_NAME , name);
        rowValues.put(TRIPBUDGET_COLUMN_NAME , budget);
        rowValues.put(TRIPDESTI_COLUMN_NAME , desti);
        rowValues.put(STARTDATE_COLUMN_NAME , start_date);
        rowValues.put(ENDDATE_COLUMN_NAME , end_date);
        rowValues.put(TRIPDESC_COLUMN_NAME , desc);
        return database.insertOrThrow(DATABASE_NAME, null,rowValues);
    }
    public long insertUser(String username, String password, long tripId) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(USER_NAME_COLUMN_NAME, username);
        rowValues.put(USER_Password_COLUMN_NAME, password);
        rowValues.put(USER_TRIP_ID_COLUMN_NAME, tripId);
        return database.insertOrThrow(USER_TABLE_NAME, null, rowValues);
    }
    public List<dataTrip> getDetails() {
        List<dataTrip> tripList = new ArrayList<>();
        Cursor results = database.query("list_trip",
                new String[]{"trip_id", "name", "budget", "desti", "start_date", "end_date", "desc"}, null, null, null, null, "trip_id");

        results.moveToFirst();
        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(1);
            String budget = results.getString(2);
            String desti = results.getString(3);
            String start_date = results.getString(4);
            String end_date = results.getString(5);
            String desc = results.getString(6);

            dataTrip trip = new dataTrip(id, name, budget, desti, start_date, end_date, desc);
            tripList.add(trip);
            results.moveToNext();
        }
        return tripList;
    }

}
