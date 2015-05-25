package com.sebas.catarro1.sintoma;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sebas.catarro1.R;
import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.db.dataObjects.SintomaDB;
import com.sebas.catarro1.util.ElegirFechaFragment;
import com.sebas.catarro1.util.ElegirHoraFragment;
import com.sebas.catarro1.util.Miscelanea;
import com.sebas.catarro1.util.SebasUnCheckedException;

import java.text.ParseException;
import java.util.Date;

public class ActividadNuevoSintoma extends ActionBarActivity {

    EditText etNombreSintoma;
    EditText etValor;
    EditText etUnidadesValor;
    TextView tvFechaSintoma;
    TextView tvHoraSintoma;
    EditText etComentariosCatarro;

    Integer catarroID;
    TextView lbNuevoSintoma;
    private BaseDePatos baseDePatos;
    private Integer sintomaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_nuevo_sintoma);

        etNombreSintoma = (EditText) findViewById(R.id.etNombreSintoma);
        etValor = (EditText) findViewById(R.id.etValor);
        etUnidadesValor = (EditText) findViewById(R.id.etUnidadesValor);
        tvFechaSintoma = (TextView) findViewById(R.id.datePickerFechaSintoma);
        tvHoraSintoma = (TextView) findViewById(R.id.datePickerHoraSintoma);
        etComentariosCatarro = (EditText) findViewById(R.id.multiLineComentarios);
        lbNuevoSintoma = (TextView) findViewById(R.id.lbNuevoSintoma);

        //si queremos un update
        Bundle bundle = getIntent().getExtras();
        catarroID = Miscelanea.BundleGetInteger(bundle, "ID_CATARRO");
        sintomaID = Miscelanea.BundleGetInteger(bundle, "ID_SINTOMA");


        if (null != sintomaID) {
            lbNuevoSintoma.setText(getString(R.string.lbModificarSintoma));
        }

        if (null != sintomaID) {
            baseDePatos = BaseDePatos.getInstance(getApplicationContext());
            recuperarSintoma(sintomaID);
        } else {
            Bundle bundleFechaInicial = Miscelanea.dameFechaInicial();
            tvFechaSintoma.setText(Miscelanea.dameFechaComoString(bundleFechaInicial.getInt("anno"), bundleFechaInicial.getInt("mes"), bundleFechaInicial.getInt("dia")));
        }


        // Set up action bar.
        // de momento no mostramos el UP, aunque el codigo está
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);







    }


    private void recuperarSintoma(Integer sintomaID) {
        SintomaDB sintomaDB = SintomaDB.findById(baseDePatos, sintomaID);

        etNombreSintoma.setText(sintomaDB.getNombreSintoma());

        Long fecha = sintomaDB.getFechaSintoma();
        String fechaString = Miscelanea.dameFechaComoString(fecha);
        tvFechaSintoma.setText(fechaString);
        String hora = Miscelanea.dameHoraComoString(fecha);
        tvHoraSintoma.setText(hora);

        etValor.setText(sintomaDB.getValorSintoma().toString());
        etUnidadesValor.setText(sintomaDB.getUnidadesValorSintoma());

        etComentariosCatarro.setText(sintomaDB.getComentarioSintoma());

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_nuevo_sintoma, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //deshabilitado de momento. Aqui llegamos desde actividad persona y tambien desde actividad catarro, modificar. El up siempre nos lleva a atividad persona
            case android.R.id.home:
                return true;
            case R.id.guardar:
                boolean resultado = true;
                if (!bDatosOk()) {
                    Toast.makeText(this, "Introduzca los datos requeridos", Toast.LENGTH_SHORT).show();
                    resultado = false;
                }
                else {
//                    Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                    try {
                        guardarSintoma();
                    } catch (ParseException e) {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    this.finish();
                }
                return resultado;
            case R.id.cancelar:
                Toast.makeText(this, "nok", Toast.LENGTH_SHORT).show();
                this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void guardarSintoma() throws ParseException {
        BaseDePatos baseDePatos = BaseDePatos.getInstance(this.getApplicationContext());



        String fechaCatarro = String.valueOf(tvFechaSintoma.getText());
        String hora = tvHoraSintoma.getText().toString();
        String fechaHora = fechaCatarro + " " + hora;

        Date date = Miscelanea.dameStringFechaHoraComoFecha(fechaHora);
        Double valor = Double.valueOf(etValor.getText().toString());

        if (null == sintomaID) {
            SintomaDB sintomaDB = new SintomaDB(etNombreSintoma.getText().toString(), valor, etUnidadesValor.getText().toString(), etComentariosCatarro.getText().toString(),  date.getTime(), catarroID);
            sintomaDB.addToDB(baseDePatos);
        } else {
            SintomaDB sintomaDB = new SintomaDB(sintomaID, etNombreSintoma.getText().toString(), valor, etUnidadesValor.getText().toString(), etComentariosCatarro.getText().toString(),  date.getTime(), catarroID);
            int rowsUpdated = sintomaDB.updateToDB(baseDePatos);
            if (0 == rowsUpdated) throw new SebasUnCheckedException();

        }

    }

    private boolean bDatosOk() {
        boolean resultado = true;
        String aux;
        aux = etNombreSintoma.getText().toString().trim();
        if (aux.length() == 0) {
            resultado = false;
        }
        aux = etValor.getText().toString().trim();
        if (aux.length() == 0) {
            resultado = false;
        }
        aux = etUnidadesValor.getText().toString().trim();
        if (aux.length() == 0) {
            resultado = false;
        }
        return resultado;

    }


    public void mostrarDialogoElegirFecha(View v) throws ParseException {
        Bundle fechaInicial = Miscelanea.dameFechaCortaComoBundle(tvFechaSintoma.getText().toString());

        DialogFragment newFragment = new ElegirFechaFragment();
        newFragment.setArguments(fechaInicial);

        ((ElegirFechaFragment) newFragment).setOnDateSetListener(onDateSetListener);


        //newFragment.show(getSupportFragmentManager(), "datePicker");
        newFragment.show(getFragmentManager(), "datePicker");
    }


    public void mostrarDialogoElegirHora(View v) throws ParseException {
        Bundle horaInicial = Miscelanea.dameHoraComoBundle(tvHoraSintoma.getText().toString());

        DialogFragment newFragment = new ElegirHoraFragment();
        newFragment.setArguments(horaInicial);

        ((ElegirHoraFragment) newFragment).setOnTimeSetListener(onTimeSetListener);


        //newFragment.show(getSupportFragmentManager(), "datePicker");
        newFragment.show(getFragmentManager(), "timePicker");
    }


    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String horaString = Miscelanea.dameHoraComoString(hourOfDay, minute);
            tvHoraSintoma.setText(horaString);
        }
    };





    //-------------------------------------- Callback date picker
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String fechaString = Miscelanea.dameFechaComoString(year, monthOfYear, dayOfMonth);

            tvFechaSintoma.setText(fechaString);

        }
    };














}
