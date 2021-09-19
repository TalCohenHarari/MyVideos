package com.example.myvideos.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myvideos.MyApplication;
import com.example.myvideos.model.dao.UserDao;
import com.example.myvideos.model.dao.VideoDao;
import com.example.myvideos.model.tables.Video;
import com.example.myvideos.model.tables.User;


@Database(entities = {User.class, Video.class}, version = 2)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract VideoDao videoDao();
}

public class AppLocalDB{

    public final static  AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "mySqliteDatabase.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

