package com.example.test4.Adapters.test4;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test4.Adapters.test4.Adapters.AdultAdapter;
import com.example.test4.Adapters.test4.Models.adultmodel;
import com.example.test4.Databases.DatabaseManager;
import com.example.test4.R;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

public class Records extends AppCompatActivity {
    private static final String TAG = "ViewDevice Fragment";

    RecyclerView recyclerView;
    AdultAdapter adultAdapter;
    private List<adultmodel> adultmodelList;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        databaseManager = new DatabaseManager(getApplicationContext());
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.adulterantrecycler2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<adultmodel> adultmodelList = new ArrayList<adultmodel>();
        Cursor cursor = databaseManager.fetch();
        if (cursor.moveToFirst()) {
            do {
                adultmodelList.add(new adultmodel(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));

            } while (cursor.moveToNext());

            adultAdapter = new AdultAdapter(getApplicationContext(), adultmodelList);
            recyclerView.setAdapter(adultAdapter);
            adultAdapter.notifyDataSetChanged();
        }
    }
}