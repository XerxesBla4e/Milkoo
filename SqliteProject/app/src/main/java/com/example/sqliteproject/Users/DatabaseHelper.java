package com.example.sqliteproject.Users;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "Machines1";
    static final int DB_VERSION = 3;

    static final String TABLE_NAME = "Machine";
    public static final String ID = "_id";
    public static final String CURRENT_DATE = "date";
    public static final String NAME = "username";
    public static final String SERIAL_NUMBER = "serial";
    public static final String MODEL_NUMBER = "model_no";
    public static final String MANUFACTURER = "manufacturer";
    public static final String DEPARTMENT = "department";
    public static final String MACHINE_STATE = "machine_state";
    public static final String LAST_SERVICE = "last_service";
    public static final String COMMENT = "comment";


    static final String CREATE_DB_QUERY = "CREATE TABLE " + TABLE_NAME +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CURRENT_DATE + " TEXT NOT NULL, " +
            NAME + " TEXT NOT NULL, " + SERIAL_NUMBER + " TEXT NOT NULL, " + MODEL_NUMBER
            + " TEXT NOT NULL, " + MANUFACTURER + " TEXT NOT NULL, " +
            DEPARTMENT + " TEXT NOT NULL, " + MACHINE_STATE + " TEXT NOT NULL, " + LAST_SERVICE
            + " TEXT NOT NULL, " + COMMENT + " TEXT NOT NULL " + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
