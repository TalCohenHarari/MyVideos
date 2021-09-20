package com.example.myvideos.model.tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

// Many To Many Table
@Entity(primaryKeys = {"userId", "videoId"})
public class UserVideoCrossRef {
        @NonNull
        public String userId;
        @NonNull
        public String videoId;

        public UserVideoCrossRef() {}

        @Ignore
        public UserVideoCrossRef(String userId, String videoId) {
                this.userId = userId;
                this.videoId = videoId;
        }

        //Setters
        public void setUserId(String userId) { this.userId = userId; }
        public void setVideoId(String videoId) { this.videoId = videoId; }

        //Getters
        public String getVideoId() { return videoId; }
        public String getUserId() { return userId; }
}
