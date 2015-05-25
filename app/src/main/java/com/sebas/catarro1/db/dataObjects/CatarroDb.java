package com.sebas.catarro1.db.dataObjects;

import android.content.ContentValues;
import android.database.Cursor;

import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.util.ItemParaListaDoble;
import com.sebas.catarro1.util.Miscelanea;

import java.util.ArrayList;
import java.util.List;


public class CatarroDb implements DataBaseTable, ItemParaListaDoble {



    // NO TOCAR, ESTO HAY QUE REPETIRLO EN TODAS ESTAS CLASES
    public static List<CatarroDb> findByFKOrderedByDateDesc(BaseDePatos baseDePatos, Integer id) {
        Cursor cursor = baseDePatos.findByFK(TABLE_NAME, FOREING_KEY, id, COLUMNS.FECHA + " DESC");
        return catarrosFromCursor(cursor);
    }

    private static List<CatarroDb> catarrosFromCursor(Cursor cursor) {
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
        values.put(COLUMNS.ID_PERSONA.toString(), this.getIdPersona());
        return values;
    }


    public int updateToDB(BaseDePatos baseDePatos) {
        ContentValues values = dameContentValues();
        return baseDePatos.update(TABLE_NAME, values, COLUMNS.ID.toString(), this.idCatarro);
    }

    public static final String TABLE_NAME = "catarro";
    private static final String FOREING_KEY = COLUMNS.ID_PERSONA.toString() ;

    private enum COLUMNS {ID, NOMBRE, FECHA, COMENTARIOS, ID_PERSONA};

    private static CatarroDb getCatarroFromCursor(Cursor cursor) {
        Integer id = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID.toString()));
        String nombre = cursor.getString(cursor.getColumnIndex(COLUMNS.NOMBRE.toString()));
        Long fecha = cursor.getLong(cursor.getColumnIndex(COLUMNS.FECHA.toString()));
        String comentarios = cursor.getString(cursor.getColumnIndex(COLUMNS.COMENTARIOS.toString()));
        Integer idPersona = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID_PERSONA.toString()));

        return new CatarroDb(id, nombre, fecha, comentarios, idPersona);
    }


    Integer idCatarro;
    String nombre;
    Long fecha;
    String comentarios;
    Integer idPersona;

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }


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
                + COLUMNS.COMENTARIOS + " TEXT,"
                + COLUMNS.ID_PERSONA + " INTEGER"
                + ")";
        //don't use FK intentionally. It must be assured by app logic.
//        to use FK we need to add this as the las line
//        + " FOREIGN KEY ("+COLUMNS.ID_PERSONA+") REFERENCES "+PersonaDb.TABLE_NAME+" ("+PersonaDb.getPKColumnName()+"));";
    }



    public CatarroDb(Integer id, String nombre, Long fecha, String comentarios, Integer idPersona) {
        super();
        this.idCatarro = id;
        this.nombre = nombre;
        this.comentarios = comentarios;
        this.fecha = fecha;
        this.idPersona = idPersona;
    }

    public CatarroDb(String nombre, Long fecha, String comentarios, Integer idPersona) {
        super();
        this.nombre = nombre;
        this.idPersona = idPersona;
        this.comentarios = comentarios;
        this.fecha = fecha;
    }


    @Override
    public String toString() {
        String aux = Miscelanea.dameFechaComoString(fecha);
        aux += ": " + nombre;
        return aux;
    }


    @Override
    public String getText1() {
        return Miscelanea.dameFechaComoString(fecha);
    }

    @Override
    public String getText2() {
        return nombre;
    }



}
