package com.sptuts.newmaterial.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sptuts.newmaterial.fragments.Thirdfragment;
import com.sptuts.newmaterial.fragments.TweetFragment;
import com.sptuts.newmaterial.fragments.TweetList;

/**
 * Created by SHRI on 6/24/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                //Firstfragment firstFrag = new Firstfragment();
                TweetFragment firstFrag = new TweetFragment();
                return firstFrag;
            case 1:
                //Secondfragment secondFrag = new Secondfragment();
                TweetList secondFrag = new TweetList();
                return secondFrag;
            case 2:
                Thirdfragment thirdFrag = new Thirdfragment();
                return thirdFrag;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
