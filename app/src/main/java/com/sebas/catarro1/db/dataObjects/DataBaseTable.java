package com.sebas.catarro1.db.dataObjects;

import android.database.Cursor;

import com.sebas.catarro1.db.BaseDePatos;

import java.util.List;


public interface DataBaseTable {
	public void addToDB(BaseDePatos dbHelper);
	public void updateToDB(BaseDePatos baseDePatos);



	}
