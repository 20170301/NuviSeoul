package com.gangnam4bungate.nuviseoul.holder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gangnam4bungate.nuviseoul.ui.fragment.PlanFragment;
import com.gangnam4bungate.nuviseoul.ui.fragment.RecommendFragment;

/**
 * Created by wsseo on 2017. 10. 25..
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                PlanFragment planFragment = new PlanFragment();
                return planFragment;
            case 1:
                RecommendFragment recommendFragment = new RecommendFragment();
                return recommendFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}