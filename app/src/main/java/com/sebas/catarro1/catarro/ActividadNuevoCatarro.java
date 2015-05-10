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
import android.widget.Toast;

import com.sebas.catarro1.R;
import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.db.dataObjects.CatarroDb;
import com.sebas.catarro1.util.ElegirFechaFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActividadNuevoCatarro extends ActionBarActivity {
    EditText etNombreCatarro;
    TextView tvFechaCatarro;
    EditText etComentariosCatarro;
    SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy");
    private Integer catarroID;
    BaseDePatos baseDePatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_nuevo_catarro);
        tvFechaCatarro = (TextView) findViewById(R.id.datePickerFechaCatarro);
        etNombreCatarro = (EditText) findViewById(R.id.etNombreCatarro);
        etComentariosCatarro = (EditText) findViewById(R.id.multiLineComentarios);


        //si queremos un update
        Bundle bundle = getIntent().getExtras();
        if (null != bundle){
            catarroID = bundle.getInt("ID_PERSONA");
            baseDePatos = BaseDePatos.getInstance(getApplicationContext());
            recuperarCatarro(catarroID);
        }
        else {
            Bundle bundleFechaInicial = dameFechaInicial();
            tvFechaCatarro.setText(dameFechaComoString(bundleFechaInicial.getInt("anno"), bundleFechaInicial.getInt("mes"), bundleFechaInicial.getInt("dia")));
        }
    }


    private void recuperarCatarro(int idCatarro) {
        CatarroDb catarroDb = CatarroDb.findById(baseDePatos, idCatarro);

        etNombreCatarro.setText(catarroDb.getNombre());

        Long fecha = catarroDb.getFecha();
        String fechaString = dameFechaComoString(fecha);
        tvFechaCatarro.setText(fechaString);

        etComentariosCatarro.setText(catarroDb.getComentarios());
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
        switch (item.getItemId()) {
            case R.id.guardar:

                if (!bDatosOk())
                    Toast.makeText(this, "Introduzca los datos requeridos", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                    try {
                        guardarCatarro();
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

    private void guardarCatarro() throws ParseException {
        BaseDePatos baseDePatos = BaseDePatos.getInstance(this.getApplicationContext());
        String fechaCatarro = String.valueOf(tvFechaCatarro.getText());
        Date date = sdf.parse(fechaCatarro);

        if (null == catarroID) {
            CatarroDb persona = new CatarroDb(etNombreCatarro.getText().toString(), date.getTime(), etComentariosCatarro.getText().toString() );
            persona.addToDB(baseDePatos);
        }else{
            CatarroDb persona = new CatarroDb(catarroID, etNombreCatarro.getText().toString(), date.getTime(), etComentariosCatarro.getText().toString() );
            persona.updateToDB(baseDePatos);
        }

    }

    private boolean bDatosOk() {
        boolean resultado = true;
        String nommbre = etNombreCatarro.getText().toString().trim();
        if (nommbre.length() == 0) {
            resultado = false;
        }
        return resultado;

    }


    public void mostrarDialogoElegirFecha(View v) {
        Bundle fechaInicial = dameFechaInicial();

        DialogFragment newFragment = new ElegirFechaFragment();
        newFragment.setArguments(fechaInicial);

        ((ElegirFechaFragment) newFragment).setOnDateSetListener(onDateSetListener);


        //newFragment.show(getSupportFragmentManager(), "datePicker");
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private Bundle dameFechaInicial() {
        Date aux = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(aux);
        Bundle fecha = new Bundle();
        fecha.putInt("dia", cal.get(Calendar.DAY_OF_MONTH));
        fecha.putInt("mes", cal.get(Calendar.MONTH));
        fecha.putInt("anno", cal.get(Calendar.YEAR));

        return fecha;
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String fechaString = dameFechaComoString(year, monthOfYear, dayOfMonth);

            tvFechaCatarro.setText(fechaString);

        }
    };

    public String dameFechaComoString(int year, int monthOfYear, int dayOfMonth) {
        String result;

        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, monthOfYear);
        calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date fecha = calender.getTime();
        result = sdf.format(fecha);


        return result;
    }
    private String dameFechaComoString(Long fechaNacimiento) {
        Date aux = new Date(fechaNacimiento);
        return sdf.format(aux);
    }

}
