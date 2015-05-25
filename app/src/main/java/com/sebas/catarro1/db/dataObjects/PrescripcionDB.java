package com.sebas.catarro1.db.dataObjects;

import android.content.ContentValues;
import android.database.Cursor;

import com.sebas.catarro1.db.BaseDePatos;

import java.util.ArrayList;
import java.util.List;


public class PrescripcionDB implements DataBaseTable {

    private Integer idPrescripcion;


    private String nombreMedicamento;
    private Double valorPosologia;
    private Integer cadenciaEnHoras;
    private Integer duracionEnDias;
    private Integer numeroTomas;
    private String comentarioPrescripcion;
    private Long fechaInicio;
    private Integer idCatarro;

    public PrescripcionDB(Integer id, String nombre, Double posologia, Integer cadencia, Integer duracion, Integer numTomas, String comentario,  Long fecha,  Integer catarroID) {
        this.idPrescripcion = id;
        this.nombreMedicamento = nombre;
        this.valorPosologia = posologia;
        this.cadenciaEnHoras = cadencia;
        this.duracionEnDias = duracion;
        this.numeroTomas = numTomas;
        this.comentarioPrescripcion = comentario;
        this.fechaInicio = fecha;
        this.idCatarro = catarroID;
    }

    public PrescripcionDB(String nombre, Double posologia, Integer cadencia, Integer duracion, Integer numTomas, String comentario,  Long fecha,  Integer catarroID) {
        this.nombreMedicamento = nombre;
        this.valorPosologia = posologia;
        this.cadenciaEnHoras = cadencia;
        this.duracionEnDias = duracion;
        this.numeroTomas = numTomas;
        this.comentarioPrescripcion = comentario;
        this.fechaInicio = fecha;
        this.idCatarro = catarroID;
    }



