package com.sebas.catarro1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.db.dataObjects.PersonaDb;
import com.sebas.catarro1.persona.ActividadNuevaPersona;
import com.sebas.catarro1.persona.ActividadPersona;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    BaseDePatos baseDePatos;
    LinearLayout linearLayout;

    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseDePatos = BaseDePatos.getInstance(this.getApplicationContext());

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

    }

    @Override
    protected void onStart() {
        super.onStart();
        linearLayout.removeAllViewsInLayout();
        mostrarPacientes();
    }


    private void mostrarPacientes() {
        int count = 0;

        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearLayout.addView(row);

        for (PersonaDb personaDb : PersonaDb.selectAll(baseDePatos)) {
            //Toast.makeText(this, personaDb.getNombre(), Toast.LENGTH_SHORT).show();
            Log.d("sebas", personaDb.getNombre());

            if (count%2 == 0){
                row = new LinearLayout(this);
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                linearLayout.addView(row);
            }

            count++;
            Button b = new Button(this);
            b.setText(personaDb.getNombre());
            b.setId(personaDb.getIdPersona());
            b.setOnClickListener(this);
            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            row.addView(b);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == R.id.nuevaPersona)
        {
//            Toast.makeText(this, "nueva persoana", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, ActividadNuevaPersona.class);
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int identidad = v.getId();
        switch (identidad) {
            default:
                //Toast.makeText(this, "pulsado: " + identidad, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, ActividadPersona.class);
                i.putExtra("ID_PERSONA", identidad);
                startActivity(i);
        }
    }
}
