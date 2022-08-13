package com.example.sqliteproject.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sqliteproject.HOD.HODPage;
import com.example.sqliteproject.MainActivity;
import com.example.sqliteproject.Model.UploadModel;
import com.example.sqliteproject.Model.UserModel;
import com.example.sqliteproject.R;
import com.example.sqliteproject.Users.DatabaseHelper;
import com.example.sqliteproject.Users.DatabaseManger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "Main Fragment";

    private Button device;
    private Button viewdevice;
    private Button upload;
   // private Button hod;
    DatabaseManger databaseManger;
    DatabaseReference databaseReference;

    FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private String userName;
    private String HealthFacilty;
    private String HealthFlevel;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainfrag, container, false);

        /*
        user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userProfile = snapshot.getValue(UserModel.class);

                if (userProfile != null) {
                    userName = userProfile.getName();
                    HealthFacilty = userProfile.getHealth_Facility();
                    HealthFlevel = userProfile.getHealthFacility_Level();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        databaseManger = new DatabaseManger(getContext());

        try {
            databaseManger.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Machines");
        device = (Button) view.findViewById(R.id.btncreate);
        viewdevice = (Button) view.findViewById(R.id.btnview);
        upload = (Button) view.findViewById(R.id.btnadduser);
        /*
        hod = (Button) view.findViewById(R.id.btnview2);

        hod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HODPage.class);
                startActivity(intent);
            }
        });*/

        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setViewPager(1);
            }
        });

        viewdevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setViewPager(2);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UploadModel> uploadModelList = new ArrayList<UploadModel>();
                Cursor cursor = databaseManger.fetch();
                if (cursor.moveToFirst()) {
                    do {

                        String current_date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CURRENT_DATE));
                        String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                        String serialno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SERIAL_NUMBER));
                        String modelno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MODEL_NUMBER));
                        String manufacturer = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MANUFACTURER));
                        String department = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEPARTMENT));
                        String machinestate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MACHINE_STATE));
                        String lastservice = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LAST_SERVICE));
                        String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COMMENT));

                        uploadModelList.add(new UploadModel(current_date, name, serialno, modelno, manufacturer
                                , department, machinestate, lastservice, comment));

                    }
                    while (cursor.moveToNext());

                    if (uploadModelList.size() > 0) {
                        for (UploadModel o : uploadModelList) {
                            databaseReference.push().setValue(o);
                        }
                        Toast.makeText(getContext(), "Machines' Details Successfully Upload", Toast.LENGTH_SHORT).show();
                        //  databaseManager.delete();
                    }
                }
            }
        });

        return view;
    }
}
