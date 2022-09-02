package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.test4.Adapters.test4.Adapters.AdultAdapter;

public class HODPage extends AppCompatActivity {

    RecyclerView recyclerView;
    AdultAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        recyclerView = findViewById(R.id.adulterantrecycler2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UploadModel> options =
                new FirebaseRecyclerOptions.Builder<UploadModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Machines"), UploadModel.class)
                        .build();

        itemAdapter = new ItemAdapter(this, options);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
        itemAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        itemAdapter.stopListening();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menumenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(), Logn.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}