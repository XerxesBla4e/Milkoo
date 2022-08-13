package com.example.sqliteproject.Users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.SQLDataException;

public class DatabaseManger {
    DatabaseHelper dbHelper;
    Context context;
    SQLiteDatabase sqLiteDatabase;

    public DatabaseManger(Context ctx) {

        context = ctx;
    }

    public DatabaseManger open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();//initialise db instance with db object returned by helper class
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public int insert(String current_date, String name, String serial_no, String model_no, String manufacturer, String department,
                      String machine_state, String last_service, String comment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CURRENT_DATE, current_date);
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.SERIAL_NUMBER, serial_no);
        contentValues.put(DatabaseHelper.MODEL_NUMBER, model_no);
        contentValues.put(DatabaseHelper.MANUFACTURER, manufacturer);
        contentValues.put(DatabaseHelper.DEPARTMENT, department);
        contentValues.put(DatabaseHelper.MACHINE_STATE, machine_state);
        contentValues.put(DatabaseHelper.LAST_SERVICE, last_service);
        contentValues.put(DatabaseHelper.COMMENT, comment);
        int d = (int) sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        return d;
    }


    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper.ID, DatabaseHelper.CURRENT_DATE, DatabaseHelper.NAME
                , DatabaseHelper.SERIAL_NUMBER, DatabaseHelper.MODEL_NUMBER, DatabaseHelper.MANUFACTURER, DatabaseHelper.DEPARTMENT,
                DatabaseHelper.MACHINE_STATE, DatabaseHelper.LAST_SERVICE, DatabaseHelper.COMMENT};
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public int update(long _id, String current_date, String name, String serial_no, String model_no, String manufacturer, String department,
                      String machine_state, String last_service, String comment) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CURRENT_DATE, current_date);
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.SERIAL_NUMBER, serial_no);
        contentValues.put(DatabaseHelper.MODEL_NUMBER, model_no);
        contentValues.put(DatabaseHelper.MANUFACTURER, manufacturer);
        contentValues.put(DatabaseHelper.DEPARTMENT, department);
        contentValues.put(DatabaseHelper.MACHINE_STATE, machine_state);
        contentValues.put(DatabaseHelper.LAST_SERVICE, last_service);
        contentValues.put(DatabaseHelper.COMMENT, comment);
        int f = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.ID + "= ?", new String[]{String.valueOf(_id)});
        return f;
    }

    public int delete(long _id) {
        int z = sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "= ?", new String[]{String.valueOf(_id)});
        return z;
    }
}
