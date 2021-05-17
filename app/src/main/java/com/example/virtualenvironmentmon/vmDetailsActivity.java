package com.example.virtualenvironmentmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.virtualenvironmentmon.models.VM;

public class vmDetailsActivity extends AppCompatActivity {

    private TextView tvVMName;
    private TextView tvVMID;
    private TextView tvVMStatus;
    private TextView tvVMTemplate;
    private TextView tvVMUptime;
    private TextView tvVMDisk;
    private TextView tvVMMaxMemory;
    private TextView tvVMCurrentMem;
    private TextView tvVMCores;
    private TextView tvVMCPULoad;
    private Button btnGraph;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm_details);

        Intent i = getIntent();
        VM vm = (VM) i.getSerializableExtra("vm_details");

        tvVMName = findViewById(R.id.tvVMName);
        tvVMID = findViewById(R.id.tvVMID);
        tvVMStatus = findViewById(R.id.tvVMStatus);
        tvVMTemplate = findViewById(R.id.tvVMTemplate);
        tvVMUptime = findViewById(R.id.tvVMUptime);
        tvVMDisk = findViewById(R.id.tvVMDisk);
        tvVMMaxMemory = findViewById(R.id.tvVMMaxMemory);
        tvVMCurrentMem = findViewById(R.id.tvVMCurrentMem);
        tvVMCores = findViewById(R.id.tvVMCores);
        tvVMCPULoad = findViewById(R.id.tvVMCPULoad);
        btnGraph = findViewById(R.id.btnLogin);

        tvVMName.setText(vm.name);
        System.out.println("===========" + vm.name);
        tvVMID.setText(vm.ID);
        tvVMStatus.setText(vm.status);
        if(vm.template.equals("1"))
            tvVMTemplate.setText("yes");
        else
            tvVMTemplate.setText("no");
        tvVMUptime.setText(vm.uptime + "m");
        tvVMDisk.setText(String.valueOf(vm.maxDisk) + " GB");
        tvVMMaxMemory.setText(String.valueOf(vm.maxMem) + " MB");
        tvVMCurrentMem.setText(String.valueOf(vm.currentMem) + " MB");
        tvVMCores.setText(String.valueOf(vm.cpus) + " cores");
        tvVMCPULoad.setText(String.valueOf(vm.cpuLoad) + "%");


//        btnGraph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onGraphBtnClick(View v) {
//                ;
//            }
//            });

    }
}