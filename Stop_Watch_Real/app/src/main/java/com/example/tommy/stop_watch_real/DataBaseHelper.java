package com.example.tommy.stop_watch_real;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UCharacter;

/**
 * Created by Justin Chan Yung Shing on 18/08/2017.
 */

class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Record"; // name of the database
    private static final int DB_VERSION = 1; //Version

    DataBaseHelper (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE RECORD ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "DATE STRING," //careful
                + "MINUTES INTEGER,"
                + "LIST STRING" + ");"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //no need to worry about this yet.
    }
}
