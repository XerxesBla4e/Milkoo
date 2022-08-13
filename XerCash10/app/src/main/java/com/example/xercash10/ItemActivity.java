package com.example.xercash10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.xercash10.Database.DatabaseHelper;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTxtname, edtTxtdescription;
    private ImageView itemImage;
    private Uri imagUri;
    private Button btnItemAdd;
    private final static int IMG_REQUEST_CODE = 6;
    private DatabaseHelper databaseHelper;
    private AddItem addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        initViews();

        itemImage.setOnClickListener(this);
        btnItemAdd.setOnClickListener(this);
    }

    private void initViews() {
        edtTxtname = findViewById(R.id.edtTxtItemName);
        edtTxtdescription = findViewById(R.id.edtTxtItemdescription);
        itemImage = findViewById(R.id.itemImage);
        btnItemAdd = findViewById(R.id.btnItemAdd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnItemAdd:
                if (validateFields()) {
                    itemAdd();
                }
                break;
            case R.id.itemImage:
                selectItemImage();
                break;
            default:
                break;
        }
    }


    private void selectItemImage() {
        Intent xerxes = new Intent(Intent.ACTION_GET_CONTENT);
        xerxes.setType("image/*");
        startActivityForResult(Intent.createChooser(xerxes, "Select Item Image"), IMG_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST_CODE && resultCode == RESULT_OK && data.getData() != null) {
            imagUri = data.getData();
            itemImage.setImageURI(imagUri);
        }
    }

    private void itemAdd() {
        addItem = new AddItem();
        addItem.execute();
    }

    private boolean validateFields() {
        if (edtTxtname.getText().toString().equals("")) {
            return false;
        }
        if (edtTxtdescription.getText().toString().equals("")) {
            return false;
        }
        if (imagUri.toString().equals("")) {
            return false;
        }
        return true;
    }

    private class AddItem extends AsyncTask<Void, Void, Integer> {
        private String name, description, imgUri;

        protected void onPreExecute() {
            super.onPreExecute();

            databaseHelper = new DatabaseHelper(ItemActivity.this);
            this.name = edtTxtname.getText().toString();
            this.description = edtTxtdescription.getText().toString();
            this.imgUri = String.valueOf(imagUri);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues itemValues = new ContentValues();
            itemValues.put("name", name);
            itemValues.put("description", description);
            itemValues.put("image_url", imgUri);

            long id = db.insert("items", null, itemValues);
            return (int) id;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (integer != -1) {
                Intent xer6 = new Intent(ItemActivity.this, MainActivity.class);
                xer6.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(xer6);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != addItem) {
            if (!addItem.isCancelled()) {
                addItem.cancel(true);
            }
        }
    }
}