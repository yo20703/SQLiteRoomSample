package com.example.sqliteroomsample.room.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllById(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE (:firstName) AND" +
            " last_name LIKE (:lastName) LIMIT 1")
    User findByName(String firstName, String lastName);

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);
}
