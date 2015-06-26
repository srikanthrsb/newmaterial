package com.sptuts.newmaterial.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sptuts.newmaterial.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Secondfragment extends android.support.v4.app.Fragment {


    @InjectView(R.id.tilLName) TextInputLayout tilName;
    @InjectView(R.id.tilAge) TextInputLayout tilAge;
    @InjectView(R.id.tilPhone) TextInputLayout tilPhone;
    @InjectView(R.id.edAge) EditText edAge;
    @InjectView(R.id.edPhone) EditText edPhone;

    public Secondfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.inject(this,view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tilName.setErrorEnabled(true);
        tilName.setError("Min 2 char required");
        edAge.setError("Required");
        tilPhone.setErrorEnabled(true);
        tilPhone.setError("Enter a phone number");
        edPhone.setError("Required");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
