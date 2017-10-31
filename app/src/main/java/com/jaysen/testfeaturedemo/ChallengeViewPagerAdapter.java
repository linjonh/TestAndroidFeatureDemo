package com.jaysen.testfeaturedemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 挑战任务viewPager Adapter
 * Created by jaysen.lin@foxmail.com on 2017/5/22.
 */

public class ChallengeViewPagerAdapter extends FragmentPagerAdapter {
    private String[] mTabTitles;

    public ChallengeViewPagerAdapter(FragmentManager fm, String[] tabTitles) {
        super(fm);
        mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new ChallengeSubFragment();
                break;
            case 1:
            default:
                fragment = new ChallengeSubFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles != null && mTabTitles.length >= getCount() ? mTabTitles[position] : "TAB " + position;
    }
}
