package com.example.everb.kronicle;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;

    FloatingActionButton fab, fabNotes, fabTimer, fabHabits;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    private TabLayout tabLayout;

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

        /****************************************************************/
        /** THIS SEGMENT IS RESPONSIBLE FOR MENU (HAMBURGER) BEHAVIOUR **/
        // Drawer-SideMenu Setup
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Activity will have its self selected initially:
        navigationView.getMenu().getItem(0).setChecked(true);

        // This will change the highlight once the other activity is opened
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
                    // Selected Self : Nothing happens
                    return true;
                }
                // if MY ACCOUNT
                if (itemId == R.id.my_account_drawer) {
                    Intent intent_my_account = new Intent(MainActivity.this, MyAccount.class);
                    startActivity(intent_my_account);
                }
                // if SETTINGS
                if (itemId == R.id.settings_drawer) {
                    Intent intent_settings = new Intent(MainActivity.this, Settings.class);
                    startActivity(intent_settings);
                }
                // if ABOUT
                if (itemId == R.id.about_drawer) {
                    Intent intent_about = new Intent(MainActivity.this, About.class);
                    startActivity(intent_about);
                }

                return true;
            }

        });
        /*********************| END OF MENU CHUNK |**********************/


        // Create the tabLayout with fragments
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        // Build Tab Adapter object, Fragments go here
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentNotes(), getString(R.string.notes));
        adapter.AddFragment(new FragmentTimer(), getString(R.string.timer));
        adapter.AddFragment(new FragmentHabits(), getString(R.string.habits));

        // Adapter Setup for tabLayout
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    /** END OF ONCREATE **/


    // Behaviour when app returns to this page
    @Override
    public void onResume() {
        super.onResume();
        // Re-Adjust the highlighted menu item (THIS OCCURS WHEN USER PRESSES 'Back')
        navigationView.getMenu().getItem(0).setChecked(true);
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

    // Exiting the app requires the back button to be pressed twice
    boolean backButtonPressedTwice = false;

    // Closes drawer when back button is pressed
    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        else {

            if (backButtonPressedTwice) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            else {
                Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_LONG).show();

                backButtonPressedTwice = true;
                new CountDownTimer(3000, 1000) {

                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        backButtonPressedTwice = false;
                    }
                }.start();
            }
        }
    }
}
