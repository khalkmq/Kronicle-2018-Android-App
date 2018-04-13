package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

public class UserDatabase extends SQLiteOpenHelper {

    // Turn Listener to the user Database
    interface OnDBReadyListener {
        void onDBReady(SQLiteDatabase db);
    }

    // Database Name
    private static final String DATABASE_NAME = "userInfo.db";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Make a single instance of the database
    private static UserDatabase userDatabase;
    // Make Context instance
    private Context appContext;

    // Private function to add entries
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE offlineUsers (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT, " +
                    "password TEXT, " +
                    "email TEXT, " +
                    "birthdate TEXT);";

    // Private function to delete entries
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS offlineUsers";

    // Constructor
    private UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        appContext = context.getApplicationContext();
    }

    // Public call to retrieve or create the database.
    public static synchronized UserDatabase getInstance(Context context) {
        if (userDatabase == null) {
            userDatabase = new UserDatabase(context.getApplicationContext());
        }
        return userDatabase;
    }

    /** onCreate **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        // We can pre-populate this if needed - look at Joke Project 2
    }

    /** onUpgrade **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /** onDowngrade **/
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /** ASync Writeable database **/
    public void asyncWritableDatabase(OnDBReadyListener listener) {
        new OpenDbAsyncTask().execute(listener);
    }

    /** ASync class **/
    private static class OpenDbAsyncTask extends AsyncTask<OnDBReadyListener,Void,SQLiteDatabase> {
        OnDBReadyListener listener;

        @Override
        protected SQLiteDatabase doInBackground(OnDBReadyListener... params){
            listener = params[0];
            return UserDatabase.userDatabase.getWritableDatabase();
        }

        @Override
        protected void onPostExecute(SQLiteDatabase db) {
            listener.onDBReady(db);
        }
    }
}
