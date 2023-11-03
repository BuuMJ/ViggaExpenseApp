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
    private static final String TRIPLEVEL_COLUMN_NAME = "level";
    private static final String TRIPDESTI_COLUMN_NAME = "desti";
    private static final String STARTDATE_COLUMN_NAME = "start_date";
    private static final String ENDDATE_COLUMN_NAME = "end_date";
    private static final String TRIPDESC_COLUMN_NAME = "desc";
    private static final String PARKING_COLUMN_NAME = "parking";
    private static final String TRIPLENGTH_COLUMN_NAME = "length";
    private static final String TRIPBUDGET_COLUMN_NAME = "budget";
    private SQLiteDatabase database;
    private static final String DATABASE_CREATE_QUERY = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT)",
            DATABASE_NAME, ID_COLUMN_NAME, TRIPNAME_COLUMN_NAME, TRIPLEVEL_COLUMN_NAME, TRIPDESTI_COLUMN_NAME, STARTDATE_COLUMN_NAME, ENDDATE_COLUMN_NAME, TRIPDESC_COLUMN_NAME, PARKING_COLUMN_NAME, TRIPLENGTH_COLUMN_NAME, TRIPBUDGET_COLUMN_NAME
    );
    private static final String OBSERVATIONS_TABLE_NAME = "observations";
    private static final String OBSERVATION_ID_COLUMN_NAME = "observation_id";
    private static final String OBSERVATION_TRIP_ID_COLUMN_NAME = "trip_id";
    private static final String OBSERVATION_TEXT_COLUMN_NAME = "observation_text";

    private static final String OBSERVATIONS_TABLE_CREATE_QUERY = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER, " +
                    "%s TEXT, " +
                    "FOREIGN KEY(%s) REFERENCES %s(%s))",
            OBSERVATIONS_TABLE_NAME, OBSERVATION_ID_COLUMN_NAME, OBSERVATION_TRIP_ID_COLUMN_NAME,
            OBSERVATION_TEXT_COLUMN_NAME, OBSERVATION_TRIP_ID_COLUMN_NAME, DATABASE_NAME,
            ID_COLUMN_NAME
    );
    public DatabaseHelpers(Context context){
        super(context, DATABASE_NAME, null,1);
        database = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_QUERY);
        db.execSQL(OBSERVATIONS_TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        Log.w(this.getClass().getName(),DATABASE_NAME + "database upgrade to version" + newVersion + " - old data lost");
        onCreate(db);
    }
    public long inserDetails(String name, String level, String desti, String start_date, String end_date, String desc, String parking, String length, String budget){
        ContentValues rowValues = new ContentValues();
        rowValues.put(TRIPNAME_COLUMN_NAME , name);
        rowValues.put(TRIPLEVEL_COLUMN_NAME , level);
        rowValues.put(TRIPDESTI_COLUMN_NAME , desti);
        rowValues.put(STARTDATE_COLUMN_NAME , start_date);
        rowValues.put(ENDDATE_COLUMN_NAME , end_date);
        rowValues.put(TRIPDESC_COLUMN_NAME , desc);
        rowValues.put(PARKING_COLUMN_NAME, parking);
        rowValues.put(TRIPLENGTH_COLUMN_NAME, length);
        rowValues.put(TRIPBUDGET_COLUMN_NAME, budget);
        return database.insertOrThrow(DATABASE_NAME, null,rowValues);
    }
    public long insertObservation(int tripId, String observationText) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(OBSERVATION_TRIP_ID_COLUMN_NAME, tripId);
        rowValues.put(OBSERVATION_TEXT_COLUMN_NAME, observationText);
        return database.insertOrThrow(OBSERVATIONS_TABLE_NAME, null, rowValues);
    }
    public List<dataTrip> getDetails() {
        List<dataTrip> tripList = new ArrayList<>();
        Cursor results = database.query("list_trip",
                new String[]{"trip_id", "name", "level", "desti", "start_date", "end_date", "desc", "parking", "length", "budget"}, null, null, null, null, "trip_id");

        results.moveToFirst();
        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(1);
            String level = results.getString(2);
            String desti = results.getString(3);
            String start_date = results.getString(4);
            String end_date = results.getString(5);
            String desc = results.getString(6);
            String parking = results.getString(7);
            String length = results.getString(8);
            String budget = results.getString(9);
            dataTrip trip = new dataTrip(id, name, level, desti, start_date, end_date, desc, parking, length, budget);
            tripList.add(trip);
            results.moveToNext();
        }
        return tripList;
    }
    public void updateTrip(String tripId,String name, String level, String desti, String start_date, String end_date, String desc, String parking, String length, String budget){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues rowValues = new ContentValues();
        rowValues.put(TRIPNAME_COLUMN_NAME , name);
        rowValues.put(TRIPLEVEL_COLUMN_NAME , level);
        rowValues.put(TRIPDESTI_COLUMN_NAME , desti);
        rowValues.put(STARTDATE_COLUMN_NAME , start_date);
        rowValues.put(ENDDATE_COLUMN_NAME , end_date);
        rowValues.put(TRIPDESC_COLUMN_NAME , desc);
        rowValues.put(PARKING_COLUMN_NAME, parking);
        rowValues.put(TRIPLENGTH_COLUMN_NAME, length);
        rowValues.put(TRIPBUDGET_COLUMN_NAME, budget);
        long result = database.update(DATABASE_NAME, rowValues, "trip_id=?", new String[]{tripId});
    }
    public void deleteTrip(int tripId) {
        SQLiteDatabase database = getWritableDatabase();
        String whereClause = "trip_id=?";
        String[] whereArgs = {String.valueOf(tripId)};
        database.delete("list_trip", whereClause, whereArgs);
    }
    public void resetDatabase() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(database);
    }

}
