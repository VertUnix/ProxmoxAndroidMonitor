package com.example.virtualenvironmentmon.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.example.virtualenvironmentmon.R;
import com.example.virtualenvironmentmon.models.VM;

import java.util.List;

public class VMAdapter extends BaseAdapter {

    private final Context ctx;
    private final List<VM> vms;

    public VMAdapter(Context ctx, List<VM> vms) {
        this.ctx = ctx;
        this.vms = vms;
    }

    @Override
    public int getCount() {
        return this.vms.size();
    }

    @Override
    public Object getItem(int position) {
        return this.vms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.vm_item_layout, parent,false);

        ImageView iv = v.findViewById(R.id.vm_item_image_view);
        TextView tvStatus = v.findViewById(R.id.status);
        String disk_uptime_text;
        VM vm = (VM) getItem(position);
        if(vm.status.equals("running")) {
            iv.setColorFilter(ContextCompat.getColor(ctx, R.color.online_green), android.graphics.PorterDuff.Mode.SRC_IN);
            tvStatus.setText("running");
            tvStatus.setTextColor(ContextCompat.getColor(ctx, R.color.online_green));
            disk_uptime_text = "Disk: " + vm.maxDisk + " GB" + "              " + "Uptime: " + vm.uptime + " m";
        }
        else {
            iv.setColorFilter(ContextCompat.getColor(ctx, R.color.grayed_out), android.graphics.PorterDuff.Mode.SRC_IN);
            tvStatus.setText(vm.status);
            disk_uptime_text = "Disk: " + vm.maxDisk + " GB";
        }
        TextView tv1 = v.findViewById(R.id.vm_title_id);
        TextView tv2 = v.findViewById(R.id.vm_cpu_mem);
        TextView tv3 = v.findViewById(R.id.vm_disk_uptime);
        iv.setImageResource(vm.getImage());
        tv1.setText(vm.name + " (" + vm.ID + ")");
        tv2.setText("CPUs: " + vm.cpus + "              " + "Memory: " + vm.maxMem + " MB");
        tv3.setText(disk_uptime_text);
        return v;
    }



}
