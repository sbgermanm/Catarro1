package com.sebas.catarro1.db.dataObjects;

import android.content.ContentValues;
import android.database.Cursor;

import com.sebas.catarro1.db.BaseDePatos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CatarroDb implements DataBaseTable {



    // NO TOCAR, ESTO HAY QUE REPETIRLO EN TODAS ESTAS CLASES
    public static List<CatarroDb> selectAll(BaseDePatos baseDePatos) {
        Cursor cursor = baseDePatos.selectAll(TABLE_NAME);
        int num = cursor.getCount();
        ArrayList<CatarroDb> catarros = new ArrayList<>(num);
        while (cursor.moveToNext()) {
            CatarroDb p = getCatarroFromCursor(cursor);
            catarros.add(p);
        }
        cursor.close();
        return catarros;
    }

    public static CatarroDb findById(BaseDePatos baseDePatos, Integer idCatarro) {
        Cursor cursor = baseDePatos.findById(TABLE_NAME, COLUMNS.ID.toString(), idCatarro);
        cursor.moveToFirst();
        CatarroDb p = getCatarroFromCursor(cursor);
        cursor.close();
        return p;
    }

    public static void delete(BaseDePatos baseDePatos, Integer personaID) {
        baseDePatos.delete(TABLE_NAME, COLUMNS.ID.toString(), personaID);
    }


    public void addToDB(BaseDePatos dbHelper) {
        ContentValues values = dameContentValues();
        dbHelper.add(TABLE_NAME, values);
    }

    private ContentValues dameContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMNS.FECHA.toString(), this.getFecha());
        values.put(COLUMNS.NOMBRE.toString(), this.getNombre());
        values.put(COLUMNS.COMENTARIOS.toString(), this.getComentarios());
        return values;
    }


    public void updateToDB(BaseDePatos baseDePatos) {
        ContentValues values = dameContentValues();
        baseDePatos.update(TABLE_NAME, values, COLUMNS.ID.toString(), this.idCatarro);
    }






    public static final String TABLE_NAME = "catarro";


    private static enum COLUMNS {ID, NOMBRE, FECHA, COMENTARIOS};

    private static CatarroDb getCatarroFromCursor(Cursor cursor) {
        Integer id = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID.toString()));
        String nombre = cursor.getString(cursor.getColumnIndex(COLUMNS.NOMBRE.toString()));
        Long fecha = cursor.getLong(cursor.getColumnIndex(COLUMNS.FECHA.toString()));
        String comentarios = cursor.getString(cursor.getColumnIndex(COLUMNS.COMENTARIOS.toString()));
        CatarroDb p = new CatarroDb(id, nombre, fecha, comentarios);
        return p;
    }


    Integer idCatarro;
    String nombre;
    Long fecha;
    String comentarios;

    public Integer getIdCatarro() {
        return idCatarro;
    }

    public void setIdCatarro(Integer idCatarro) {
        this.idCatarro = idCatarro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public static String getCreateTable() {
        return "CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMNS.ID + " INTEGER PRIMARY KEY, "
                + COLUMNS.NOMBRE + " TEXT, "
                + COLUMNS.FECHA + " INTEGER, "
                + COLUMNS.COMENTARIOS + " TEXT"
                + ")";
    }



    public CatarroDb(Integer id, String nombre, Long fecha, String comentarios) {
        super();
        this.idCatarro = id;
        this.nombre = nombre;
        this.comentarios = comentarios;
        this.fecha = fecha;
    }

    public CatarroDb(String nombre, Long fecha, String comentarios) {
        super();
        this.nombre = nombre;
        this.comentarios = comentarios;
        this.fecha = fecha;
    }







}
