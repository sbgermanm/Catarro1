package com.sebas.catarro1.db.dataObjects;

import android.content.ContentValues;
import android.database.Cursor;

import com.sebas.catarro1.db.BaseDePatos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SintomasDB implements DataBaseTable {

    private Integer idSintoma;


    private String nombreSintoma;
    private Double valorSintoma;
    private String unidadesValorSintoma;
    private String comentarioSintoma;
    private Long fechaSintoma;

    public SintomasDB(Integer id, String nombre, Double valor, String unidadValor, String comentario, Long fecha) {
        this.idSintoma = id;
        this.nombreSintoma = nombre;
        this.valorSintoma = valor;
        this.unidadesValorSintoma = unidadValor;
        this.comentarioSintoma = comentario;
        this.fechaSintoma = fecha;
    }


    public String getComentarioSintoma() {
        return comentarioSintoma;
    }

    public void setComentarioSintoma(String comentarioSintoma) {
        this.comentarioSintoma = comentarioSintoma;
    }

    public Long getFechaSintoma() {
        return fechaSintoma;
    }

    public void setFechaSintoma(Long fechaSintoma) {
        this.fechaSintoma = fechaSintoma;
    }

    public Integer getIdSintoma() {
        return idSintoma;
    }

    public void setIdSintoma(Integer idSintoma) {
        this.idSintoma = idSintoma;
    }

    public String getUnidadesValorSintoma() {
        return unidadesValorSintoma;
    }

    public void setUnidadesValorSintoma(String unidadesValorSintoma) {
        this.unidadesValorSintoma = unidadesValorSintoma;
    }



    public String getNombreSintoma() {
        return nombreSintoma;
    }

    public void setNombreSintoma(String nombreSintoma) {
        this.nombreSintoma = nombreSintoma;
    }


    public Double getValorSintoma() {
        return valorSintoma;
    }

    public void setValorSintoma(Double valorSintoma) {
        this.valorSintoma = valorSintoma;
    }

    public static final String TABLE_NAME = "sintoma";

    public static String getPKColumnName() {
        return String.valueOf(COLUMNS.ID_SINTOMA);
    }


    private static enum COLUMNS {ID_SINTOMA, NOMBRE, VALOR, UNIDAD_VALOR, COMENTARIO, FECHA};


    // NO TOCAR, ESTO HAY QUE REPETIRLO EN TODAS ESTAS CLASES
    public static List<SintomasDB> selectAll(BaseDePatos baseDePatos) {
        Cursor cursor = baseDePatos.selectAll(TABLE_NAME);
        int num = cursor.getCount();
        ArrayList<SintomasDB> sintomas = new ArrayList<>(num);
        while (cursor.moveToNext()) {
            SintomasDB p = getSintomaFromCursor(cursor);
            sintomas.add(p);
        }
        cursor.close();
        return sintomas;
    }

    public static SintomasDB findById(BaseDePatos baseDePatos, Integer id) {
        Cursor cursor = baseDePatos.findById(TABLE_NAME, COLUMNS.ID_SINTOMA.toString(), id);
        cursor.moveToFirst();
        SintomasDB p = getSintomaFromCursor(cursor);
        cursor.close();
        return p;
    }

    public static void delete(BaseDePatos baseDePatos, Integer id) {
        baseDePatos.delete(TABLE_NAME, COLUMNS.ID_SINTOMA.toString(), id);
    }


    public void addToDB(BaseDePatos dbHelper) {
        ContentValues values = dameContentValues();
        dbHelper.add(TABLE_NAME, values);
    }

    private ContentValues dameContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMNS.NOMBRE.toString(), this.getNombreSintoma());
        values.put(COLUMNS.VALOR.toString(), this.getValorSintoma());
        values.put(COLUMNS.UNIDAD_VALOR.toString(), this.getUnidadesValorSintoma());
        values.put(COLUMNS.COMENTARIO.toString(), this.getComentarioSintoma());
        values.put(COLUMNS.FECHA.toString(), this.getFechaSintoma());
        return values;
    }


    public int updateToDB(BaseDePatos baseDePatos) {
        ContentValues values = dameContentValues();
        return baseDePatos.update(TABLE_NAME, values, COLUMNS.ID_SINTOMA.toString(), this.idSintoma);
    }








    private static SintomasDB getSintomaFromCursor(Cursor cursor) {
        Integer id = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID_SINTOMA.toString()));
        String nombre = cursor.getString(cursor.getColumnIndex(COLUMNS.NOMBRE.toString()));
        Long fecha = cursor.getLong(cursor.getColumnIndex(COLUMNS.FECHA.toString()));
        Double valor = cursor.getDouble(cursor.getColumnIndex(COLUMNS.VALOR.toString()));
        String unidadValor = cursor.getString(cursor.getColumnIndex((COLUMNS.UNIDAD_VALOR.toString())));
        String comentario = cursor.getString(cursor.getColumnIndex((COLUMNS.COMENTARIO.toString())));
        SintomasDB p = new SintomasDB(id, nombre, valor, unidadValor, comentario, fecha);

        return p;
    }












    public static String getCreateTable() {
        return "CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMNS.ID_PERSONA + " INTEGER PRIMARY KEY, "
                + COLUMNS.NOMBRE + " TEXT, "
                + COLUMNS.FECHA_NACIMEINTO + " INTEGER, "
                + COLUMNS.PESO + " INTEGER"
                + ")";
    }



    public SintomasDB(Integer id, String nombre, Integer peso, Long fechaNacimiento) {
        super();
        this.idPersona = id;
        this.nombreSintoma = nombre;
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
    }

    public SintomasDB(String nombre, Integer peso, Long fechaNacimiento) {
        super();
        this.nombreSintoma = nombre;
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
    }





    public int getEdad(){
        Date hoy = new Date();
        Calendar calHoy = Calendar.getInstance();
        calHoy.setTime(hoy);

        Calendar calNac = Calendar.getInstance();
        calNac.setTimeInMillis(this.fechaNacimiento);

        int annoNac = calNac.get(Calendar.YEAR);
        int annoHoy = calHoy.get(Calendar.YEAR);

        int anos = annoHoy-annoNac;

        int diaAnoNac = calNac.get(Calendar.DAY_OF_YEAR);
        int diaAnoHoy = calHoy.get(Calendar.DAY_OF_YEAR);

        if (diaAnoHoy<diaAnoNac) anos--;

        return anos;
    }

}
