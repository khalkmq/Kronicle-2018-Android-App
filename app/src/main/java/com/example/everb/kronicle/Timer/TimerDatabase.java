package com.example.everb.kronicle.Timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;


public class TimerDatabase extends SQLiteOpenHelper {

    // Turn Listener to the user Database
    interface OnDBReadyListener {
        void onDBReady(SQLiteDatabase db);
    }

    // Database Name
    private static final String TIMER_DATABASE_NAME = "TimerInfo.db";
    // Database Version
    private static final int TIMER_DATABASE_VERSION = 1;
    // Make a single instance of the database
    private static TimerDatabase timerDatabase;
    // Make Context instance
    private Context appContext;

    // Table Variables
    public static final String TIMER_TABLE_NAME = "timerUsers";
    public static final String COLUMN_TIMER_ID = "_id";        //Used for wiring dbs
    public static final String COLUMN_TIMER_USER = "userID";   //Used for wiring dbs
    public static final String COLUMN_TITLE = "title_timer";
    public static final String COLUMN_FOCUS = "focus_timer";
    public static final String COLUMN_SHORT = "short_timer";
    public static final String COLUMN_LONG = "long_timer";
    public static final String COLUMN_LONG_WAIT = "wait";

    // Private function to add entries
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TIMER_TABLE_NAME + " (" + COLUMN_TIMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TIMER_USER + " INTEGER, " + COLUMN_TITLE + " TEXT, " + COLUMN_FOCUS + " TEXT, " + COLUMN_SHORT + " TEXT, " + COLUMN_LONG + " TEXT, " + COLUMN_LONG_WAIT + " TEXT);";

    // Private function to delete entries
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS offlineUsers";

    public void addTimer(TimerData timer) {
        SQLiteDatabase timerDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, timer.getTitle());
        values.put(COLUMN_FOCUS, timer.getFocus_duration());
        values.put(COLUMN_SHORT, timer.getShort_break_duration());
        values.put(COLUMN_LONG, timer.getLong_break_duration());
        values.put(COLUMN_LONG_WAIT, timer.getLong_break_wait());

        // Inserting Row
        timerDB.insert(TIMER_TABLE_NAME, null, values);
        timerDB.close();
    }

    public List<TimerData> getAllTimers() {
        // array of columns to fetch
        String[] columns = {COLUMN_TIMER_ID, COLUMN_TITLE, COLUMN_FOCUS, COLUMN_SHORT, COLUMN_LONG, COLUMN_LONG_WAIT};
        // sorting orders
        String sortOrder =
                COLUMN_TITLE + " ASC";
        List<TimerData> timerList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table

        Cursor cursor = db.query(TIMER_TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TimerData timer = new TimerData();
                timer.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TIMER_ID))));
                timer.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                timer.setFocus_duration(cursor.getString(cursor.getColumnIndex(COLUMN_FOCUS)));
                timer.setShort_break_duration(cursor.getString(cursor.getColumnIndex(COLUMN_SHORT)));
                timer.setLong_break_duration(cursor.getString(cursor.getColumnIndex(COLUMN_LONG)));
                timer.setLong_break_wait(cursor.getString(cursor.getColumnIndex(COLUMN_LONG_WAIT)));
                // Adding user record to list
                timerList.add(timer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return timerList;
    }

    // Constructor
    TimerDatabase(Context context) {
        super(context, TIMER_DATABASE_NAME, null, TIMER_DATABASE_VERSION);
        appContext = context.getApplicationContext();
    }

    // Public call to retrieve or create the database.
    public static synchronized TimerDatabase getInstance(Context context) {
        if (timerDatabase == null) {
            timerDatabase = new TimerDatabase(context.getApplicationContext());
        }
        return timerDatabase;
    }

    /**
     * onCreate
     **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        // We can pre-populate this if needed - look at Joke Project 2
    }

    /**
     * onUpgrade
     **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * onDowngrade
     **/
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * ASync Writeable database
     **/
    public void asyncWritableDatabase(TimerDatabase.OnDBReadyListener listener) {
        new TimerDatabase.OpenDbAsyncTask().execute(listener);
    }

    /**
     * ASync class
     **/
    private static class OpenDbAsyncTask extends AsyncTask<OnDBReadyListener, Void, SQLiteDatabase> {
        TimerDatabase.OnDBReadyListener listener;

        @Override
        protected SQLiteDatabase doInBackground(TimerDatabase.OnDBReadyListener... params) {
            listener = params[0];
            return TimerDatabase.timerDatabase.getWritableDatabase();
        }

        @Override
        protected void onPostExecute(SQLiteDatabase db) {
            listener.onDBReady(db);
        }
    }

}









