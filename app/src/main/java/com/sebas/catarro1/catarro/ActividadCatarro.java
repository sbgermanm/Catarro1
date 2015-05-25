package com.sebas.catarro1.catarro;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sebas.catarro1.R;
import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.db.dataObjects.CatarroDb;
import com.sebas.catarro1.db.dataObjects.PrescripcionDB;
import com.sebas.catarro1.db.dataObjects.SintomaDB;
import com.sebas.catarro1.sintoma.ActividadNuevoSintoma;
import com.sebas.catarro1.util.AdaptadorListasBasico;
import com.sebas.catarro1.util.ConfirmationDialogFragment;
import com.sebas.catarro1.util.Miscelanea;

import java.util.List;

public class ActividadCatarro extends ActionBarActivity implements ConfirmationDialogFragment.EliminarPersonaDialogListener, AdapterView.OnItemClickListener {


    private BaseDePatos baseDePatos;
    private Integer catarroID;
    private TextView tvNombreCatarro;
    private ListView lvListaSintomas;
    private ListView lvListaTomas;
    private Integer personaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_catarro);

        baseDePatos = BaseDePatos.getInstance(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        catarroID = Miscelanea.BundleGetInteger(bundle, "ID_CATARRO");
        personaID = Miscelanea.BundleGetInteger(bundle, "ID_PERSONA");


        tvNombreCatarro = (TextView) findViewById(R.id.etFechaYNombreCatarro);
        lvListaSintomas = (ListView) findViewById(R.id.listViewSintomas);
        lvListaTomas = (ListView) findViewById(R.id.listViewTomas);

        lvListaSintomas.setOnItemClickListener(this);
        lvListaTomas.setOnItemClickListener(this);



    }


    @Override
    protected void onResume() {
        super.onResume();
        mostrarCatarro(catarroID);
        mostrarSintomas();
        mostrarTomas();
    }

    private void mostrarTomas() {
        List<PrescripcionDB> tomas = PrescripcionDB.findByFKOrderedByDateDesc(baseDePatos, catarroID);
        AdaptadorListasBasico<PrescripcionDB> adaptadorListasBasico = new AdaptadorListasBasico<>(this, android.R.layout.simple_list_item_1, tomas);
        lvListaTomas.setAdapter(adaptadorListasBasico);

    }

    private void mostrarSintomas() {
        List<SintomaDB> sintomas = SintomaDB.findByFKOrderedByDateDesc(baseDePatos, catarroID);
        AdaptadorListasBasico<SintomaDB> adaptadorListasBasico = new AdaptadorListasBasico<>(this, android.R.layout.simple_list_item_1, sintomas);
        lvListaSintomas.setAdapter(adaptadorListasBasico);
    }

    private void mostrarCatarro(Integer catarroID) {
        CatarroDb catarroDb = CatarroDb.findById(baseDePatos, catarroID);
        tvNombreCatarro.setText(catarroDb.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_catarro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionBarModificarCatarro) {
            Intent i = new Intent(this, ActividadNuevoCatarro.class);
            i.putExtra("ID_CATARRO", catarroID);
            i.putExtra("ID_PERSONA", personaID);

            startActivity(i);
            return true;
        }
        if (id == R.id.actionBarEliminarCatarro) {
            DialogFragment df = new ConfirmationDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TITULO", getString(R.string.message_dialog_eliminar_catarro));
            df.setArguments(bundle);
            df.show(getFragmentManager(), "eliminarCatarro");
            return true;
        }
        if (id == R.id.actionBarNuevoSintoma) {
            Intent i = new Intent(this, ActividadNuevoSintoma.class);
            i.putExtra("ID_CATARRO", catarroID);

            startActivity(i);
            return true;        }
        if (id == R.id.actionBarNuevaToma) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    //    ----------------------------- Controler methods ----------------------------------
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
        final CatarroDb item = (CatarroDb) parent.getItemAtPosition(position);
        Log.d("sebas", "" + item.getIdCatarro() + ": " + item.getNombreSintoma());
        //lanzarPantallaCatarro
        Intent i = new Intent(this, ActividadCatarro.class);
        i.putExtra("ID_CATARRO", item.getIdCatarro());
        startActivity(i);
        */
        int identidad = view.getId();
        switch (identidad) {
            case R.id.listViewSintomas:
                onItemClickListViewSintomas(parent, view, position, id);
                break;

            case R.id.listViewTomas:
                onItemClickListViewTomas(parent, view, position, id);
                break;

        }
    }

    private void onItemClickListViewSintomas(AdapterView<?> parent, View view, int position, long id) {
    }

    private void onItemClickListViewTomas(AdapterView<?> parent, View view, int position, long id) {
    }





    //    ----------------------------- ConfirmationDialogFragment.EliminarPersonaDialogListener Callbacks Controler methods ----------------------------------
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        CatarroDb.delete(baseDePatos, catarroID);
        this.finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}
