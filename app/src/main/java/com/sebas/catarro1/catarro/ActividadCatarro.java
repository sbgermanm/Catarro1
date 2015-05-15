package com.sebas.catarro1.catarro;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sebas.catarro1.R;
import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.db.dataObjects.CatarroDb;
import com.sebas.catarro1.db.dataObjects.SintomaDB;
import com.sebas.catarro1.util.AdaptadorListasBasico;
import com.sebas.catarro1.util.Miscelanea;

import java.util.List;

public class ActividadCatarro extends ActionBarActivity implements AdapterView.OnItemClickListener {


    private BaseDePatos baseDePatos;
    private Integer catarroID;
    private TextView tvNombre;
    private ListView lvListaSintomas;
    private ListView lvListaTomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_catarro);

        baseDePatos = BaseDePatos.getInstance(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        catarroID = Miscelanea.BundleGetInteger(bundle, "ID_CATARRO");

        tvNombre = (TextView) findViewById(R.id.etFechaYNombreCatarro);
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
        List<TomasDB> tomas = TomasDB.selectAllOrderedByDateDesc(baseDePatos);
        AdaptadorListasBasico<TomasDB> adaptadorListasBasico = new AdaptadorListasBasico<TomasDB>(this, android.R.layout.simple_list_item_1 , tomas);
        lvListaTomas.setAdapter(adaptadorListasBasico);

    }

    private void mostrarSintomas() {
        List<SintomaDB> sintomas = SintomaDB.selectAllOrderedByDateDesc(baseDePatos);
        AdaptadorListasBasico<SintomaDB> adaptadorListasBasico = new AdaptadorListasBasico<SintomaDB>(this, android.R.layout.simple_list_item_1 , sintomas);
        lvListaSintomas.setAdapter(adaptadorListasBasico);
    }

    private void mostrarCatarro(Integer catarroID) {
        CatarroDb catarroDb = CatarroDb.findById(baseDePatos, catarroID);
        tvNombre.setText(catarroDb.toString());
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
        if (id == R.id.action_settings) {
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

}
