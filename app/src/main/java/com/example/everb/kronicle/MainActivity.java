package com.example.everb.kronicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.icon_menu);

        // Drawer-SideMenu Setup
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Will always have the home button selected
        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    // Set item to Highlight
                    menuItem.setChecked(true);

                    // Close Side menu once clicked
                    mDrawerLayout.closeDrawers();

                    // Determines which item was selected
                    int itemId = menuItem.getItemId();

                    if (itemId == R.id.home_drawer) {
                        return true;
                    }

                    if (itemId == R.id.my_account_drawer) {
                        Intent intent_my_account = new Intent(MainActivity.this, MyAccount.class);
                        startActivity(intent_my_account);
                    }

                    if (itemId == R.id.settings_drawer) {
                        Intent intent_settings = new Intent(MainActivity.this, Settings.class);
                        startActivity(intent_settings);
                    }

                    if (itemId == R.id.about_drawer) {
                        Intent intent_about = new Intent(MainActivity.this, About.class);
                        startActivity(intent_about);
                    }

                    return true;
                }
            });

        // Create the tabLayout with fragments
        tablayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        // Build Tab Adapter object, Fragments go here
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentNotes(), getString(R.string.notes));
        adapter.AddFragment(new FragmentTimer(), getString(R.string.timer));
        adapter.AddFragment(new FragmentHabits(), getString(R.string.habits));

        // Adapter Setup
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
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
