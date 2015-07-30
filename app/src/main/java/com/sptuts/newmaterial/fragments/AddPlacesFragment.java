package com.sptuts.newmaterial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sptuts.newmaterial.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddPlacesFragment extends Fragment {

    @InjectView(R.id.btnFromDate)
    TextView btnFromDate;
    @InjectView(R.id.btnToDate)
    TextView btnToDate;
    @InjectView(R.id.btnFromTime)
    TextView btnFromTime;
    @InjectView(R.id.btnToTime)
    TextView btnToTime;

    public AddPlacesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_places, container, false);
        ButterKnife.inject(this, view);
        return view;
    }


    @OnClick({R.id.btnFromDate, R.id.btnToDate})
    public void setDates(final View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                if (view.getId() == R.id.btnFromDate) {
                    btnFromDate.setText(date);
                } else {
                    btnToDate.setText(date);
                }
                //tvFromDate.setText(date);
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @OnClick({R.id.btnFromTime, R.id.btnToTime})
    public void setTimes(final View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
                String time = hourOfDay + "h" + minute;
                if (view.getId() == R.id.btnFromTime) {
                    btnFromTime.setText(time);
                } else {
                    btnToTime.setText(time);
                }
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }


}
