package com.example.test4.Adapters.test4.LogSign;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test4.Adapters.test4.Databases.DatabaseManager;
import com.example.test4.R;

import java.sql.SQLDataException;


public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText name, contact, password, repassword;
    String sname, scontact, spass, srepass;
    Button signup;
    DatabaseManager databaseManager;
    ImageView back;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        initViews();

        signup.setOnClickListener(this);
        back.setOnClickListener(this);

        databaseManager = new DatabaseManager(getApplicationContext());
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }
    }

    private boolean validateFiels() {
        sname = name.getText().toString();
        scontact = contact.getText().toString();
        spass = password.getText().toString();
        srepass = repassword.getText().toString();

        if (sname.isEmpty()) {
            name.setError("Name required");
            return false;
        }
        if (scontact.isEmpty()) {
            contact.setError("Contact required");
            return false;
        }
        if (spass.isEmpty()) {
            password.setError("Password required");
            return false;
        }
        if (srepass.isEmpty()) {
            repassword.setError("Re-enter password");
            return false;
        }
        if (!spass.equals(srepass)){
            password.setError("Password Mismatch");
            return false;
        }

        return true;
    }

    private void initViews() {
        name = findViewById(R.id.editname);
        contact = findViewById(R.id.contact);
        password = findViewById(R.id.editpass);
        repassword = findViewById(R.id.confirm_password);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        signup = findViewById(R.id.btn3);
        back = findViewById(R.id.back2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn3:
                sname = name.getText().toString();
                scontact = contact.getText().toString();
                spass = password.getText().toString();
                srepass = repassword.getText().toString();

                if (validateFiels()) {
                    progressBar.setVisibility(View.VISIBLE);
                    signup.setEnabled(false);
                    signup.setVisibility(View.GONE);

                    int xer = databaseManager.insert(sname, scontact, spass);
                    if (xer > 0) {
                        progressBar.setVisibility(View.GONE);
                        signup.setEnabled(true);
                        signup.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SignUp.this, Login.class));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        signup.setEnabled(true);
                        signup.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Oops!! Failed to Create Account", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.back2:
                onBackPressed();
                break;

        }
    }
}