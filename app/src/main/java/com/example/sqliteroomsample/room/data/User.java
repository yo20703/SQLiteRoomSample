package com.example.sqliteroomsample.room.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    public int uid = 0;
    @ColumnInfo(name = "first_name")
    public String firstName = "";
    @ColumnInfo(name = "last_name")
    public String lastName = "";
}
