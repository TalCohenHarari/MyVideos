package com.example.myvideos.model.tables;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserVideoJoin {
    @Embedded
    public User user;
    @Relation(parentColumn = "userId", entityColumn = "userOwnerId" )
    public List<Video> videoList;
}