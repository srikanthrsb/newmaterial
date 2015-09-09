package com.sptuts.newmaterial.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.sptuts.newmaterial.MainActivity;
import com.sptuts.newmaterial.R;
import com.sptuts.newmaterial.utils.ConnectionDetector;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, GoogleApiClient.ConnectionCallbacks, LocationListener {

    protected static final String TAG = "location-settings";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;

    protected Location mCurrentLocation;
    TextView tvCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
        buildGoogleApiClient();
        //checkLocationSettings();
        //checkConnectionDetails();
        //checkISODateTime();
        String iso = getISODate("07-Sep-2015", "dd-MMM-yyyy");
        tvCurrentLocation.setText(iso);
    }

    private void checkISODateTime() {
        String strDate = "07-Sep-2015";
        String strTime = "8:00 pm";
        SimpleDateFormat form = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat formTime = new SimpleDateFormat("h:mm aa");
        Date date = null;
        Date time = null;
        try {
            date = form.parse(strDate);
            time = formTime.parse(strTime);
            SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat postFormaterTime = new SimpleDateFormat("HH:mm:ss");
            String newDateStr = postFormater.format(date);
            String newTimeStr = postFormaterTime.format(time);
            tvCurrentLocation.setText(newDateStr + "\n" + newTimeStr);
            //String newTimeStr = sdf.format(time);
            //System.out.println("Date  : "+newDateStr);
            //System.out.println("Time  : "+newTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getISODate(String strDate, String strFormat) {
        String isoDate = "";
        SimpleDateFormat form = new SimpleDateFormat(strFormat);
        SimpleDateFormat iSOFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = form.parse(strDate);
            isoDate = iSOFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isoDate;
    }


    private String getDateTimeInFormat(String strDate, String fromFormat, String toFormat) {
        String strNewDate = "";
        SimpleDateFormat from = new SimpleDateFormat(fromFormat);
        SimpleDateFormat to = new SimpleDateFormat(toFormat);
        Date date = null;
        try {
            date = from.parse(strDate);
            strNewDate = to.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strNewDate;
    }


    public static String formatDateTime(Context context, String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }


    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void checkLocationSettings() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        mLocationSettingsRequest = builder.build();
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");
                Toast.makeText(getApplicationContext(), "Location is set : LocationSettingsResult", Toast.LENGTH_LONG).show();
                //startLocationUpdates();
                showCurrentLocation();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(MyActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        Toast.makeText(getApplicationContext(), "Location is set, onActivityResult", Toast.LENGTH_LONG).show();
                        showCurrentLocation();
                        //startLocationUpdates();
                        //checkLocationSettings();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        Toast.makeText(getApplicationContext(), "Location is NOT Set, onActivityResult : GO BACK", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.
        /*if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            //stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            //updateLocationUI();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                /*mRequestingLocationUpdates = true;
                setButtonsEnabledState();*/
                showCurrentLocation();
            }
        });
    }


    protected void checkConnectionDetails() {
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toast.makeText(getApplicationContext(), "Internet not connected", Toast.LENGTH_LONG).show();
            /*Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);*/
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(intent);
            /*alert.showAlertDialog(this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);*/
            // stop executing code by return
            return;
        }
    }

    public void showCurrentLocation() {
        /*if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Toast.makeText(getApplicationContext(),"Location is null",Toast.LENGTH_LONG).show();
        }*/
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
            if (addressList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++) {
                    sb.append(addressList.get(0).getAddressLine(i).toString() + "\n");

                }

                tvCurrentLocation.setText(sb.toString() + "\n" +
                        "LatLng " + String.valueOf(mCurrentLocation.getLatitude()) + "," + String.valueOf(mCurrentLocation.getLongitude()));

                /*tvCurrentLocation.setText(addressList.get(0).getMaxAddressLineIndex() + "\n" +
                        "LatLng " + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()));*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) return;
        mCurrentLocation = location;
    }
}
