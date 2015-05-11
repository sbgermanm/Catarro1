package com.sebas.catarro1.persona;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sebas.catarro1.R;
import com.sebas.catarro1.catarro.ActividadNuevoCatarro;
import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.db.dataObjects.CatarroDb;
import com.sebas.catarro1.db.dataObjects.PersonaDb;
import com.sebas.catarro1.util.AdaptadorListasBasico;
import com.sebas.catarro1.util.ConfirmationDialogFragment;

import java.util.List;


public class ActividadPersona extends ActionBarActivity implements ConfirmationDialogFragment.EliminarPersonaDialogListener, AdapterView.OnItemClickListener {

    BaseDePatos baseDePatos ;
    TextView etNombre;
    EditText etPeso;
    Integer personaID;
    ListView lvListaCatarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);

        baseDePatos = BaseDePatos.getInstance(getApplicationContext());
        int id_persona = getIntent().getExtras().getInt("ID_PERSONA");
        personaID = new Integer(id_persona);

        etNombre = (TextView) findViewById(R.id.etNombrePersonaEdad);
        etPeso = (EditText) findViewById(R.id.etPesoPersona);
        lvListaCatarros = (ListView) findViewById(R.id.listViewCatarros);

        lvListaCatarros.setOnItemClickListener(this);




    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarPersona(personaID);
        mostrarCatarros();
    }

    private void mostrarCatarros() {
        List<CatarroDb> catarros = CatarroDb.selectAll(baseDePatos);
        AdaptadorListasBasico<CatarroDb> adaptadorListasBasico = new AdaptadorListasBasico<CatarroDb>(this, android.R.layout.simple_list_item_1 , catarros);
        lvListaCatarros.setAdapter(adaptadorListasBasico);
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
        if (id == R.id.actionBarNuevoCatarro) {
            //lanzarPantallaNuevoCatarro
            Intent i = new Intent(this, ActividadNuevoCatarro.class);
            i.putExtra("ID_PERSONA", personaID);
            startActivity(i);
            return true;

        }

        if (id == R.id.actionBarEliminarPersona) {
            //lanzarPantallaNuevoCatarro
            DialogFragment df = new ConfirmationDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TITULO", getString(R.string.message_dialog_eliminar_persona));
            df.setArguments(bundle);
            df.show(getFragmentManager(), "eliminarPersonita");
            return true;
        }

        if (id == R.id.actionBarModificarPersona) {
            //lanzarPantallaNuevoCatarro
            Intent i = new Intent(this, ActividadNuevaPersona.class);
            i.putExtra("ID_PERSONA", personaID);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //    ----------------------------- ConfirmationDialogFragment.EliminarPersonaDialogListener Callbacks Controler methods ----------------------------------
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        PersonaDb.delete(baseDePatos, personaID);
        this.finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }




//    ----------------------------- Controler methods ----------------------------------
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final CatarroDb item = (CatarroDb) parent.getItemAtPosition(position);
        Log.d("sebas", "" + item.getIdCatarro() + ": " + item.getNombre());

    }
}
