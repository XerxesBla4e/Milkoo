package com.example.test4.Adapters.test4.Adapters;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test4.Adapters.test4.Models.adultmodel;
import com.example.test4.Adapters.test4.Results;
import com.example.test4.Databases.DatabaseManager;
import com.example.test4.R;

import java.sql.SQLDataException;
import java.util.List;

public class AdultAdapter extends RecyclerView.Adapter<AdultAdapter.ViewHolder> {
    private DatabaseManager databaseManager;
    public boolean submitButton;

    Context context;
    List<adultmodel> adultmodelList;

    public AdultAdapter(Context context, List<adultmodel> adultmodelList) {
        this.context = context;
        this.adultmodelList = adultmodelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recrow, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        adultmodel adultmodel = adultmodelList.get(position);

        long adult_id = adultmodel.get_id();

        holder.tvaddress.setText(adultmodel.getAddress());
        holder.tvadulterant.setText(adultmodel.getAdulterant());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecord(adult_id);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editRecord(adult_id);
            }
        });

    }

    private void editRecord(long adult_id) {
        updateDialog(adult_id);
    }

    private void updateDialog(long adult_id) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.update_record);

        //Initialize the dialog views
        final EditText sourceName = dialog.findViewById(R.id.milksource);
        final EditText adulterant1 = dialog.findViewById(R.id.adulterant);
        final CheckBox accept_deny = dialog.findViewById(R.id.accept);
        Button saveBtn = dialog.findViewById(R.id.saveRecord);

        saveBtn.setOnClickListener((v) -> {
            String sourceAddress = sourceName.getText().toString();
            String adulterant = adulterant1.getText().toString();
            submitButton = accept_deny.isChecked();

            updateRecord(adult_id, sourceAddress, adulterant);

            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateRecord(long adult_id, String sourceAddress, String adulterant) {
        databaseManager = new DatabaseManager(context);
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }

        if (submitButton) {
            int i = databaseManager.updateRecord(adult_id, sourceAddress, adulterant);
            if (i > 0) {
                Toast.makeText(context, "Successfully Updated Record", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Ooops!!Updating Failed...", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Accept diagnosis status(checkbox)", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRecord(long adult_id) {
        databaseManager = new DatabaseManager(context);
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            e.printStackTrace();
        }
        int res = databaseManager.delete(adult_id);
        if (res > 0) {
            Toast.makeText(context, "Successfully Saved Record", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Ooops!!Saving Failed...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return adultmodelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvaddress, tvadulterant;
        Button edit, delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvaddress = itemView.findViewById(R.id.address);
            tvadulterant = itemView.findViewById(R.id.adulterant1);
            edit = itemView.findViewById(R.id.btnedit);
            delete = itemView.findViewById(R.id.btndelete);
        }
    }

}
