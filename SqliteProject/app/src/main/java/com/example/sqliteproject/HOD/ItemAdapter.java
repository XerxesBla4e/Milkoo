package com.example.sqliteproject.HOD;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqliteproject.Model.MachinesModel;
import com.example.sqliteproject.Model.UploadModel;
import com.example.sqliteproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class ItemAdapter extends FirebaseRecyclerAdapter<UploadModel, ItemAdapter.ViewHolder> {
    Context context;


    public ItemAdapter(Context context, FirebaseRecyclerOptions<UploadModel> options) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hodrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, final int i, UploadModel uploadModel) {

       // viewHolder.inchargename.setText(uploadModel.getName());
       // viewHolder.uhealthfacilty.setText(uploadModel.getHealthfacilty());
      //  viewHolder.ulevel.setText(uploadModel.getLevel());
        viewHolder.uc_date.setText(uploadModel.getCurrent_date());
        viewHolder.umchne.setText(uploadModel.getMachnename());
        viewHolder.userial.setText(uploadModel.getSerialno());
        viewHolder.umodel.setText(uploadModel.getModelno());
        viewHolder.umanufacturer.setText(uploadModel.getManufacturer());
        viewHolder.udepartment.setText(uploadModel.getDepartment());
        viewHolder.ustate.setText(uploadModel.getMachinestate());
        viewHolder.ulastservice.setText(uploadModel.getLastservice());
        viewHolder.ucomment.setText(uploadModel.getComment());

        viewHolder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(viewHolder.umchne.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1100)
                        .create();

                // dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtName);
                name.setText(uploadModel.getMachnename());
                EditText state = view.findViewById(R.id.txtState);
                state.setText(uploadModel.getMachinestate());
                EditText modelnum1 = view.findViewById(R.id.txtModel);
                modelnum1.setText(uploadModel.getModelno());
                EditText serialnum1 = view.findViewById(R.id.txtSerial);
                serialnum1.setText(uploadModel.getSerialno());
                EditText comment = view.findViewById(R.id.txtComment);
                comment.setText(uploadModel.getComment());

                Button btnUpdate = view.findViewById(R.id.btnupdate);

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("machinestate", state.getText().toString());
                        map.put("modelno", modelnum1.getText().toString());
                        map.put("serialno", serialnum1.getText().toString());
                        map.put("comment", comment.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Machines")
                                .child(getRef(i).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(viewHolder.umchne.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(viewHolder.umchne.getContext(), "Error Updating Machine Details", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        viewHolder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.umchne.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("No Return After this");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Machines")
                                .child(getRef(i).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(viewHolder.umchne.getContext(), "Deletion Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView inchargename, uhealthfacilty, ulevel, uc_date, umchne, userial, umodel, umanufacturer, udepartment, ustate, ulastservice, ucomment;
        Button btndel, btnedit;

        public ViewHolder(View itemView) {
            super(itemView);

            inchargename = itemView.findViewById(R.id.incharge);
            uhealthfacilty = itemView.findViewById(R.id.healthfclty);
            ulevel = itemView.findViewById(R.id.hflevel);
            uc_date = itemView.findViewById(R.id.entrydate);
            umchne = itemView.findViewById(R.id.mnameh);
            userial = itemView.findViewById(R.id.mserialh);
            umodel = itemView.findViewById(R.id.mnumberh);
            umanufacturer = itemView.findViewById(R.id.mmanfh);
            udepartment = itemView.findViewById(R.id.mdepartmenth);
            ustate = itemView.findViewById(R.id.mstateh);
            ulastservice = itemView.findViewById(R.id.mlastserviceh);
            ucomment = itemView.findViewById(R.id.rcommenth);
            btndel = (Button) itemView.findViewById(R.id.btndelete);
            btnedit = (Button) itemView.findViewById(R.id.btnedit);

        }
    }
}

