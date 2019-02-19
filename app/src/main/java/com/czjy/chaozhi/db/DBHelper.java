package com.czjy.chaozhi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
            super(context, "chaozhi.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
             * 创建tab_datalibrary【资料库表】
             */
        String createTableDataLibrary = "create table tab_datalibrary (file_id int , file_name varchar(100), " +
                "file varchar(150) , file_localurl varchar(150) , primary key('file_id')) ";
        db.execSQL(createTableDataLibrary);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
