package com.example.tumo20.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tumo20.Models.User;


public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user) {
        int ctct = user.getContact();

        editor.putInt(SESSION_KEY, ctct).commit();
    }

    public int getSession() {
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void removeSession() {
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