    public Integer getIdCatarro() {
        return idCatarro;
    }
    public void setIdCatarro(Integer idCatarro) {
        this.idCatarro = idCatarro;
    }
    public Integer getCadenciaEnHoras() {
        return cadenciaEnHoras;
    }
    public void setCadenciaEnHoras(Integer cadenciaEnHoras) {
        this.cadenciaEnHoras = cadenciaEnHoras;
    }
    public String getComentarioPrescripcion() {
        return comentarioPrescripcion;
    }
    public void setComentarioPrescripcion(String comentarioPrescripcion) {
        this.comentarioPrescripcion = comentarioPrescripcion;
    }
    public Integer getDuracionEnDias() {
        return duracionEnDias;
    }
    public void setDuracionEnDias(Integer duracionEnDias) {
        this.duracionEnDias = duracionEnDias;
    }
    public Long getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Integer getIdPrescripcion() {
        return idPrescripcion;
    }
    public void setIdPrescripcion(Integer idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }
    public String getNombreMedicamento() {
        return nombreMedicamento;
    }
    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }
    public Integer getNumeroTomas() {
        return numeroTomas;
    }
    public void setNumeroTomas(Integer numeroTomas) {
        this.numeroTomas = numeroTomas;
    }
    public Double getValorPosologia() {
        return valorPosologia;
    }
    public void setValorPosologia(Double valorPosologia) {
        this.valorPosologia = valorPosologia;
    }


    public static final String TABLE_NAME = "prescripcion";
    private static final String FOREING_KEY = COLUMNS.ID_CATARRO.toString() ;

    public static String getPKColumnName() {
        return String.valueOf(COLUMNS.ID_PRESCRIPCION);
    }


    private static enum COLUMNS {ID_PRESCRIPCION, NOMBRE, POSOLOGIA, CADENCIA, DURACION, NUMTOMAS, COMENTARIO, FECHA, ID_CATARRO};


    private static List<PrescripcionDB> prescripcionesFromCursor(Cursor cursor) {
        int num = cursor.getCount();
        ArrayList<PrescripcionDB> prescripciones = new ArrayList<>(num);
        while (cursor.moveToNext()) {
            PrescripcionDB p = getPrescripcionFromCursor(cursor);
            prescripciones.add(p);
        }
        cursor.close();
        return prescripciones;
    }

    public static List<PrescripcionDB> findByFKOrderedByDateDesc(BaseDePatos baseDePatos, Integer id) {
        Cursor cursor = baseDePatos.findByFK(TABLE_NAME, FOREING_KEY, id, COLUMNS.FECHA + " DESC");
        return prescripcionesFromCursor(cursor);
    }




    public static PrescripcionDB findById(BaseDePatos baseDePatos, Integer id) {
        Cursor cursor = baseDePatos.findById(TABLE_NAME, COLUMNS.ID_PRESCRIPCION.toString(), id);
        cursor.moveToFirst();
        PrescripcionDB p = getPrescripcionFromCursor(cursor);
        cursor.close();
        return p;
    }

    public static void delete(BaseDePatos baseDePatos, Integer id) {
        baseDePatos.delete(TABLE_NAME, COLUMNS.ID_PRESCRIPCION.toString(), id);
    }


    public void addToDB(BaseDePatos dbHelper) {
        ContentValues values = dameContentValues();
        dbHelper.add(TABLE_NAME, values);
    }





    private ContentValues dameContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMNS.NOMBRE.toString(), this.getNombreMedicamento());
        values.put(COLUMNS.POSOLOGIA.toString(), this.getValorPosologia());
        values.put(COLUMNS.CADENCIA.toString(), this.getCadenciaEnHoras());
        values.put(COLUMNS.DURACION.toString(), this.getDuracionEnDias());
        values.put(COLUMNS.NUMTOMAS.toString(), this.getNumeroTomas());
        values.put(COLUMNS.COMENTARIO.toString(), this.getComentarioPrescripcion());
        values.put(COLUMNS.FECHA.toString(), this.getFechaInicio());
        values.put(COLUMNS.ID_CATARRO.toString(), this.getIdCatarro());
        return values;
    }


    public int updateToDB(BaseDePatos baseDePatos) {
        ContentValues values = dameContentValues();
        return baseDePatos.update(TABLE_NAME, values, COLUMNS.ID_PRESCRIPCION.toString(), this.idPrescripcion);
    }




    private static PrescripcionDB getPrescripcionFromCursor(Cursor cursor) {
        Integer id = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID_PRESCRIPCION.toString()));
        String nombre = cursor.getString(cursor.getColumnIndex(COLUMNS.NOMBRE.toString()));
        Double valor = cursor.getDouble(cursor.getColumnIndex(COLUMNS.POSOLOGIA.toString()));
        Integer cadencia = cursor.getInt(cursor.getColumnIndex(COLUMNS.CADENCIA.toString()));
        Integer duracion = cursor.getInt(cursor.getColumnIndex(COLUMNS.DURACION.toString()));
        Integer numtomas = cursor.getInt(cursor.getColumnIndex(COLUMNS.NUMTOMAS.toString()));
        String comentario = cursor.getString(cursor.getColumnIndex((COLUMNS.COMENTARIO.toString())));
        Long fecha = cursor.getLong(cursor.getColumnIndex(COLUMNS.FECHA.toString()));
        Integer catarroID = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID_CATARRO.toString()));

        return new PrescripcionDB(id, nombre, valor, cadencia, duracion, numtomas, comentario, fecha, catarroID);
    }


    public static String getCreateTable() {
        return "CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMNS.ID_PRESCRIPCION + " INTEGER PRIMARY KEY, "
                + COLUMNS.NOMBRE + " TEXT, "
                + COLUMNS.POSOLOGIA +  " REAL, "
                + COLUMNS.CADENCIA + " INTEGER"
                + COLUMNS.DURACION + " INTEGER"
                + COLUMNS.NUMTOMAS + " INTEGER"
                + COLUMNS.COMENTARIO + " TEXT, "
                + COLUMNS.FECHA + " INTEGER"
                + COLUMNS.ID_CATARRO + " INTEGER"
                + ")";
    }

    @Override
    public String toString() {
        return "PENDIENTE";
    }
}
