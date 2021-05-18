package com.example.virtualenvironmentmon.fragments;

import android.content.Intent;
import android.icu.number.Precision;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.virtualenvironmentmon.HomeActivity;
import com.example.virtualenvironmentmon.MainActivity;
import com.example.virtualenvironmentmon.R;
import com.example.virtualenvironmentmon.adapters.VMAdapter;
import com.example.virtualenvironmentmon.models.VM;
import com.example.virtualenvironmentmon.vmDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.corsinvest.proxmoxve.api.PveClient;

import static com.example.virtualenvironmentmon.Constants.bytes_to_gigabytes;
import static com.example.virtualenvironmentmon.Constants.bytes_to_megabytes;

public class VMSFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vms, container, false);
    }

    private List<VM> fetchVMList() {
        List<VM> vms = new ArrayList<>();



//        vms.add(new VM("101", "kali-vm-1", "offline", "", 2, 2048,
//                32, R.drawable.ic_baseline_backup_table_24, 0, 0));
//        vms.add(new VM("102", "kali-vm-2", "running", "01:05:46", 2, 2048,
//                32, R.drawable.ic_baseline_backup_table_24, 2374, 0.432524));
//        vms.add(new VM("104", "ubuntu-vm-1", "offline", "", 2, 2048,
//                32, R.drawable.ic_baseline_backup_table_24, 0, 0));


        /**
         * Reading an attribute of the parent activity.
         */
        HomeActivity ha = (HomeActivity)getActivity();
        PveClient client = new PveClient(ha.client);

        System.out.println("=================");

//        try {
//            String serverReply = String.valueOf(client.getVersion().version().getResponse().get("data"));
//            System.out.println("+++++++++++++" + serverReply);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        JSONArray vms_list = null;
        try {
            vms_list = client.getNodes().get("pve").getQemu().vmlist().getResponse().getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < vms_list.length(); i++) {
            try {
                System.out.println(vms_list.get(i));
                String vm_json = String.valueOf(vms_list.get(i));
                JSONObject replyJsonObject = new JSONObject(vm_json);
                String name = replyJsonObject.getString("name");
                String ID = replyJsonObject.getString("vmid");
                String status = replyJsonObject.getString("status");
                String template = replyJsonObject.getString("template");
                int cpus = replyJsonObject.getInt("cpus");
                double cpuLoad = Math.round(replyJsonObject.getDouble("cpu") * 100);
                int uptime = replyJsonObject.getInt("uptime") / 60;
                long maxMem = replyJsonObject.getLong("maxmem") / bytes_to_megabytes;
                long currentMem = replyJsonObject.getLong("mem") / bytes_to_megabytes;
                long maxDisk = replyJsonObject.getLong("maxdisk") / bytes_to_gigabytes;

                System.out.println("NUME: " + name);
                vms.add(new VM(ID, name, status, template, uptime, cpus,
                        maxMem, maxDisk, R.drawable.ic_baseline_backup_table_24, currentMem, cpuLoad));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        return vms;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<VM> vms = fetchVMList();

        VMAdapter adapter=new VMAdapter(getActivity(), vms);
        ListView lv = getActivity().findViewById(R.id.VMSListView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "T: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity().getApplicationContext(), vmDetailsActivity.class);
                intent.putExtra("vm_details", vms.get(position));
                startActivity(intent);
            }
        });
    }
}
