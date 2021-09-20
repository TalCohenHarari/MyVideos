package com.example.myvideos.model.tables;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;


//Definition for ROOM (It's not a table)
public class VideoWithUser {
    @Embedded
    public Video video;
    @Relation(
            parentColumn = "userId",
            entityColumn = "videoId",
            associateBy = @Junction(UserVideoCrossRef.class)
    )
    public List<User> playlists;
}
