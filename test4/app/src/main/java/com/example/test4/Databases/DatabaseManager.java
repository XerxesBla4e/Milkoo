package com.example.test4.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

public class DatabaseManager {
    Context context;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public DatabaseManager(Context ctxt) {
        context = ctxt;
    }

    public DatabaseManager open() throws SQLDataException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public int insert(String dealername, String contact, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, dealername);
        contentValues.put(DatabaseHelper.CONTACT, contact);
        contentValues.put(DatabaseHelper.PASSWORD, password);

        int i = (int) sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        return i;
    }

    public int saverecord(String sourceAddress, String adulterant) {
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(DatabaseHelper.LOCATION, sourceAddress);
        contentValues1.put(DatabaseHelper.ADULTERANT, adulterant);

        int i = (int) sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME1, null, contentValues1);
        return i;
    }

    public Cursor fetch(String dealercontact) {
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE contact = ? ";
        String[] selectionArgs = {dealercontact};
        Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch() {
        String[] items = new String[]{DatabaseHelper.ID, DatabaseHelper.NAME, DatabaseHelper.CONTACT, DatabaseHelper.PASSWORD};
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME, items, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String dealername, String contact, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, dealername);
        contentValues.put(DatabaseHelper.CONTACT, contact);
        contentValues.put(DatabaseHelper.PASSWORD, password);
        int f = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.ID + "=" + _id, null);
        return f;
    }

    /*
    public int delete(long id) {
        int r = sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + " = " + id, null);
        return r;
    }


    public void delete() {
        sqLiteDatabase.execSQL("delete from " + DatabaseHelper.TABLE_NAME);
    }*/

    public boolean checkAlreadyExists(String contact, String password) {

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE contact = ? AND password = ?";
        String[] selectionArgs = {contact, password};
        Cursor cursor1 = sqLiteDatabase.rawQuery(query, selectionArgs);
        if (cursor1.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}

