package com.codeunite.paymyrch.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Sqlhandler {
    static SQLiteDatabase sqlDatabase;
    Context context;
    DatabaseHelper dbHelper;

    public Sqlhandler(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        sqlDatabase = this.dbHelper.getWritableDatabase();
    }

    public void executeQuery(String query) {
        try {
            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }
            sqlDatabase = this.dbHelper.getWritableDatabase();
            sqlDatabase.execSQL(query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {
            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }
            sqlDatabase = this.dbHelper.getWritableDatabase();
            c1 = sqlDatabase.rawQuery(query, null);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c1;
    }
}
