package com.sptuts.newmaterial.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sptuts.newmaterial.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddPlacesFragment extends Fragment {

    public AddPlacesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_places, container, false);
    }
}
