package com.example.sqliteroomsample.room.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, VaccineStatus.class} , version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract VaccineStatusDao vaccineStatusDao();
}
