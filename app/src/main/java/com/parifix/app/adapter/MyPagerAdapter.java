package com.parifix.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.parifix.app.util.CustomPager;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList();
    private int mCurrentPosition = -1;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        super.setPrimaryItem(viewGroup, i, obj);
        if (i != this.mCurrentPosition) {
            Fragment fragment = (Fragment) obj;
            CustomPager customPager = (CustomPager) viewGroup;
            if (fragment != null && fragment.getView() != null) {
                this.mCurrentPosition = i;
                customPager.measureCurrentView(fragment.getView());
            }
        }
    }

    public Fragment getItem(int i) {
        return (Fragment) this.fragments.get(i);
    }

    public int getCount() {
        return this.fragments.size();
    }
}