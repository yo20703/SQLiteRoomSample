package com.example.sqliteroomsample.room.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VaccineStatusDao {
    @Query("SELECT * FROM vaccine_status")
    List<VaccineStatus> getAll();

    @Insert
    void insertVaccineStatus(VaccineStatus vaccineStatus);

    @Delete
    void deleteVaccineStatus(VaccineStatus vaccineStatus);
}
