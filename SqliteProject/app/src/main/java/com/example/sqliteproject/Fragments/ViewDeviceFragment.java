package com.example.sqliteproject.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqliteproject.Adapter.MachineAdapter;
import com.example.sqliteproject.Model.MachinesModel;
import com.example.sqliteproject.R;
import com.example.sqliteproject.Users.DatabaseManger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

public class ViewDeviceFragment extends Fragment {

    private static final String TAG = "ViewDevice Fragment";

    RecyclerView recyclerView;
    MachineAdapter machineAdapter;
    private List<MachinesModel> machinesModelList;
    DatabaseManger databaseManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_retrieve_data, container, false);

        databaseManager = new DatabaseManger(getContext());
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }

        recyclerView = view.findViewById(R.id.machinerecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MachinesModel> machinesModelList = new ArrayList<MachinesModel>();
        Cursor cursor = databaseManager.fetch();
        if (cursor.moveToFirst()) {
            do {
                machinesModelList.add(new MachinesModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                        , cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)
                        , cursor.getString(7), cursor.getString(8), cursor.getString(9)));

            } while (cursor.moveToNext());

            machineAdapter = new MachineAdapter(getContext(), machinesModelList);
            recyclerView.setAdapter(machineAdapter);
            machineAdapter.notifyDataSetChanged();
        }

        return view;
    }

}
