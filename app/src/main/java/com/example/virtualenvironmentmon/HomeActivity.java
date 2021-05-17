package com.example.virtualenvironmentmon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.virtualenvironmentmon.fragments.ContainersFragment;
import com.example.virtualenvironmentmon.fragments.HomeFragment;
import com.example.virtualenvironmentmon.fragments.MapsFragment;
import com.example.virtualenvironmentmon.fragments.VMSFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import it.corsinvest.proxmoxve.api.PveClient;

public class HomeActivity extends AppCompatActivity {

    public PveClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * The following 2 lines work only if this activity and MainActivity run on the same process.
         */
        final PveClient objReceived = ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("object_value")).getData();
        client = new PveClient(objReceived);

//        try {
//            String serverReply = String.valueOf(client.getVersion().version().getResponse().get("data"));
//            System.out.println("------+-------" + serverReply);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_vms:
                            selectedFragment = new VMSFragment();
                            break;
                        case R.id.nav_containers:
                            selectedFragment = new ContainersFragment();
                            break;
                        case R.id.nav_map:
                            selectedFragment = new MapsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };
}