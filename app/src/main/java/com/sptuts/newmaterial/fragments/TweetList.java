package com.sptuts.newmaterial.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sptuts.newmaterial.R;
import com.sptuts.newmaterial.utils.Logger;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetList extends ListFragment {


   /* @InjectView(R.id.swipeRefreshTweets)
    SwipeRefreshLayout swipeRefreshTweets;
*/
    public TweetList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet_list, container, false);
        //ButterKnife.inject(view);

    }


/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        */
/*final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#NarendraModi")
                .build();*//*

        //ButterKnife.inject(getActivity());

    }
*/


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SwipeRefreshLayout swipeRefreshTweets = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipeRefreshTweets);
        //UserTimeline userTimeline = new UserTimeline.Builder().screenName("ny1").build();
        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#NarendraModi")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(getActivity(),
                searchTimeline);
        setListAdapter(adapter);

        swipeRefreshTweets.setColorSchemeResources(R.color.primary,R.color.accent);
        swipeRefreshTweets.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshTweets.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeRefreshTweets.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Logger.e("TWT",e.getMessage());
                    }
                });
            }
        });
    }
}
