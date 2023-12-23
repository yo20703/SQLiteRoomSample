package com.example.sqliteroomsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.util.Log;

import com.example.sqliteroomsample.room.data.AppDatabase;
import com.example.sqliteroomsample.room.data.User;
import com.example.sqliteroomsample.room.data.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase appDatabase =
                Room.databaseBuilder(this, AppDatabase.class, "UserDataBase").build();

        UserDao userDao = appDatabase.userDao();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                User user = new User();
//                user.uid = 0;
//                user.firstName = "王";
//                user.lastName = "小明";
//
//                userDao.insertUser(user);
                List<User> users = userDao.getAll();
                Log.i("TAG", "users: ");
            }
        }).start();
    }
}