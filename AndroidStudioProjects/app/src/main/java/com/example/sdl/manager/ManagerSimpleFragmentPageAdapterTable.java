package com.example.sdl.manager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ManagerSimpleFragmentPageAdapterTable extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "Table Orders", "Parcel orders"};
    private Context context;

    public ManagerSimpleFragmentPageAdapterTable(@NonNull FragmentManager fm) {
        super(fm);
    }

       @Override
    public Fragment getItem(int position)
    {
        if (position == 0) {
            return new ManagerOrderFragment();
        }
        else {
            return new ManagerParcelFragment();
        }
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position].toUpperCase();
    }
}
