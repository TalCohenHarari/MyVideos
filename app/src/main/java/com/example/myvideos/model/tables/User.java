package com.example.myvideos.model.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ID)
    private String id;
    @ColumnInfo(name = NAME)
    private String name;
    @ColumnInfo(name = PASSWORD)
    private String password;
    @ColumnInfo(name = IS_ACTIVE)
    private boolean isActive;

    //ColumnNames
    final static String ID="id";
    final static String NAME="full_name";
    final static String PASSWORD="password";
    final static String IS_ACTIVE="isActive";

    //Constructors
    public User() {}

    @Ignore
    public User(String id,String name, String password, boolean isActive) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.isActive = isActive;
    }

    //Setters
    public void setId(@NonNull String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setActive(boolean active) { isActive = active; }


    //Getters
    @NonNull
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public boolean isActive() { return isActive; }
}
