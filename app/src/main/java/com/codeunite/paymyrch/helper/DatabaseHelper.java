package com.codeunite.paymyrch.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.codeunite.paymyrch.model.OperatorModel;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_OPERATOR = "CREATE TABLE operator(operator_name TEXT,operator_code TEXT,operator_type TEXT);";
    private static final String DATABASE_NAME = "RechargeManager";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_OPERATOR_CODE = "operator_code";
    public static final String KEY_OPERATOR_NAME = "operator_name";
    public static final String KEY_OPERATOR_TYPE = "operator_type";
    private static final String LOG = "DatabaseHelper";
    private static final String TABLE_OPERATOR = "operator";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OPERATOR);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS operator");
        onCreate(db);
    }

    public long createOperatorList(OperatorModel operatorModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OPERATOR_NAME, operatorModel.getOperatorName());
        values.put(KEY_OPERATOR_CODE, operatorModel.getOperatorCode());
        values.put(KEY_OPERATOR_TYPE, operatorModel.getOperatorType());
        return db.insert(TABLE_OPERATOR, null, values);
    }

    public List<OperatorModel> getAllOperators() {
        List<OperatorModel> operatorModels = new ArrayList();
        String selectQuery = "SELECT  * FROM operator";
        Log.e(LOG, selectQuery);
        Cursor c = getReadableDatabase().rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                OperatorModel operatorModel = new OperatorModel();
                operatorModel.setOperatorName(c.getString(c.getColumnIndex(KEY_OPERATOR_NAME)));
                operatorModel.setOperatorCode(c.getString(c.getColumnIndex(KEY_OPERATOR_CODE)));
                operatorModel.setOperatorType(c.getString(c.getColumnIndex(KEY_OPERATOR_TYPE)));
                operatorModels.add(operatorModel);
            } while (c.moveToNext());
        }
        return operatorModels;
    }
}
