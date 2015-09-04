package com.sptuts.newmaterial.fragments;


import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sptuts.newmaterial.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocFragment extends android.support.v4.app.Fragment {

    private GoogleMap mMap;
    TextView tvCurrentLocation;

    public LocFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loc, container, false);
        tvCurrentLocation = (TextView) view.findViewById(R.id.tvCurrentLocation);
        setUpMapIfNeeded();
        showLocationDialog();
        //showCurrentLocation();
        return view;
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    private void showLocationDialog(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************
    }

    public void showCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Best location provider is decided by the criteria
        Criteria criteria = new Criteria();
        //location manager will take the best location from the criteria
        locationManager.getBestProvider(criteria, true);


        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addressList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++) {
                    sb.append(addressList.get(0).getAddressLine(i).toString() + "\n");

                }

                tvCurrentLocation.setText(sb.toString() + "\n" +
                        "LatLng " + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()));

                /*tvCurrentLocation.setText(addressList.get(0).getMaxAddressLineIndex() + "\n" +
                        "LatLng " + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()));*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
