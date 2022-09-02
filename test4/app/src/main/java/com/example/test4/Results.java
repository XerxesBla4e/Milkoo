package com.example.test4;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test4.Databases.DatabaseManager;

import java.sql.SQLDataException;

public class Results extends AppCompatActivity implements View.OnClickListener {

    private Button detect, save;
    private DatabaseManager databaseManager;
    private ImageView back, guide;
    private Float pH;
    private String pH1;
    Boolean submitButton;
    private TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_results);

        initViews();

        detect.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        guide.setOnClickListener(this);

        databaseManager = new DatabaseManager(getApplicationContext());
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }

        pH1 = getIntent().getExtras().get("pH").toString();
        pH = Float.parseFloat(pH1);
        res.setText("" + pH);
    }

    public void initViews() {
        detect = findViewById(R.id.btn3);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back4);
        guide = findViewById(R.id.guide);
        res = findViewById(R.id.res);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back4:
                onBackPressed();
                break;
            case R.id.guide:
                guideDialog();
                break;
            case R.id.btn3:
                detectAdtrt();
                break;
            case R.id.save:
                saveDialog();
                break;
            default:
                break;
        }
    }

    private void detectAdtrt() {
        if (pH > 6.7) {
            res.setText("Dirty Water");
        }
    }

    private void guideDialog() {
        final Dialog dialog = new Dialog(Results.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.guidexml);

        //ImageView closeBtn = dialog.findViewById(R.id.guide);

        dialog.show();
    }

    private void saveDialog() {

        final Dialog dialog = new Dialog(Results.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.save_record);

        //Initialize the dialog views
        final EditText sourceName = dialog.findViewById(R.id.milksource);
        final EditText adulterant1 = dialog.findViewById(R.id.adulterant);
        final CheckBox accept_deny = dialog.findViewById(R.id.accept);
        Button saveBtn = dialog.findViewById(R.id.saveRecord);

        saveBtn.setOnClickListener((v) -> {
            String sourceAddress = sourceName.getText().toString();
            String adulterant = adulterant1.getText().toString();
            submitButton = accept_deny.isChecked();

            saveRecord(sourceAddress, adulterant);

            dialog.dismiss();
        });

        dialog.show();
    }

    private void saveRecord(String sourceAddress, String adulterant) {
        if (submitButton) {
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Accept diagnosis status(checkbox)", Toast.LENGTH_SHORT).show();
        }
    }
}