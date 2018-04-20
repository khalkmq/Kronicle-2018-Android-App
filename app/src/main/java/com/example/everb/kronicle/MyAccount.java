package com.example.everb.kronicle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyAccount extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;

    //Data base stuff
    SQLiteDatabase theDB;
    long currentRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_ma);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.icon_menu);

        /****************************************************************/
        /** THIS SEGMENT IS RESPONSIBLE FOR MENU (HAMBURGER) BEHAVIOUR **/
        // Drawer-SideMenu Setup
        mDrawerLayout = findViewById(R.id.drawer_layout_ma);
        navigationView = findViewById(R.id.nav_view_ma);

        // Activity will have its self selected initially:
        navigationView.getMenu().getItem(1).setChecked(true);

        // This will run activity and highlight item once the other activity is opened
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // SAME FOR ALL: Set item to Highlight
                menuItem.setChecked(true);
                // SAME FOR ALL: Close Side menu once clicked
                mDrawerLayout.closeDrawers();
                // SAME FOR ALL: Determines which item was selected
                int itemId = menuItem.getItemId();

                // If HOME
                if (itemId == R.id.home_drawer) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 300);
                }
                // if MY ACCOUNT
                if (itemId == R.id.my_account_drawer) {
                    // Selected Self : Nothing happens
                    return true;
                }
                // if SETTINGS
                if (itemId == R.id.settings_drawer) {
                    Intent intent_settings = new Intent(MyAccount.this, Settings.class);
                    startActivity(intent_settings);
                    finish();
                }
                // if ABOUT
                if (itemId == R.id.about_drawer) {
                    Intent intent_about = new Intent(MyAccount.this, About.class);
                    startActivity(intent_about);
                    finish();
                }
                // if LOGOUT
                if (itemId == R.id.logout_drawer) {
                    Intent intent_about = new Intent(MyAccount.this, LogoutHandler.class);
                    startActivity(intent_about);
                    finish();
                }

                return true;
            }
        });
        /*********************| END OF MENU CHUNK |**********************/



    }/**End of on-Create**/


    /** Refresh button behaviour **/
    public void btnRefreshClick(View view) {

        // Check if an account is logged in
        String[] projection = {"loggedIn"};
        Cursor cursor = theDB.query("offlineUsers", projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if(cursor.getInt(cursor.getColumnIndexOrThrow("loggedIn")) == 1)
                Toast.makeText(getApplicationContext(), "BOOP MOFO", Toast.LENGTH_LONG).show();
        }
        cursor.close();

        StringBuffer sb = new StringBuffer();
        String[] columns = {"_id", "loggedIn", "username", "password", "email", "birthdate"};

        Cursor c = theDB.query("offlineUsers", columns, null, null, null, null, "_id");

        //Toast.makeText(getApplicationContext(), "Accounts Refreshed", Toast.LENGTH_LONG).show();

        while (c.moveToNext()) {
            sb.append("id: " + c.getLong(c.getColumnIndexOrThrow("_id")) + "\n");
            sb.append("logged: " + c.getString(c.getColumnIndexOrThrow("loggedIn")) + "\n");
            sb.append(c.getString(c.getColumnIndexOrThrow("username")) + "\n");
            sb.append(c.getString(c.getColumnIndexOrThrow("password")) + "\n");
            sb.append(c.getString(c.getColumnIndexOrThrow("email")) + "\n");
            sb.append(c.getString(c.getColumnIndexOrThrow("birthdate")) + "\n");
            sb.append("---------------------------------------------------------------\n");
        }
       ((TextView) findViewById(R.id.lblResults)).setText(sb);
        c.close();
    }

    /** Delete Button Behaviour **/
    public void btnDeleteClick(View view) {
        // Deletes everything.
        theDB.delete("offlineUsers", null, null);
    }

    /** Delete Account Button Behaviour **/
    public void btnDeleteAccountClick(View view) {
        // Delete the account currently Logged in
        theDB.delete("offlineUsers", "loggedIn" + "=" + "1" , null);

        // Send a toast to the User
        Toast.makeText(getApplicationContext(), "Account Successfully Deleted", Toast.LENGTH_LONG).show();

        // Go to the Landing Page
        startActivity(new Intent(this, LandingPage.class));

        // Finish this activity
        finish();
    }

    /** Log Out Button Behaviour **/
    public void btnLogOutClick(View view) {
        // Set Content
        ContentValues values = new ContentValues();
        values.put("loggedIn", false);
        theDB.update("offlineUsers", values,"loggedIn = 1", null);

        // Send a toast to the User
        Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();

        // Go to the Landing Page
        startActivity(new Intent(this, LandingPage.class));

        // Finish this activity
        finish();
    }

    // Behaviour when app returns to this page
    @Override
    public void onResume() {
        super.onResume();
        // Re-Adjust the highlighted menu item (THIS OCCURS WHEN USER PRESSES 'Back')
        navigationView.getMenu().getItem(1).setChecked(true);

        // Get a writable database
        UserDatabase.getInstance(this).asyncWritableDatabase(new UserDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;
            }
        });
    }

    // Drawer menu icon behaviour
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Closes drawer when back button is pressed, else it returns to main activity
    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        else {
        super.onBackPressed();
        }
    }

    // When paused close db
    @Override
    public void onPause() {
        super.onPause();
        theDB.close();
    }

}
