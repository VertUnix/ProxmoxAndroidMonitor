package com.example.virtualenvironmentmon.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.virtualenvironmentmon.models.daos.LocationsDao;

@Database(entities = {Locations.class}, version = 1, exportSchema = false)
public abstract class LocationsDB extends RoomDatabase{

    private final static String DB_NAME="coord_db.db";
    private static LocationsDB instance;

    public static LocationsDB getInstance(Context cnt){
        if (instance==null){
            instance= Room.databaseBuilder(cnt, LocationsDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


    public abstract LocationsDao getLocationsDao();

}
