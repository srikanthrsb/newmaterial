package com.sptuts.newmaterial.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sptuts.newmaterial.R;
import com.sptuts.newmaterial.adapters.RecyclerAdapter;
import com.sptuts.newmaterial.models.CWCountries;
import com.sptuts.newmaterial.views.ContextMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Thirdfragment extends android.support.v4.app.Fragment {

    ContextMenuRecyclerView recyclerView;
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
        recyclerView = (ContextMenuRecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        registerForContextMenu(recyclerView);
        List<CWCountries> cwCountriesList = new ArrayList<CWCountries>();
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            cwCountriesList.add(new CWCountries(obj.getCountry(), obj.getDisplayCountry()));
        }

        recyclerAdapter = new RecyclerAdapter(cwCountriesList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_recy, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContextMenuRecyclerView.RecyclerViewContextMenuInfo info = (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.mnuCopy:
                // do your stuff
                Toast.makeText(getContext(),"Copy is Selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuDelete:
                // do your stuff
                Toast.makeText(getContext(),"Delete is Selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuEdit:
                // do your stuff
                Toast.makeText(getContext(),"Edit is Selected",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
