package com.example.myvideos.model.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Video {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ID)
    private String id;
    @ColumnInfo(name = USER_ID)
    private String userOwnerId;
    @ColumnInfo(name = USER_NAME)
    private String userName;
    @ColumnInfo(name = VIDEO_NAME)
    private String videoName;
    @ColumnInfo(name = IS_DELETED)
    private boolean isDeleted;
    @ColumnInfo(name = IS_FAVORITE)
    private boolean isFavorite;
    @ColumnInfo(name = VIDEO_PATH)
    private String videoPath;

    //ColumnNames
    final static String ID="id";
    final static String USER_ID="userOwnerId";
    final static String USER_NAME="userName";
    final static String VIDEO_NAME="videoName";
    final static String IS_DELETED="isDeleted";
    final static String IS_FAVORITE="isFavorite";
    final static String VIDEO_PATH="videoPath";

    //Constructors
    public Video() {}

    @Ignore
    public Video(String id, String userName, String userOwnerId, boolean isDeleted,String videoName,boolean isFavorite,String path) {
        this.id = id;
        this.userName=userName;
        this.userOwnerId = userOwnerId;
        this.videoName = videoName;
        this.isDeleted = isDeleted;
        this.isFavorite = isFavorite;
        this.videoPath=path;
    }

    //Setters
    public void setId(@NonNull String id) { this.id = id; }
    public void setUserOwnerId(String userOwnerId) { this.userOwnerId = userOwnerId; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setVideoName(String videoName) { this.videoName = videoName; }
    public void isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }



    //Getters
    @NonNull
    public String getId() { return id; }
    public String getUserOwnerId() { return userOwnerId; }
    public String getUserName() { return userName; }
    public String getVideoName() { return videoName; }
    public boolean isDeleted() { return isDeleted; }
    public boolean isFavorite() { return isFavorite; }
    public String getVideoPath() { return videoPath; }
}
