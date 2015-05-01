package com.sebas.catarro1;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.db.dataObjects.PersonaDb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ActividadNuevaPersona extends ActionBarActivity {

    TextView fechaNacimiento;
    EditText etNombrePersona;
    EditText etPeso;

    SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_persona);
        fechaNacimiento = (TextView) findViewById(R.id.fechaNacDatePicker);
        fechaNacimiento.setText(dameFechaComoString(2000, 1, 1));

        etNombrePersona = (EditText)  findViewById(R.id.etNombrePersona);
        etPeso = (EditText)  findViewById(R.id.etPeso);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nueva_persona, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.guardar:

                if (!bDatosOk())
                    Toast.makeText(this, "Introduzca los datos requeridos", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                    try {
                        guardarPersona();
                    } catch (ParseException e) {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                this.finish();
                return true;
            case R.id.cancelar:
                Toast.makeText(this, "nok", Toast.LENGTH_SHORT).show();
                this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void guardarPersona() throws ParseException {
        BaseDePatos baseDePatos = BaseDePatos.getInstance(this.getApplicationContext());
        String fechaNacimientoText = String.valueOf(fechaNacimiento.getText());
        Date date = sdf.parse(fechaNacimientoText);
        Integer peso = new Integer(etPeso.getText().toString());
        PersonaDb persona = new PersonaDb(etNombrePersona.getText().toString(),peso, date.getTime() );
        persona.addToDB(baseDePatos);


    }

    private boolean bDatosOk() {
        boolean bResult=true;
        String nommbre = etNombrePersona.getText().toString().trim();
        if (nommbre.length() == 0)
        {
            bResult = false;
        }
        Integer peso = new Integer(etPeso.getText().toString());
        if (peso < 0 ) bResult = false;
        if (peso > 200) bResult = false;
        return bResult;
    }


    public void mostrarDialogoElegirFecha(View v) {
        Bundle fechaInicial = dameFechaNacimientoDefecto();

        DialogFragment newFragment = new ElegirFechaFragment();
        newFragment.setArguments(fechaInicial);

        ((ElegirFechaFragment)newFragment).setOnDateSetListener(onDateSetListener);


        //newFragment.show(getSupportFragmentManager(), "datePicker");
        newFragment.show(getFragmentManager(),"datePicker");
    }

    private Bundle dameFechaNacimientoDefecto() {
        Bundle fecha = new Bundle();
        fecha.putInt("dia", 1);
        fecha.putInt("mes", 1);
        fecha.putInt("anno", 2000);

        return fecha;
    }


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

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String fechaString = dameFechaComoString(year, monthOfYear, dayOfMonth);

            fechaNacimiento.setText(fechaString);

        }
    };
}
