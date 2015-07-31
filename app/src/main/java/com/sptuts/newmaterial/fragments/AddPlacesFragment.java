package com.sptuts.newmaterial.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.sptuts.newmaterial.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemSelected;


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
        return view;
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
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                if (view.getId() == R.id.tvFromDate) {
                    tvFromDate.setText(date);
                } else {
                    tvToDate.setText(date);
                }
                //tvFromDate.setText(date);
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
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
                            if (i > 0){
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
}
