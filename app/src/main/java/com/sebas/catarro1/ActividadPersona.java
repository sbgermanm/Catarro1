package com.sebas.catarro1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.db.dataObjects.PersonaDb;


public class ActividadPersona extends ActionBarActivity {

    BaseDePatos baseDePatos ;
    TextView etNombre;
    EditText etPeso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);
        int id_persona = getIntent().getExtras().getInt("ID_PERSONA");
        etNombre = (TextView) findViewById(R.id.etNombrePersonaEdad);
        etPeso = (EditText) findViewById(R.id.etPesoPersona);

        baseDePatos = BaseDePatos.getInstance(getApplicationContext());

        recuperarPersona(id_persona);

    }

    private void recuperarPersona(int id_persona) {
        PersonaDb personaDb = PersonaDb.findById(baseDePatos, id_persona);
        String aux = personaDb.getNombre() ;
        int edad = personaDb.getEdad();
        aux = aux + ", " + edad + " años";

        etNombre.setText(aux);
        int peso = personaDb.getPeso();
        etPeso.setText(""+peso);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_persona, menu);
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
}
