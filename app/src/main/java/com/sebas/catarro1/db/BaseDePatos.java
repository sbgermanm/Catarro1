package com.sebas.catarro1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.sebas.catarro1.db.dataObjects.PersonaDb;


/**
 * Created by sgerman on 18/04/2015.
 *
 *
 *
 *
 public class EagerSingleton {
    private static volatile EagerSingleton instance = null;

    // private constructor
     private EagerSingleton() {
     }

     public static EagerSingleton getInstance() {
         if (instance == null) {
            synchronized (EagerSingleton.class) {
             // Double check
            if (instance == null) {
                instance = new EagerSingleton();
            }
         }
     }
     return instance;
     }
 }
 */
public class BaseDePatos extends SQLiteOpenHelper {

    //Lazy Singleton, as we need to pass a context...
    // theInstance
    private static volatile BaseDePatos theInstance = null;
    //prevents construction
    private BaseDePatos(Context context){
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


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "anotatomasDB.db";




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAR_TABLA_PERSONA = PersonaDb.getCreateTable();
        db.execSQL(CREAR_TABLA_PERSONA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
//		onCreate(db);
    }





    public void add(String tabla, ContentValues values) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(tabla, null, values);
        //db.close();
    }

    public Cursor selectAll(String tableName) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableName, null, null, null, null, null, null, null);
        //db.close();
        return cursor;
    }
}
