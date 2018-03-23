package com.example.everb.kronicle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name,CursorFactory factory, int version) {

        super(context, name, factory, version);
    }

    // This function is called when there's no database on the app
    @Override
    public void onCreate(SQLiteDatabase _db) {

        _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);

    }

    // When there's a mismatch between the current database and the one on the app, the old database is updated to the current version
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {

        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");

        // Update the existing database to the new version. More than one previous version can be handled by comparing _oldVersion and _newVersion values
        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + "LOGIN");

        // Create a new one.
        onCreate(_db);
    }

}