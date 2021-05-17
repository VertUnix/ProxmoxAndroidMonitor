package com.example.virtualenvironmentmon.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "places")

public class Locations {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String place_name;
    private double lat;
    private double lng;

    public Locations(String place_name, double lat, double lng) {
        this.id = id;
        this.place_name = place_name;
        this.lat = lat;
        this.lng = lng;
    }


    public int getId() {
        return id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
