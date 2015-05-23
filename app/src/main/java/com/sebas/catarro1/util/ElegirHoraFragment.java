package com.sebas.catarro1.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by sgerman on 03/04/2015.
 *
 *
 */
public  class ElegirHoraFragment extends DialogFragment {


    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    int hora;
    int minutos;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), onTimeSetListener, hora, minutos, true);
    }



    public ElegirHoraFragment() {
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        hora = args.getInt("hora");
        minutos = args.getInt("minutos");

    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener)
    {
        this.onTimeSetListener = onTimeSetListener;
    }


}