package com.example.virtualenvironmentmon.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virtualenvironmentmon.R;
import com.example.virtualenvironmentmon.models.Locations;
import com.example.virtualenvironmentmon.models.LocationsDB;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsFragment extends Fragment {

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            /**
            * WARNING!
             * If there is a crash when Map Fragment is selected, the entries from the DB
             * are probably gone and must be re-inserted.
             *
             * Adding 4 locations in the DB.
             * On the first run, the following insert lines MUST be included:
            */
//            Locations loc_Bucharest = new Locations("Bucharest", 44.4314135324562, 26.100022758591628);
//            Locations loc_pve = new Locations("PVE node", 44.411537663676945, 26.173585079047385);
//            Locations loc_bkp1 = new Locations("Backup 1 node", 44.445625, 26.039647);
//            Locations loc_bkp2 = new Locations("Backup 2 node", 44.4183707338293, 26.08444736468625);
            LocationsDB db = LocationsDB.getInstance(getActivity().getApplicationContext());
//            db.getLocationsDao().insert(loc_Bucharest);
//            db.getLocationsDao().insert(loc_pve);
//            db.getLocationsDao().insert(loc_bkp1);
//            db.getLocationsDao().insert(loc_bkp2);

            List<Locations> ld = db.getLocationsDao().getAll();
            for (Locations loc : ld) {
                if(!loc.getPlace_name().equals("Bucharest")) {
                    LatLng node = new LatLng(loc.getLat(), loc.getLng());
                    googleMap.addMarker(new MarkerOptions().position(node).title(loc.getPlace_name()));
                }
            }

            Locations city = db.getLocationsDao().getLocationByName("Bucharest");
            //LatLng Bucharest = new LatLng(44.4314135324562, 26.100022758591628);
            LatLng Bucharest = new LatLng(city.getLat(), city.getLng());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Bucharest, 10.5f));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(node2, 15f));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}