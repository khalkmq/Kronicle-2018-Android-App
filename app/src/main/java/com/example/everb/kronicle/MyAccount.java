package com.example.everb.kronicle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MyAccount extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;

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

                return true;
            }
        });
        /*********************| END OF MENU CHUNK |**********************/


    }

    /**
     * End of on-Create
     **/

    // Behaviour when app returns to this page
    @Override
    public void onResume() {
        super.onResume();
        // Re-Adjust the highlighted menu item (THIS OCCURS WHEN USER PRESSES 'Back')
        navigationView.getMenu().getItem(1).setChecked(true);
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
}
