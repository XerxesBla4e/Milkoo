package com.example.sqliteproject.LogSign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqliteproject.Model.HODModel;
import com.example.sqliteproject.Model.UserModel;
import com.example.sqliteproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private EditText etemail, etname, etpassword;
    private TextView tv;
    private String email, name, password, repassword;
    private ProgressBar progressBar;
    private ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        hooks();

        mAuth = FirebaseAuth.getInstance();

    }

    public void hooks() {
        etemail = findViewById(R.id.email);
        etname = findViewById(R.id.name);
        etpassword = findViewById(R.id.pas);
        progressBar = findViewById(R.id.progressBar2);
        backbtn = findViewById(R.id.back_presssed);
        tv = findViewById(R.id.signtxt);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
    }


    private boolean validateFields() {
        String email = etemail.getText().toString().trim();
        String name = etname.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        ProgressDialog dialog = new ProgressDialog(Signup.this);

        if (email.isEmpty()) {
            etemail.setError("Email Cant be empty");
            etemail.requestFocus();
            return false;
        } else if (name.isEmpty()) {
            etname.setError("Name Field Cant be empty");
            etname.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            etpassword.setError("Password Field Cant be empty");
            etpassword.requestFocus();
            return false;
        } else if (password.length() < 6) {
            etpassword.setError("Min password is 6 characters!");
            etpassword.requestFocus();
            return false;
        }
        return true;
    }

    public void signup(View view) {

        if (!validateFields()) {
            return;
        }

        String email = etemail.getText().toString().trim();
        String name = etname.getText().toString().trim();
        String password = etpassword.getText().toString().trim();


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        HODModel hodModel = new HODModel(name, email, password, "hod");

                        databaseReference
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(hodModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Signup.this, Logn.class);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(Signup.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });

    }

    public void HaveAccnt(View view) {
        startActivity(new Intent(Signup.this, Logn.class));
    }
}