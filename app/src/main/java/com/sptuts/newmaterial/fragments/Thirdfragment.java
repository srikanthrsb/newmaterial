package com.sptuts.newmaterial.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sptuts.newmaterial.R;
import com.sptuts.newmaterial.adapters.RecyclerAdapter;
import com.sptuts.newmaterial.models.CWCountries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Thirdfragment extends android.support.v4.app.Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    public Thirdfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<CWCountries> cwCountriesList = new ArrayList<CWCountries>();
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            cwCountriesList.add(new CWCountries(obj.getCountry(), obj.getDisplayCountry()));
        }

        recyclerAdapter = new RecyclerAdapter(cwCountriesList);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
