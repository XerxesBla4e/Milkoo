package com.example.tumo20.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.tumo20.Database.DatabaseManager;
import com.example.tumo20.MainActivity;
import com.example.tumo20.Models.User;
import com.example.tumo20.R;
import com.example.tumo20.SessionManager.SessionManager;

import java.sql.SQLDataException;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText contact, password;
    String scontact, spassword, sdname;
    Button login;
    ProgressBar progressBar;
    ImageView back;
    DatabaseManager databaseManager;
    TextView signup,forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        initViews();

        login.setOnClickListener(this);
        back.setOnClickListener(this);
        signup.setOnClickListener(this);

        databaseManager = new DatabaseManager(getApplicationContext());
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }

        sdname = getIntent().getStringExtra("dealername");
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        int userId = sessionManager.getSession();

        if (userId != -1) {
            moveToMain();
        } else {

        }
    }

    private void initViews() {
        contact = findViewById(R.id.contact2);
        password = findViewById(R.id.password);
        login = findViewById(R.id.signin);
        progressBar = findViewById(R.id.progress3);
        progressBar.setVisibility(View.GONE);
        back = findViewById(R.id.back3);
        signup = findViewById(R.id.signup);
    }


    @Override
    public void onClick(View view) {
        int clickid = view.getId();
        switch (clickid) {
            case R.id.signin:
                scontact = contact.getText().toString();
                spassword = password.getText().toString();

                if (validateFields()) {
                    progressBar.setVisibility(View.VISIBLE);
                    login.setEnabled(false);
                    login.setVisibility(View.GONE);
                    boolean res = databaseManager.checkAlreadyExists(scontact, spassword);
                    if (res) {
                        progressBar.setVisibility(View.GONE);
                        login.setEnabled(true);
                        login.setVisibility(View.VISIBLE);

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                        User user = new User(Integer.parseInt(scontact));
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        sessionManager.saveSession(user);

                        moveToMain();

                    } else {
                        progressBar.setVisibility(View.GONE);
                        login.setEnabled(true);
                        login.setVisibility(View.VISIBLE);

                        Toast.makeText(this, "Oops!! Login UnSuccessful", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.signup:
                startActivity(new Intent(getApplicationContext(),SignUp.class));
                break;
            case R.id.back3:
                onBackPressed();
                break;
        }
    }

    private void moveToMain() {
        Intent xerx = new Intent(Login.this, MainActivity.class);
        xerx.putExtra("dealercontact", scontact);
        xerx.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(xerx);
    }

    private boolean validateFields() {
        scontact = contact.getText().toString();
        spassword = password.getText().toString();

        if (scontact.isEmpty()) {
            contact.setError("Contact required");
            return false;
        }
        if (spassword.isEmpty()) {
            password.setError("Password required");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}