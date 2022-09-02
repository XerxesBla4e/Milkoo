package com.example.test4.LogSign;

import android.app.Activity;
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

import com.example.test4.Databases.DatabaseManager;
import com.example.test4.MainActivity;
import com.example.test4.Models.User;
import com.example.test4.R;
import com.example.test4.Sessions.SessionManager;

import java.sql.SQLDataException;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText contact, password;
    private String scontact, spassword, sdname;
    private Button login;
    private ProgressBar progressBar;
    // private ImageView back;
    private DatabaseManager databaseManager;
    private TextView signup, frgtpass;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        initViews();

        login.setOnClickListener(this);
        // back.setOnClickListener(this);
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
        //  back = findViewById(R.id.back3);
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

                    //   IntrusionDetect(scontact, spassword);
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

                        contact.setText("");
                        password.setText("");

                        counter += counter;

                        Toast.makeText(this, "Oops!! Login Unsuccessful", Toast.LENGTH_SHORT).show();

                        if (counter == 3) {
                            Toast.makeText(this, "Too many attempts,closing app", Toast.LENGTH_SHORT).show();
                            System.exit(0);
                        }
                    }

                }
                break;
            case R.id.signup:
                Intent xerx = new Intent(Login.this, SignUp.class);
                xerx.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(xerx);
                break;
            default:
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

}