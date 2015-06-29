package com.sptuts.newmaterial.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sptuts.newmaterial.R;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetFragment extends android.support.v4.app.Fragment {


    public TweetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LinearLayout myLayout = (LinearLayout)getActivity().findViewById(R.id.mytwt);
        final List<Long> twtIDs = Arrays.asList(503435417459249153L, 510908133917487104L, 473514864153870337L, 477788140900347904L);
         //Arrays.asList(510908133917487104L,20L);
        TweetUtils.loadTweets(twtIDs, new LoadCallback<List<Tweet>>() {
            @Override
            public void success(List<Tweet> tweets) {
                for (Tweet tweet:tweets)
                    myLayout.addView(new CompactTweetView(getActivity(),tweet));
            }

            @Override
            public void failure(TwitterException e) {
                Snackbar.make(getView(),"Some problem with twitter",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
