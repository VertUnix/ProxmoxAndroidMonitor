package com.example.virtualenvironmentmon.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virtualenvironmentmon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

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
            LatLng node1 = new LatLng(44.411537663676945, 26.173585079047385);
            LatLng node2 = new LatLng(44.445625, 26.039647);
            LatLng node3 = new LatLng(44.4183707338293, 26.08444736468625);
            LatLng Bucharest = new LatLng(44.4314135324562, 26.100022758591628);
            googleMap.addMarker(new MarkerOptions().position(node1).title("PVE node Location"));
            googleMap.addMarker(new MarkerOptions().position(node2).title("Bkp1 node Location"));
            googleMap.addMarker(new MarkerOptions().position(node3).title("Bkp2 node Location"));
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