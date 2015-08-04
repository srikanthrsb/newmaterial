package com.sptuts.newmaterial.fragments;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sptuts.newmaterial.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelectLocationFragment extends Fragment {

    public SelectLocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //setupWindowAnimations();
        return inflater.inflate(R.layout.fragment_select_location, container, false);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= 21) {
            Explode explode = new Explode();
            explode.setDuration(2000);
            getActivity().getWindow().setEnterTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(2000);
            getActivity().getWindow().setReturnTransition(fade);
        }
    }
}
