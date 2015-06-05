package com.sebas.catarro1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sebas.catarro1.db.dataObjects.CatarroDb;
import com.sebas.catarro1.db.dataObjects.PersonaDb;
import com.sebas.catarro1.db.dataObjects.PrescripcionDB;
import com.sebas.catarro1.db.dataObjects.SintomaDB;


/**
 * Created by sgerman on 18/04/2015.
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * public class EagerSingleton {
 * private static volatile EagerSingleton instance = null;
 * <p/>
 * // private constructor
 * private EagerSingleton() {
 * }
 * <p/>
 * public static EagerSingleton getInstance() {
 * if (instance == null) {
 * synchronized (EagerSingleton.class) {
 * // Double check
 * if (instance == null) {
 * instance = new EagerSingleton();
 * }
 * }
 * }
 * return instance;
 * }
 * }
 */
public class BaseDePatos extends SQLiteOpenHelper {

    //Lazy Singleton, as we need to pass a context...
    // theInstance
    private static volatile BaseDePatos theInstance = null;

    //prevents construction
    private BaseDePatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static BaseDePatos getInstance(Context ctx) {
        if (theInstance == null) {
            synchronized (BaseDePatos.class) {
                // Double check
                if (theInstance == null) {
                    theInstance = new BaseDePatos(ctx);
                }
            }
        }
        return theInstance;
    }


    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "anotatomasDB.db";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAR_TABLA_PERSONA = PersonaDb.getCreateTable();
        //Log.d("sebas", CREAR_TABLA_PERSONA);
        db.execSQL(CREAR_TABLA_PERSONA);
        db.execSQL(CatarroDb.getCreateTable());
        db.execSQL(SintomaDB.getCreateTable());
        db.execSQL(PrescripcionDB.getCreateTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PersonaDb.TABLA_PERSONA);
        db.execSQL("DROP TABLE IF EXISTS " + CatarroDb.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SintomaDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PrescripcionDB.TABLE_NAME);

        onCreate(db);
    }


    public void add(String tabla, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tabla, null, values);
        //db.close();
    }

    public Cursor selectAll(String tableName) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, null, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor selectAll(String tableName, String orderBy) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, null, null, null, null, null, orderBy, null);
        return cursor;
    }


    public Cursor findById(String tableName, String idColumnName, Integer id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, null, idColumnName + " = " + id, null, null, null, null, null);
        return cursor;
    }

    public Cursor findByFK(String tableName, String idColumnName, Integer id, String orderBy) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, null, idColumnName + " = " + id, null, null, null, orderBy, null);
        return cursor;
    }



    public void delete(String tabla, String primaryKey, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tabla, primaryKey + "=" + id, null);

    }

    public int update(String tabla, ContentValues values, String primaryKey, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(tabla, values, primaryKey + "=" + id, null);

    }
}
