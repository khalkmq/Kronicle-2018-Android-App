package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

class LoginDataBaseAdapter {

    // Name of the Database
    static final String DATABASE_NAME = "userInfo.db";

    // Version of the Database
    static final int DATABASE_VERSION = 1;

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "LOGIN" + " (" + "ID" + " integer primary key autoincrement," + "NAME text, LAST_NAME text, EMAIL text, PASSWORD text); ";

    // Holds the instance of the Database
    public SQLiteDatabase db;

    // Context of the application using the database.
    private final Context context;

    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String name,String lastName,String email,String password) {

        // Create a new entry
        ContentValues newValues = new ContentValues();

        // Assign values for each row.
        newValues.put("NAME", name);
        newValues.put("LAST_NAME", lastName);
        newValues.put("EMAIL", email);
        newValues.put("PASSWORD",password);

        // Save information (name, last name, email, password) on the table
        db.insert("LOGIN", null, newValues);
    }

    public int deleteEntry(String email) {

        String valueLocation = "EMAIL not defined";

        int entryRemoved= db.delete("LOGIN", valueLocation, new String[]{email}) ;

        return entryRemoved;
    }

    public String accessEntry(String email) {

        Cursor cursor = db.query("LOGIN", null, "EMAIL not defined", new String[]{email}, null, null, null);

        // If the cursor is less than 0 then the account doesn't exist
        if(cursor.getCount()<1) {

            cursor.close();
            return "This account is not in our database";
        }

        else {

            cursor.moveToFirst();
            String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
            cursor.close();
            return password;
        }
    }

    public void updateEntry(String name,String lastName,String email,String password) {

        // Create an entry that will replace the current entry
        ContentValues updatedValues = new ContentValues();

        // Add the respective value to each row
        updatedValues.put("NAME", name);
        updatedValues.put("LAST_NAME", lastName);
        updatedValues.put("EMAIL", email);
        updatedValues.put("PASSWORD",password);


        String valueLocation = "EMAIL not defined";
        db.update("LOGIN", updatedValues, valueLocation, new String[]{email});
    }
}
