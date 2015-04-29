package com.sebas.catarro1.db.dataObjects;

import android.content.ContentValues;
import android.database.Cursor;

import com.sebas.catarro1.db.BaseDePatos;

import java.util.ArrayList;
import java.util.List;


public class PersonaDb implements DataBaseTable {

	private static final String TABLA_PERSONA = "persona";
	private static final String COLUMN_ID_PERSONA = "idPersona";
	private static final String COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento";
	private static final String COLUMN_NOMBRE_PERSONA = "nombre";
	
	Integer idPersona;
	String nombre;
	Long fechaNacimiento;

	
	
	public Long getFechaNacimiento() {
		return fechaNacimiento;
	}




	public void setFechaNacimiento(Long fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}



	String createTable;
	
	public static String getCreateTable() {
		return  "CREATE TABLE " +
	             TABLA_PERSONA + "("
	             + COLUMN_ID_PERSONA + " INTEGER PRIMARY KEY," + COLUMN_NOMBRE_PERSONA 
	             + " TEXT," + COLUMN_FECHA_NACIMIENTO 
	             + " INTEGER" + ")";
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
	
	public PersonaDb(Integer id, String nombre, Long fechaNacimiento) {
		super();
		this.idPersona = id;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public PersonaDb(String nombre, Long fechaNacimiento) {
		super();
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}
	

	
	public void addToDB(BaseDePatos dbHelper){
		ContentValues values = new ContentValues();
        values.put(COLUMN_FECHA_NACIMIENTO,this.getFechaNacimiento());
        values.put(COLUMN_ID_PERSONA,this.getIdPersona());
        values.put(COLUMN_NOMBRE_PERSONA,this.getNombre());

        dbHelper.add(TABLA_PERSONA, values);
	}
	
	public static List<PersonaDb> selectAll(BaseDePatos baseDePatos){
        Cursor cursor = baseDePatos.selectAll(TABLA_PERSONA);
        int num = cursor.getCount();
        ArrayList<PersonaDb> personas = new ArrayList<PersonaDb>(num);
        while (cursor.moveToNext()){
            Integer id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            Long fechaNac = cursor.getLong(2);
            PersonaDb p = new PersonaDb(id, nombre, fechaNac );
            personas.add(p);
        }
        cursor.close();
        return personas;
    }
	
	
	
	
}
