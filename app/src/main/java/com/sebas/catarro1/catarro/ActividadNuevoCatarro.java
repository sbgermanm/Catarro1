package com.sebas.catarro1.catarro;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.sebas.catarro1.R;
import com.sebas.catarro1.util.ElegirFechaFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActividadNuevoCatarro extends ActionBarActivity {
    EditText nombreCatarro;
    TextView fechaCatarro;
    EditText comentariosCatarro;
    SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_nuevo_catarro);
        fechaCatarro = (TextView) findViewById(R.id.datePickerFechaCatarro);
        nombreCatarro = (EditText) findViewById(R.id.etNombreCatarro);
        comentariosCatarro = (EditText) findViewById(R.id.multiLineComentarios);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_nuevo_catarro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void mostrarDialogoElegirFecha(View v) {
        Bundle fechaInicial = dameFechaInicial();

        DialogFragment newFragment = new ElegirFechaFragment();
        newFragment.setArguments(fechaInicial);

        ((ElegirFechaFragment)newFragment).setOnDateSetListener(onDateSetListener);


        //newFragment.show(getSupportFragmentManager(), "datePicker");
        newFragment.show(getFragmentManager(),"datePicker");
    }
    private Bundle dameFechaInicial() {
        Bundle fecha = new Bundle();
        fecha.putInt("dia", 1);
        fecha.putInt("mes", 1);
        fecha.putInt("anno", 2000);

        return fecha;
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String fechaString = dameFechaComoString(year, monthOfYear, dayOfMonth);

            fechaCatarro.setText(fechaString);

        }
    };

    public String dameFechaComoString(int year, int monthOfYear, int dayOfMonth){
        String result;

        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, monthOfYear);
        calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date fecha = calender.getTime();
        result = sdf.format(fecha);


        return result;
    }

}
