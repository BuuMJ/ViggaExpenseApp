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
    private static final String TABLE_OBSERVATIONS = "observations";
    private static final String COLUMN_OBSERVATION_ID = "observation_id";
    private static final String COLUMN_TRIP_ID_OBSERVATION = "trip_id";
    private static final String COLUMN_OBSERVATION_TITLE = "observation_title";
    private static final String COLUMN_OBSERVATION_TIME = "observation_time";
    private static final String COLUMN_OBSERVATION_NOTES = "observation_notes";

    private static final String DATABASE_CREATE_OBSERVATION_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            TABLE_OBSERVATIONS, COLUMN_OBSERVATION_ID, COLUMN_TRIP_ID_OBSERVATION, COLUMN_OBSERVATION_TITLE, COLUMN_OBSERVATION_TIME, COLUMN_OBSERVATION_NOTES
    );


    public DatabaseHelpers(Context context){
        super(context, DATABASE_NAME, null,1);
        database = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_QUERY);
        db.execSQL(DATABASE_CREATE_OBSERVATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVATIONS);
        Log.w(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " + newVersion + " - old data lost");
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
    public long inserObservation(int tripId, String observationTitle, String observationTime, String observationNotes) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_TRIP_ID_OBSERVATION, tripId);
        rowValues.put(COLUMN_OBSERVATION_TITLE, observationTitle);
        rowValues.put(COLUMN_OBSERVATION_TIME, observationTime);
        rowValues.put(COLUMN_OBSERVATION_NOTES, observationNotes);
        return database.insertOrThrow(TABLE_OBSERVATIONS, null,rowValues);
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
    public List<Observation> getObvervationDetails(int trip_id){
        List<Observation> observationList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_OBSERVATION_ID,
                COLUMN_TRIP_ID_OBSERVATION,
                COLUMN_OBSERVATION_TITLE,
                COLUMN_OBSERVATION_TIME,
                COLUMN_OBSERVATION_NOTES
        };

        String selection = COLUMN_TRIP_ID_OBSERVATION + " = ?";
        String[] selectionArgs = {String.valueOf(trip_id)};

        Cursor cursor = db.query(
                TABLE_OBSERVATIONS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int observationId = cursor.getInt(0);
            int observationTripId = cursor.getInt(1);
            String observationTitle = cursor.getString(2);
            String observationTime = cursor.getString(3);
            String observationNotes = cursor.getString(4);
            Observation observation = new Observation(observationId, observationTripId, observationTitle, observationTime, observationNotes);
            observationList.add(observation);

            cursor.moveToNext();
        }
        cursor.close();

        return observationList;
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
    public void updateObservation(String observationId, String tripId, String observationTitle, String observationTime, String observationNotes){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_TRIP_ID_OBSERVATION, tripId);
        rowValues.put(COLUMN_OBSERVATION_TITLE, observationTitle);
        rowValues.put(COLUMN_OBSERVATION_TIME, observationTime);
        rowValues.put(COLUMN_OBSERVATION_NOTES, observationNotes);
        long result = database.update(TABLE_OBSERVATIONS, rowValues, "observation_id=?", new String[]{observationId});
    }
    public void deleteObversation(int obversationId){
        SQLiteDatabase database = getWritableDatabase();
        String whereClause = "observation_id=?";
        String[] whereArgs = {String.valueOf(obversationId)};
        database.delete("observations", whereClause, whereArgs);
    }
    public void resetDatabase() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVATIONS);
        onCreate(database);
    }

}
