package com.example.insertapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.insertapi.connection.connection;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, passsword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        register.setOnClickListener(this);
    }

    private void init() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        passsword = findViewById(R.id.password);
        register = findViewById(R.id.regisBtn);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regisBtn:
                retrieveDetails();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Wrong Option", Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveDetails() {
        String name1 = name.getText().toString();
        String email1 = email.getText().toString();
        String password1 = passsword.getText().toString();

        new InsertData().execute(name1, email1, password1);
    }


    private class InsertData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String link = connection.API + "insert.php";

                String data = URLEncoder.encode("name", "UTF-8") + "=" +
                        URLEncoder.encode(strings[0], "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                        URLEncoder.encode(strings[1], "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(strings[2], "UTF-8");

                URL url = new URL(link);

                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(data);
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                return reader.readLine();

            } catch (Exception e) {
                return "Error! " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("response").equals("success")) {
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}