package com.sptuts.newmaterial.fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sptuts.newmaterial.R;
import com.sptuts.newmaterial.SelectLocation;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddPlacesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.tvFromDate)
    TextView tvFromDate;
    @Bind(R.id.tvToDate)
    TextView tvToDate;
    @Bind(R.id.tvFromTime)
    TextView tvFromTime;
    @Bind(R.id.tvToTime)
    TextView tvToTime;
    @Bind(R.id.tvRepeatDesc)
    TextView tvRepeatDesc;
    @Bind(R.id.tvLocName)
    TextView tvLoc;
    @Bind(R.id.tvRadDesc)
    TextView tvRadDesc;

    SeekBar sbRadius;
    Spinner spnrRepeatData;

    public AddPlacesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_places, container, false);
        ButterKnife.bind(this, view);
        spnrRepeatData = (Spinner) view.findViewById(R.id.spnrRepeatData);
        spnrRepeatData.setOnItemSelectedListener(this);
        sbRadius = (SeekBar) view.findViewById(R.id.sbRadius);
        sbRadius.setOnSeekBarChangeListener(sbRadiusLstnr);
        sbRadius.setProgress(15);
        //setSeekBar();
        //setupWindowAnimations();

        return view;
    }


    SeekBar.OnSeekBarChangeListener sbRadiusLstnr = new SeekBar.OnSeekBarChangeListener() {


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            if (progress == 0) {
                progress = 1;
            }
            float currentProgress = progress * 0.1f;
            String yourprogress = String.format("%.1f", currentProgress);
            tvRadDesc.setText("Radius : " + yourprogress + " km");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= 21) {
            Explode explode = new Explode();
            explode.setDuration(2000);
            getActivity().getWindow().setExitTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(2000);
            getActivity().getWindow().setReenterTransition(fade);
        }
    }

    /*AdapterView.OnItemSelectedListener onSelectedLstr = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
            if (pos != 4) {
                tvRepeatDesc.setText(adapterView.getItemAtPosition(pos).toString());
            } else {
                selectCustomDays();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };*/


    @OnClick({R.id.tvFromDate, R.id.tvToDate})
    public void setDates(final View view) {
        Calendar now = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                String month = simpleDateFormat.format(monthOfYear);

                //String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                String date = dayOfMonth + "-" + month + "-" + year;
                if (view.getId() == R.id.tvFromDate) {
                    tvFromDate.setText(date);
                } else {
                    tvToDate.setText(date);
                }
                //tvFromDate.setText(date);
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setMinDate(now);
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }


    @OnClick({R.id.tvFromTime, R.id.tvToTime})
    public void setTimes(final View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
                String time = hourOfDay + "h" + minute;
                if (view.getId() == R.id.tvFromTime) {
                    tvFromTime.setText(time);
                } else {
                    tvToTime.setText(time);
                }
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }


    public void selectCustomDays() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.weekdays_title)
                .items(R.array.weekdays)
                .itemsCallbackMultiChoice(new Integer[]{}, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0) {
                                str.append(text[i]);
                                str.append(", ");
                            }
                                /*str.append('\n');
                                str.append(which[i]);
                                str.append(", ");
                                str.append(text[i]);*/

                        }
                        //Toast.makeText(getActivity(), str.toString(), Toast.LENGTH_LONG).show();
                        tvRepeatDesc.setText(str.toString());
                        return true; // allow selection
                    }
                })
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNeutral(MaterialDialog dialog) {
                        dialog.clearSelectedIndices();
                    }

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //super.onPositive(dialog);
                        dialog.dismiss();
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.choose)
                .autoDismiss(false)
                        //.neutralText(R.string.clear_selection)
                .show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos != 4) {
            tvRepeatDesc.setText(adapterView.getItemAtPosition(pos).toString());
        } else {
            selectCustomDays();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.tvLocName)
    public void callLocationFragment() {
        /*SelectPlaceFragment selectPlaceFragment = new SelectPlaceFragment();
        selectPlaceFragment.setTargetFragment(AddPlacesFragment.this,200);
        selectPlaceFragment.show(getFragmentManager(),"LOCATIONTAG");*/

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), tvLoc, "sharedTextView");
        Intent intent = new Intent(getActivity(), SelectLocation.class);
        //startActivity(intent);
        //startActivity(intent, options.toBundle());
        getActivity().startActivity(intent, options.toBundle());

    }

}
