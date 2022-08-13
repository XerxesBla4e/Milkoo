package com.example.tumo20.Database;

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

    public Cursor fetch(String dealercontact) {
        String[] items = new String[]{DatabaseHelper.NAME};
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME, items, DatabaseHelper.CONTACT + " = '" + dealercontact + "'", null, null, null, null);

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

  /*  public boolean checkAlreadyExistsr(String dealername, String password) {
        Cursor res = sqLiteDatabase.rawQuery("");
        if(res.getCount()>0){
            return true;
        }else
        {
            return false;
        }
    }*/

    public boolean checkAlreadyExists(String contact, String password) {

       String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE contact = ? AND password = ?";
       String[]selectionArgs = {contact,password};
       Cursor cursor1 = sqLiteDatabase.rawQuery(query,selectionArgs);
        if (cursor1.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}

