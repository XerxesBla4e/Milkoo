package com.example.sqliteproject.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sqliteproject.R;
import com.example.sqliteproject.Users.DatabaseManger;

import java.sql.SQLDataException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MachineFragment extends Fragment {

    private static final String TAG = "Machine Fragment";

    EditText modal, serial, servicedate, edtcmnt, id, manufacturer;
    String current_date, mach_name1, depart1, state1;
    Button badd, bupdate, bdelete, bread;
    Spinner machinename, department, status;
    DatabaseManger manager;
    public String sn;
    public String mn;
    public String manufactur;
    public String servicedte;
    public String cmnt;
    final Calendar lastservicedate = Calendar.getInstance();
    public static final String[] machines = {"Anaethesia", "Oxygen Concentrator", "Defibrator", "Microscope", "Pulse Eximeter", "Other"};
    public static final String[] departmnt = {"Maternity Ward", "OPD", "Emergency", "Pediatrics", "ICU", "Other"};
    public static final String[] stat = {"Pass", "Fail"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.devicefrag, container, false);


        manager = new DatabaseManger(getContext());
        try {
            manager.open();
        } catch (SQLDataException sqlDataException) {
            sqlDataException.printStackTrace();
        }

        badd = (Button) view.findViewById(R.id.add);
        bupdate = (Button) view.findViewById(R.id.update);
        bdelete = (Button) view.findViewById(R.id.delete);
        bread = (Button) view.findViewById(R.id.read);
        modal = (EditText) view.findViewById(R.id.model);
        serial = (EditText) view.findViewById(R.id.serial);
        edtcmnt = (EditText) view.findViewById(R.id.comment);
        manufacturer = (EditText) view.findViewById(R.id.manufacturer);
        id = (EditText) view.findViewById(R.id.id);
        servicedate = (EditText) view.findViewById(R.id.lastservice);
        machinename = (Spinner) view.findViewById(R.id.spinner);
        department = (Spinner) view.findViewById(R.id.spinner2);
        status = (Spinner) view.findViewById(R.id.spinner3);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                lastservicedate.set(Calendar.YEAR, year);
                lastservicedate.set(Calendar.MONTH, month);
                lastservicedate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        servicedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, lastservicedate.get(Calendar.YEAR),
                        lastservicedate.get(Calendar.MONTH),
                        lastservicedate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        DateFormat cdate = new SimpleDateFormat("MMM dd yyyy, h:mm");
        current_date = cdate.format(Calendar.getInstance().getTime());

        ArrayAdapter<String> machname = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, machines);

        machname.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machinename.setAdapter(machname);
        machinename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mach_name1 = "Anaethesia";
                        break;
                    case 1:
                        mach_name1 = "Oxygen Concentrator";
                        break;
                    case 2:
                        mach_name1 = "Defibrator";
                        break;
                    case 3:
                        mach_name1 = "Microscope";
                        break;
                    case 4:
                        mach_name1 = "Pulse Eximeter";
                        break;
                    case 5:
                        mach_name1 = "Other";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> depart = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, departmnt);

        depart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(depart);
        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        depart1 = "Maternity Ward";
                        break;
                    case 1:
                        depart1 = "OPD";
                        break;
                    case 2:
                        depart1 = "Emergency";
                        break;
                    case 3:
                        depart1 = "Pediatrics";
                        break;
                    case 4:
                        depart1 = "ICU";
                        break;
                    case 5:
                        depart1 = "Other";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> state = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, stat);

        state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(state);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        state1 = "Pass";
                        break;
                    case 1:
                        state1 = "Fail";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sn = serial.getText().toString().trim();
                mn = modal.getText().toString().trim();
                manufactur = manufacturer.getText().toString().trim();
                servicedte = servicedate.getText().toString();
                cmnt = edtcmnt.getText().toString();

                if(sn.isEmpty()){
                    serial.setError("Serial Number Cant Be Empty");
                    serial.requestFocus();
                    return;
                }

                if(mn.isEmpty()){
                    modal.setError("Modal Number Cant Be Empty");
                    modal.requestFocus();
                    return;
                }
                if(sn.isEmpty()){
                    serial.setError("Serial Cant Be Empty");
                    serial.requestFocus();
                    return;
                }
                if(manufactur.isEmpty()){
                    manufacturer.setError("Manufacturer Field Cant Be Empty");
                    manufacturer.requestFocus();
                    return;
                }
                if(cmnt.isEmpty()){
                    edtcmnt.setError("Comment Field Cant Be Empty");
                    edtcmnt.requestFocus();
                    return;
                }
                int t = manager.insert(current_date, mach_name1, sn, mn, manufactur, depart1, state1, servicedte, cmnt);
                if (t > 0) {
                    Toast.makeText(getContext(), "Machine Details Added Successfullly", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Input Machine ID to Update", Toast.LENGTH_SHORT).show();
                } else {
                    sn = serial.getText().toString().trim();
                    mn = modal.getText().toString().trim();
                    manufactur = manufacturer.getText().toString().trim();
                    servicedte = servicedate.getText().toString();
                    cmnt = edtcmnt.getText().toString();

                    int xer1 = manager.update(Integer.parseInt(id.getText().toString().trim()), current_date, mach_name1, sn, mn, manufactur, depart1, state1, servicedte, cmnt);
                    if (xer1 > 0) {
                        Toast.makeText(getContext(), "Machine Details Updated Successfullly", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        bdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Input Machine ID to Delete", Toast.LENGTH_SHORT).show();
                } else {
                    int xer2 = manager.delete(Integer.parseInt(id.getText().toString().trim()));
                    if (xer2 > 0) {
                        Toast.makeText(getContext(), "Machine Deleted Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private void updateLabel() {
        String frmt = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(frmt, Locale.getDefault());
        servicedate.setText(simpleDateFormat.format(lastservicedate.getTime()));
    }

}
