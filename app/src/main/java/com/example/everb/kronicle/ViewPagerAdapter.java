package com.example.everb.kronicle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    //Variables
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentListTitles = new ArrayList<>();

    // Constructor
    public ViewPagerAdapter(FragmentManager x) {
        super(x);
    }

    // Getters
    @Override
    public Fragment getItem(int position) {
       return fragmentList.get(position);
    }
    @Override
    public int getCount() {
        return FragmentListTitles.size();
    }
    @Override
    public CharSequence getPageTitle(int position){
        return FragmentListTitles.get(position);
    }

    // Fucntion to Add Fragment
    public void AddFragment(Fragment f, String t){
        fragmentList.add(f);
        FragmentListTitles.add(t);
    }
}
