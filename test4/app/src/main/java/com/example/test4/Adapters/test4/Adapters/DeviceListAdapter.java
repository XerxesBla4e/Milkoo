package com.example.test4.Adapters.test4.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test4.Adapters.test4.MainActivity;
import com.example.test4.Adapters.test4.Models.DeviceInfoModel;
import com.example.test4.R;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Object> deviceList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAddress;
        LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.textViewDeviceName);
            txtAddress = v.findViewById(R.id.textViewDeviceAddress);
            linearLayout = v.findViewById(R.id.linerLayoutDeviceInfo);
        }
    }

    public DeviceListAdapter(Context context, List<Object> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_info_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        final DeviceInfoModel deviceInfoModel = (DeviceInfoModel) deviceList.get(position);
        itemHolder.txtName.setText(deviceInfoModel.getDeviceName());
        itemHolder.txtAddress.setText(deviceInfoModel.getDeviceHardwareAddress());

        //when device is selected
        itemHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(context, MainActivity.class);

                //send details to ,ain activity
                x.putExtra("deviceName", deviceInfoModel.getDeviceName());
                x.putExtra("deviceAddress", deviceInfoModel.getDeviceHardwareAddress());

                context.startActivity(x);

            }
        });
    }

    @Override
    public int getItemCount() {
      int dataCount = deviceList.size();
      return dataCount;
    }
}