package com.example.virtualenvironmentmon.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virtualenvironmentmon.HomeActivity;
import com.example.virtualenvironmentmon.R;
import com.example.virtualenvironmentmon.adapters.VMAdapter;
import com.example.virtualenvironmentmon.models.VM;
import com.example.virtualenvironmentmon.vmDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import it.corsinvest.proxmoxve.api.PveClient;

public class HomeFragment extends Fragment {

    private boolean terminate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStop() {
        terminate = true;
        super.onStop();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar pbCPU=(ProgressBar)getActivity().findViewById(R.id.pbCPU); // initiate the progress bar
        ProgressBar pbMem=(ProgressBar)getActivity().findViewById(R.id.pbMem); // initiate the progress bar
        TextView tvNodeCPULoad = getActivity().findViewById(R.id.tvNodeCPULoad);
        TextView tvUptime = getActivity().findViewById(R.id.tvUptime);
        int maxValue=pbCPU.getMax();
        System.out.println("=========" + maxValue);


        terminate = false;

        /**
         *
         * Running the following thread while shwitching to another view causes crash sometimes.
         * TODO: Possible solution:
         * https://stackoverflow.com/questions/8464368/how-can-i-stop-threads-created-with-an-anonymous-class
         */

        new Thread(() -> {
            HomeActivity ha = (HomeActivity)getActivity();
            PveClient client = new PveClient(ha.client);
            //JSONArray serverStatus = null;
            while(!terminate) {
                try {
                    JSONObject serverJSONStatus = new JSONObject();
                    serverJSONStatus = client.getNodes().get("pve").getStatus().getRest().getResponse().getJSONObject("data");
                    double cpuLoad = serverJSONStatus.getDouble("cpu");
                    JSONObject ksmJSONObject = new JSONObject();
                    ksmJSONObject = serverJSONStatus.getJSONObject("ksm");
                    int uptime = ksmJSONObject.getInt("uptime");
                    tvUptime.setText(String.valueOf(uptime) + " s");
                    System.out.println("====--====" + uptime);
                    /*Run on UI thread*/
                    getActivity().runOnUiThread(() -> {

                        BigDecimal bd = new BigDecimal(cpuLoad).setScale(3, RoundingMode.HALF_UP);
                        double roundedCPULoad = bd.doubleValue();
                        pbCPU.setProgress((int)(roundedCPULoad * 100));
                        pbMem.setProgress(32);
                        //tvNodeCPULoad.setText(String.valueOf(roundedCPULoad * 100) + "%");
                        tvNodeCPULoad.setText(String.valueOf((Math.round(cpuLoad * 10000.0) / 100.0)) + "%");
                    });

                    //System.out.println(serverJSONStatus);
                    // double cpuLoad = Math.round()
                    Thread.sleep(3000);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //getActivity().runOnUiThread(() -> {
//                int[] temperatures = new int[result.size()];
//                int i = 0;
//                for(WeatherCondition c : result) {
//                    temperatures[i] = c.getTemperature();
//                    i++;
//                }
//                chartView.setTemperatures(temperatures);
//                chartView.invalidate();

        }).start();

    }

}
