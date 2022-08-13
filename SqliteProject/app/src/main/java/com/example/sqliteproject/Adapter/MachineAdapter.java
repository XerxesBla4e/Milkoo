package com.example.sqliteproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqliteproject.Model.MachinesModel;
import com.example.sqliteproject.R;
import com.example.sqliteproject.Users.DatabaseManger;

import java.sql.SQLDataException;
import java.util.List;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.ViewHolder> {
    String imageUri;
    DatabaseManger databaseManager;

    Context context;
    List<MachinesModel> machinesModelList;

    public MachineAdapter(Context context, List<MachinesModel> machinesModelList) {
        this.context = context;
        this.machinesModelList = machinesModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.machinerow, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MachinesModel machinesModel = machinesModelList.get(position);

        holder.tvmid.setText(String.valueOf(machinesModel.getId()));
        holder.tvmname.setText(machinesModel.getName());
        holder.tvmserial.setText(machinesModel.getSerialnum());
        holder.tvmmodel.setText(machinesModel.getModelnum());
        holder.tvmmanf.setText(machinesModel.getManufacturer());
        holder.tvmdepartment.setText(machinesModel.getDepartment());
        holder.tvmstate.setText(machinesModel.getMachine_state());
        holder.tvmservice.setText(machinesModel.getLastservice());
        holder.tvmcomment.setText(machinesModel.getComment());

    }

    @Override
    public int getItemCount() {
        return machinesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvmid, tvmname, tvmserial, tvmmodel, tvmmanf, tvmdepartment, tvmstate, tvmservice, tvmcomment;

        public ViewHolder(View itemView) {
            super(itemView);
            tvmid = itemView.findViewById(R.id.mid);
            tvmname = itemView.findViewById(R.id.mname);
            tvmserial = itemView.findViewById(R.id.mserial);
            tvmmodel = itemView.findViewById(R.id.mnumber);
            tvmmanf = itemView.findViewById(R.id.mmanf);
            tvmdepartment = itemView.findViewById(R.id.mdepartment);
            tvmstate = itemView.findViewById(R.id.mstate);
            tvmservice = itemView.findViewById(R.id.mlastservice);
            tvmcomment = itemView.findViewById(R.id.rcomment);
        }
    }

}
