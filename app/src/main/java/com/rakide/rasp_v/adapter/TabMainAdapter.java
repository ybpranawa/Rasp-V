package com.rakide.rasp_v.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rakide.rasp_v.fragment.ContactFragment;
import com.rakide.rasp_v.fragment.DialFragment;

public class TabMainAdapter extends FragmentPagerAdapter {
    String[] title = new String[]{
            "Dial", "Contact"
    };

    public TabMainAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new DialFragment();
                break;
            case 1:
                fragment = new ContactFragment();
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
