package com.example.virtualenvironmentmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.virtualenvironmentmon.models.VM;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent i = getIntent();
        long currentMem = i.getLongExtra("currentMem", 0);
        long maxMem = i.getLongExtra("maxMem", 0);

        ChartView chartView = findViewById(R.id.chartView);

        chartView.setMemory(currentMem, maxMem);
        chartView.invalidate();
    }
}