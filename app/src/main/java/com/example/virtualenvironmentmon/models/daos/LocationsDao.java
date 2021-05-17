package com.example.virtualenvironmentmon.models.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.virtualenvironmentmon.models.Locations;

import java.util.List;

@Dao
public interface LocationsDao {

    @Insert
    void insert(Locations location);

    @Query("select * from places")
    List<Locations> getAll();

    @Query("delete from places")
    void deleteAll();

    @Delete
    void delete(Locations location);

    @Update
    void update(Locations location);

    @Query("select * from places where place_name=:lname limit 1")
    Locations getLocationByName(String lname);
}

