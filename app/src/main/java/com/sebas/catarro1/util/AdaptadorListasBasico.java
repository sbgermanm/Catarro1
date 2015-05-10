package com.sebas.catarro1.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by sgerman on 10/05/2015.
 */
public class AdaptadorListasBasico<T> extends ArrayAdapter<T> {

    /*

    Este constructor es para pasar directamente el textview de nuestro template que se correponde con el que se debe motrar en el ListView. Solo eso, una sola linea.
    Aqui el listview se tiene que llamar @+listview (id especial listas de android)


    <ListView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/listview"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />

    En este caso no hace falta pasar el id de la lista sino solo el elemento que se muestra, uno pre existenete de android:  android.R.layout.simple_list_item_1


    final AdaptadorListasBasico adapter = new AdaptadorListasBasico(this, android.R.layout.simple_list_item_1, list);

    http://www.vogella.com/tutorials/AndroidListView/article.html




     */

    public AdaptadorListasBasico(Context context, int textViewResourceId,
                              List<T> objects) {
        super(context,textViewResourceId, objects);
    }

    ////Este constructor es para pasar directamente el id del ListView y el del textView que se muestra. Solo eso, una sola linea.
    public AdaptadorListasBasico(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }




















    /*
    //Si queremos dos lineas, podemos usar este constructor. y luego en el getview meter esto:
     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_2, android.R.id.text1, values);

     @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        TextView text2 = (TextView) view.findViewById(android.R.id.text2);

        text1.setText(persons.get(position).getName());
        text2.setText(persons.get(position).getAge());
        return view;
      }


      o así
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;

        if (convertView == null) view = mInflater.inflate(resource, parent, false);
        else view = convertView;


        try {
            if (mFieldId == 0) {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = (TextView) view;
            } else {
                //  Otherwise, find the TextView field within the layout
                text = (TextView) view.findViewById(mFieldId);
            }
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        T item = getItem(position);

        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        TextView text2 = (TextView) view.findViewById(android.R.id.text2);

        text1.setText(item.get(position).getLinea1());
        text2.setText(item.get(position).getLinea2());
        return view;
      }
  */
    /*

    //Si el toString() de los objetos nos nos vale para mostrar los datos o queremos usar un layout personalizado, tenemos que meter códígo aquí
        //sacado de ArrayAdapter. cambiando text.setText(item.toString()) por text.setText(item.dameTextoParaRowEnElListView()
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Si el toString() de los objetos nos nos vale para mostrar los datos o queremos usar un layout personalizado, tenemos que meter códígo aquí
        //sacado de ArrayAdapter. cambiando text.setText(item.toString()) por text.setText(item.dameTextoParaRowEnElListView()

        View view;
        TextView text;

        if (convertView == null) {
            view = mInflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        try {
            if (mFieldId == 0) {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = (TextView) view;
            } else {
                //  Otherwise, find the TextView field within the layout
                text = (TextView) view.findViewById(mFieldId);
            }
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        T item = getItem(position);
        if (item instanceof CharSequence) {
            text.setText((CharSequence)item);
        } else {
            text.setText(item.dameTextoParaRowEnElListView());
        }

        return view;


    */

    /*

    //Si no usamos un layout simple, con un solo campo tipo TextView (que pasamos en el constructor)
// use your custom layout (el R.id.label tiene que ser un textView)
    el constructor
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.rowlayout, R.id.label, values);

    el layout
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" >

        <ImageView
            android:id="@+id/icon"
            android:layout_width="22px"
            android:layout_height="22px"
            android:layout_marginLeft="4px"
            android:layout_marginRight="10px"
            android:layout_marginTop="4px"
            android:src="@drawable/ic_launcher" >
        </ImageView>

        <TextView
            android:id="@+id/label"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@+id/label"
            android:textSize="20px" >
        </TextView>

    </LinearLayout>




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.TextView01);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.ImageView01);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = names[position];
        holder.text.setText(s);
        if (s.startsWith("Windows7")  {
            holder.image.setImageResource(R.drawable.no);
        } else {
            holder.image.setImageResource(R.drawable.ok);
        }

        return rowView;
    }
     */

}
