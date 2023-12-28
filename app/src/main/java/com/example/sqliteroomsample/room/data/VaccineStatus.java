package com.example.sqliteroomsample.room.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vaccine_status")
public class VaccineStatus {
    @PrimaryKey(autoGenerate = true)
    public int order = 0;

    @ColumnInfo(name = "vaccinated")
    public Boolean vaccinated = false;

    @ColumnInfo(name = "note")
    public String note = "未完成";
}
