package com.example.sqliteproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.example.sqliteproject.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class CreateAccount extends AppCompatActivity {

    ImageView img;
    Button btn;
    Uri image;
    private String facltylvl;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private EditText etemail, etname, ethealthfacilty, etpassword, etid;
    private TextView tv;
    private String email, name, password, repassword;
    private ProgressBar progressBar;
    private ImageView backbtn;

    private Spinner facility_level;
    public static final String[] levl = {"Regional Refral Hospital", "District Hospital", "HC4", "HC3", "HC2", "VHT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        hooks();

        ArrayAdapter<String> lvl = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, levl);

        lvl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facility_level.setAdapter(lvl);
        facility_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        facltylvl = "Regional Referal Hospital";
                        break;
                    case 1:
                        facltylvl = "District Hospital";
                        break;
                    case 2:
                        facltylvl = "HC4";
                        break;
                    case 3:
                        facltylvl = "HC3";
                        break;
                    case 4:
                        facltylvl = "HC2";
                        break;
                    case 5:
                        facltylvl = "VHT";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount.super.onBackPressed();
            }
        });

    }

    private void hooks() {
        etemail = findViewById(R.id.pas);
        etname = findViewById(R.id.signname);
        ethealthfacilty = findViewById(R.id.healthfacility);
        etpassword = findViewById(R.id.pass);
        progressBar = findViewById(R.id.progressBar2);
        backbtn = findViewById(R.id.back_presssed);
        tv = findViewById(R.id.signtxt);
        etid = findViewById(R.id.idnum);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        facility_level = findViewById(R.id.level);
    }

    private boolean validateFields() {
        String email = etemail.getText().toString().trim();
        String name = etname.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        String hf = ethealthfacilty.getText().toString().trim();
        String userid = etid.getText().toString().trim();
        ProgressDialog dialog = new ProgressDialog(CreateAccount.this);

        if (email.isEmpty()) {
            etemail.setError("Email Cant be empty");
            etemail.requestFocus();
            return false;
        } else if (name.isEmpty()) {
            etname.setError("Name Field Cant be empty");
            etname.requestFocus();
            return false;
        } else if (hf.isEmpty()) {
            etname.setError("Health Facilty Field Cant be empty");
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
        } else if (userid.isEmpty()) {
            etpassword.setError("User ID Field Cant be empty");
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
        String hf = ethealthfacilty.getText().toString().trim();
        String userid = etid.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        UserModel userModel = new UserModel(Integer.parseInt(userid), email, name, password, hf, facltylvl);

        databaseReference
                .push()
                .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(CreateAccount.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAccount.this, "Error Creating Account", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}