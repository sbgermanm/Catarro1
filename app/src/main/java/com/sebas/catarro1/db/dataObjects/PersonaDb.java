package com.sebas.catarro1.db.dataObjects;

import android.content.ContentValues;
import android.database.Cursor;

import com.sebas.catarro1.db.BaseDePatos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PersonaDb implements DataBaseTable {



    // NO TOCAR, ESTO HAY QUE REPETIRLO EN TODAS ESTAS CLASES
    public static List<PersonaDb> selectAll(BaseDePatos baseDePatos) {
        Cursor cursor = baseDePatos.selectAll(TABLA_PERSONA);
        int num = cursor.getCount();
        ArrayList<PersonaDb> personas = new ArrayList<>(num);
        while (cursor.moveToNext()) {
            PersonaDb p = getPersonFromCursor(cursor);
            personas.add(p);
        }
        cursor.close();
        return personas;
    }

    public static PersonaDb findById(BaseDePatos baseDePatos, Integer idPersona) {
        Cursor cursor = baseDePatos.findById(TABLA_PERSONA, COLUMNS.ID_PERSONA.toString(), idPersona);
        cursor.moveToFirst();
        PersonaDb p = getPersonFromCursor(cursor);
        cursor.close();
        return p;
    }

    public static void delete(BaseDePatos baseDePatos, Integer personaID) {
        baseDePatos.delete(TABLA_PERSONA, COLUMNS.ID_PERSONA.toString(), personaID);
    }


    public void addToDB(BaseDePatos dbHelper) {
        ContentValues values = dameContentValues();
        dbHelper.add(TABLA_PERSONA, values);
    }

    private ContentValues dameContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMNS.FECHA_NACIMEINTO.toString(), this.getFechaNacimiento());
//        values.put(COLUMNS.ID_PERSONA.toString(), this.getIdPersona());
        values.put(COLUMNS.NOMBRE.toString(), this.getNombre());
        values.put(COLUMNS.PESO.toString(), this.getPeso());
        return values;
    }


    public int updateToDB(BaseDePatos baseDePatos) {
        ContentValues values = dameContentValues();
        return baseDePatos.update(TABLA_PERSONA, values, COLUMNS.ID_PERSONA.toString(), this.idPersona);
    }






    public static final String TABLA_PERSONA = "persona";

    public static String getPKColumnName() {
        return String.valueOf(COLUMNS.ID_PERSONA);
    }


    private static enum COLUMNS {ID_PERSONA, NOMBRE, FECHA_NACIMEINTO, PESO};

    private static PersonaDb getPersonFromCursor(Cursor cursor) {
        Integer id = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID_PERSONA.toString()));
        String nombre = cursor.getString(cursor.getColumnIndex(COLUMNS.NOMBRE.toString()));
        Long fechaNac = cursor.getLong(cursor.getColumnIndex(COLUMNS.FECHA_NACIMEINTO.toString()));
        Integer peso = cursor.getInt(cursor.getColumnIndex(COLUMNS.PESO.toString()));
        PersonaDb p = new PersonaDb(id, nombre, peso, fechaNac);
        return p;
    }


    Integer idPersona;
    String nombre;
    Integer peso;
    Long fechaNacimiento;

    public Integer getPeso() {
        return peso;
    }
    public void setPeso(Integer peso) {
        this.peso = peso;
    }
    public Long getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getNombre() {
        return nombre;
    }
    public Integer getIdPersona() {
        return idPersona;
    }
    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public static String getCreateTable() {
        return "CREATE TABLE " +
                TABLA_PERSONA + "("
                + COLUMNS.ID_PERSONA + " INTEGER PRIMARY KEY, "
                + COLUMNS.NOMBRE + " TEXT, "
                + COLUMNS.FECHA_NACIMEINTO + " INTEGER, "
                + COLUMNS.PESO + " INTEGER"
                + ")";
    }



    public PersonaDb(Integer id, String nombre, Integer peso, Long fechaNacimiento) {
        super();
        this.idPersona = id;
        this.nombre = nombre;
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
    }

    public PersonaDb(String nombre, Integer peso, Long fechaNacimiento) {
        super();
        this.nombre = nombre;
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
    }



}
