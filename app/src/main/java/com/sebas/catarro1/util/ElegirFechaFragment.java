package com.sebas.catarro1.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by sgerman on 03/04/2015.
 *
 */
public  class ElegirFechaFragment extends DialogFragment {


    private int anno;
    private int mes;
    private int dia;
    DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), onDateSetListener, anno, mes, dia);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

    }

    public ElegirFechaFragment() {
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        anno = args.getInt("anno");
        mes = args.getInt("mes");
        dia = args.getInt("dia");

    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener)
    {
        this.onDateSetListener = onDateSetListener;
    }


}