package com.example.myvideos.model.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myvideos.model.tables.Video;

import java.util.List;

@Dao
public interface VideoDao {
    @Query("select * from Video")
    LiveData<List<Video>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Video... videos);

    @Delete
    void delete(Video video);

    @Query("select * from Video where userOwnerId = :userId")
    List<Video> getAllUserVideos(String userId);

    @Query("select * from Video where userOwnerId = :userId and isFavorite= 1")
    List<Video> getAllFavoriteUserVideos(String userId);
}
