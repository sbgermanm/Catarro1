package com.sebas.catarro1.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sebas.catarro1.R;

import java.util.List;

/**
 * Created by sgerman on 10/05/2015.
 */
public class AdaptadorListasDoblesBasico<T extends ItemParaListaDoble> extends ArrayAdapter<T> {

    /*

    Aqui el listview se tiene que llamar @+listview (id especial listas de android)


    <ListView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/listview"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />


    En el OnResume()

        AdaptadorListasDoblesBasico<CatarroDb> a2 = new AdaptadorListasDoblesBasico<CatarroDb>(this, android.R.layout.simple_list_item_2 , android.R.id.text1, catarros);
        lvListaCatarros.setAdapter(a2);

     */

    public AdaptadorListasDoblesBasico(Context context, int textViewResourceId, List<T> objects) {
        super(context, textViewResourceId, objects);
    }

    ////Este constructor es para pasar directamente el id del ListView y el del textView que se muestra. Solo eso, una sola linea.
    public AdaptadorListasDoblesBasico(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(R.id.list_view_text1);
        TextView text2 = (TextView) view.findViewById(R.id.list_view_text2);
        T item = getItem(position);
        text1.setText(item.getText1());
        text2.setText(item.getText2());
        return view;


    }


}
