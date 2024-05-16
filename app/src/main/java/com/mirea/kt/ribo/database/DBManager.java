package com.mirea.kt.ribo.database;

import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {

    private SQLiteOpenHelper sqLiteOpenHelper;

    public DBManager(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

}