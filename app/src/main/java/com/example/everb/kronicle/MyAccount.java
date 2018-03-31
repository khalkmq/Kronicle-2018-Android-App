package com.example.everb.kronicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MyAccount extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

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

        // Drawer-SideMenu Setup
        mDrawerLayout = findViewById(R.id.drawer_layout_ma);
        NavigationView navigationView = findViewById(R.id.nav_view_ma);

        // Will always have the home button selected
        navigationView.getMenu().getItem(1).setChecked(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // Set item to Highlight
                menuItem.setChecked(true);

                // Close Side menu once clicked
                mDrawerLayout.closeDrawers();

                // Determines which item was selected
                int itemId = menuItem.getItemId();

                if (itemId == R.id.home_drawer) {
                    Intent intent_home = new Intent(MyAccount.this, MainActivity.class);
                    startActivity(intent_home);
                }

                if (itemId == R.id.my_account_drawer) {
                    return true;
                }

                if (itemId == R.id.settings_drawer) {
                    Intent intent_settings = new Intent(MyAccount.this, Settings.class);
                    startActivity(intent_settings);
                }

                if (itemId == R.id.about_drawer) {
                    Intent intent_about = new Intent(MyAccount.this, About.class);
                    startActivity(intent_about);
                }

                return true;
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
}
