package com.example.tumo20.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "Accounts";
    static final int DB_VERSION = 7;

    static final String TABLE_NAME = "Account";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String CONTACT = "contact";
    public static final String PASSWORD = "password";


    static final String CREATE_DB_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " +
            CONTACT + " TEXT NOT NULL, " + PASSWORD + " TEXT NOT NULL " + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
