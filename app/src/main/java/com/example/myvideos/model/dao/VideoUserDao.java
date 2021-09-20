package com.example.myvideos.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myvideos.model.tables.UserVideoCrossRef;
import com.example.myvideos.model.tables.Video;
import com.example.myvideos.model.tables.VideoWithUser;

import java.util.List;

@Dao
public interface VideoUserDao {

    @Query("select * from UserVideoCrossRef")
    LiveData<List<UserVideoCrossRef>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserVideoCrossRef... userVideoCrossRefs);

    @Query("delete from UserVideoCrossRef where userId = :userId and videoId = :videoId")
    void delete(String userId,String videoId);


    @Query("select * from Video v,UserVideoCrossRef r where r.userId = :userId and r.videoId = v.id ")
    List<Video> getAllUserVideos(String userId);

    @Query("select * from Video v,UserVideoCrossRef r where r.userId = :userId and r.videoId = v.id and v.isFavorite= 1")
    List<Video> getAllFavoriteUserVideos(String userId);

}
