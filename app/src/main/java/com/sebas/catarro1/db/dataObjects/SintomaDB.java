package com.sebas.catarro1.db.dataObjects;

import android.content.ContentValues;
import android.database.Cursor;

import com.sebas.catarro1.db.BaseDePatos;
import com.sebas.catarro1.util.Miscelanea;

import java.util.ArrayList;
import java.util.List;


public class SintomaDB implements DataBaseTable {

    private Integer idSintoma;


    private String nombreSintoma;
    private Double valorSintoma;
    private String unidadesValorSintoma;
    private String comentarioSintoma;
    private Long fechaSintoma;
    private Integer idCatarro;

    public SintomaDB(Integer id, String nombre, Double valor, String unidadValor, String comentario, Long fecha, Integer catarroID) {
        this.idSintoma = id;
        this.nombreSintoma = nombre;
        this.valorSintoma = valor;
        this.unidadesValorSintoma = unidadValor;
        this.comentarioSintoma = comentario;
        this.fechaSintoma = fecha;
        this.idCatarro = catarroID;
    }

    public SintomaDB(String nombre, Double valor, String unidadValor, String comentario, Long fecha, Integer catarroID) {
        this.nombreSintoma = nombre;
        this.valorSintoma = valor;
        this.unidadesValorSintoma = unidadValor;
        this.comentarioSintoma = comentario;
        this.fechaSintoma = fecha;
        this.idCatarro = catarroID;
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
    public Integer getIdCatarro() {
        return idCatarro;
    }
    public void setIdCatarro(Integer idCatarro) {
        this.idCatarro = idCatarro;
    }

    public static final String TABLE_NAME = "sintoma";
    private static final String FOREING_KEY = COLUMNS.ID_CATARRO.toString() ;

    public static String getPKColumnName() {
        return String.valueOf(COLUMNS.ID_SINTOMA);
    }


    private enum COLUMNS {ID_SINTOMA, NOMBRE, VALOR, UNIDAD_VALOR, COMENTARIO, FECHA, ID_CATARRO};






    // NO TOCAR, ESTO HAY QUE REPETIRLO EN TODAS ESTAS CLASES

    private static List<SintomaDB> sintomasFromCursor(Cursor cursor) {
        int num = cursor.getCount();
        ArrayList<SintomaDB> sintomas = new ArrayList<>(num);
        while (cursor.moveToNext()) {
            SintomaDB p = getSintomaFromCursor(cursor);
            sintomas.add(p);
        }
        cursor.close();
        return sintomas;
    }

    public static List<SintomaDB> findByFKOrderedByDateDesc(BaseDePatos baseDePatos, Integer id) {
        Cursor cursor = baseDePatos.findByFK(TABLE_NAME, FOREING_KEY, id, COLUMNS.FECHA + " DESC");
        return sintomasFromCursor(cursor);
    }




    public static SintomaDB findById(BaseDePatos baseDePatos, Integer id) {
        Cursor cursor = baseDePatos.findById(TABLE_NAME, COLUMNS.ID_SINTOMA.toString(), id);
        cursor.moveToFirst();
        SintomaDB p = getSintomaFromCursor(cursor);
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
        values.put(COLUMNS.ID_CATARRO.toString(), this.getIdCatarro());
        return values;
    }


    public int updateToDB(BaseDePatos baseDePatos) {
        ContentValues values = dameContentValues();
        return baseDePatos.update(TABLE_NAME, values, COLUMNS.ID_SINTOMA.toString(), this.idSintoma);
    }








    private static SintomaDB getSintomaFromCursor(Cursor cursor) {
        Integer id = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID_SINTOMA.toString()));
        String nombre = cursor.getString(cursor.getColumnIndex(COLUMNS.NOMBRE.toString()));
        Long fecha = cursor.getLong(cursor.getColumnIndex(COLUMNS.FECHA.toString()));
        Double valor = cursor.getDouble(cursor.getColumnIndex(COLUMNS.VALOR.toString()));
        String unidadValor = cursor.getString(cursor.getColumnIndex((COLUMNS.UNIDAD_VALOR.toString())));
        String comentario = cursor.getString(cursor.getColumnIndex((COLUMNS.COMENTARIO.toString())));
        Integer catarroID = cursor.getInt(cursor.getColumnIndex((COLUMNS.ID_CATARRO.toString())));


        return new SintomaDB(id, nombre, valor, unidadValor, comentario, fecha, catarroID);
    }









    public static String getCreateTable() {
        return "CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMNS.ID_SINTOMA + " INTEGER PRIMARY KEY, "
                + COLUMNS.NOMBRE + " TEXT, "
                + COLUMNS.VALOR +  " REAL, "
                + COLUMNS.UNIDAD_VALOR + " TEXT, "
                + COLUMNS.COMENTARIO + " TEXT, "
                + COLUMNS.FECHA + " INTEGER,"
                + COLUMNS.ID_CATARRO + " INTEGER"
                + ")";
    }

    @Override
    public String toString() {
        String aux = Miscelanea.dameFechaPasadaComprensible(this.fechaSintoma);
        aux += ", " + Miscelanea.dameHoraComoString(this.fechaSintoma);
        aux += ", " + this.nombreSintoma;
        aux += ", " + this.valorSintoma;
        aux += " " + this.unidadesValorSintoma;
        return aux;

    }


}
