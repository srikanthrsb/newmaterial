package com.sptuts.newmaterial.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sptuts.newmaterial.fragments.Thirdfragment;

/**
 * Created by SHRI on 6/24/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                //Firstfragment firstFrag = new Firstfragment();
                return new Thirdfragment(); //TweetFragment();
            case 1:
                //Secondfragment secondFrag = new Secondfragment();
                return new Thirdfragment(); //TweetList();
            case 2:
                return new Thirdfragment();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
